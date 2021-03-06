package com.example.shoppinglist.DataBase;


import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.shoppinglist.ItemEntity;
import com.example.shoppinglist.ListAndItemCrossRef;
import com.example.shoppinglist.ListEntity;
import com.example.shoppinglist.ListWithItems;
import com.example.shoppinglist.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Repository relativa a query sul database riguardanti gli oggetti
 * Singleton perché mi serve che la repository sappia di quale lista cercare il contenuto
 */
public class ItemRepository {

    private static final String LOG_TAG = "ItemRepository";
    private static ItemRepository INSTANCE = null;
    private final ItemDAO itemDAO;
    private final ListDAO listDAO;

    private final LiveData<List<ItemEntity>> allItemsList;
    private LiveData<List<ItemEntity>> itemsInList;
    private LiveData<ListWithItems> listWithItems;
    private Integer currentListId;

    private ItemRepository(Application application){
        UserDatabase db = UserDatabase.getDatabase(application);
        itemDAO = db.itemDAO();
        listDAO = db.listDAO();

        allItemsList = itemDAO.getAllItems();
    }

    public static ItemRepository getInstance(Application application){
        if (INSTANCE == null){
            INSTANCE = new ItemRepository(application);
        }
        return INSTANCE;
    }

    public Integer getCurrentListId() {
        return currentListId;
    }

    public void setCurrentListId(Integer currentListId) {
        this.currentListId = currentListId;
    }

    /**
     * Esegue la query per ottenere tutti gli oggetti contenuti in una lista e ne incapsula il contenuto
     * in un LiveData
     */
    public void loadItemsFromList(){
        //Una volta qua aveva un parametro chiamato int listId
        Log.d(LOG_TAG, "Current ListId: " + currentListId);
        itemsInList = listDAO.getItemsFromList(currentListId);
    }

    /**
     * Restituisce il LiveData contenente la lista di oggetti in una specifica lista della spesa
     * @return itemsInList
     */
    public LiveData<List<ItemEntity>> getItemsInList(){
        return itemsInList;
    }

    /**
     * Elimina dal database (da tutte le tabelle) un dato oggetto
     * @param item l'oggetto da eliminare dal database
     */
    public void deleteItem(ItemEntity item){
        UserDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                itemDAO.deleteItem(item);
                listDAO.removeItemFromAllLists(item.getId());
            }
        });
    }


    public LiveData<List<ItemEntity>> getAllItems(){
        return allItemsList;
    }

    public void addItemTolist(ItemEntity itemEntity){
        UserDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                Log.d(LOG_TAG, "itemId: " + itemEntity.getId() + "; listId: " + currentListId);
                //listDAO.addItemToList(itemEntity.getId(),currentListId);
                listDAO.addItemToList(new ListAndItemCrossRef(currentListId, itemEntity.getId()));
            }
        });
    }

    public void addItemToDatabase(ItemEntity itemEntity){
        UserDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                Log.d(LOG_TAG, "itemEntity to insert: " + itemEntity.toString());
                itemDAO.insertItem(itemEntity);
            }
        });
    }

    public void removeFromList(ItemEntity itemEntity) {
        UserDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                Log.d(LOG_TAG, "itemId: " + itemEntity.getId() + "; listId: " + currentListId);
                //listDAO.addItemToList(itemEntity.getId(),currentListId);
                listDAO.removeItemFromList(new ListAndItemCrossRef(currentListId, itemEntity.getId()));
            }
        });
    }

    public List<ItemEntity> getAllItemsInList(ListEntity listEntity){
        Future<List<ItemEntity>> tmpFuture = UserDatabase.executor.submit(new Callable<List<ItemEntity>>() {
            @Override
            public List<ItemEntity> call() throws Exception {
                return listDAO.getItemsFromListToShare(listEntity.getListId());
            }
        });
        List<ItemEntity> listTemp = new ArrayList<>();
        try {
            listTemp = tmpFuture.get();
        }catch (Exception ex){
            Log.e(LOG_TAG + " - login method", "Future variable isn't ready yet");
        }
        return listTemp;
    }
}
