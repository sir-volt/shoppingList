package com.example.shoppinglist.DataBase;


import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.shoppinglist.ItemEntity;
import com.example.shoppinglist.ListWithItems;

import java.util.List;

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
     * Inserisce un nuovo oggetto nel database
     * @param newItem il nuovo oggetto da inserire nel DB
     */
    public void insertItem(ItemEntity newItem){
        //Non controllo che già esiste perché potrebbe esistere con lo stesso nome, ma immagine o prezzo diverso
        UserDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                itemDAO.insertItem(newItem);
            }
        });
    }


    //TODO fare si che elimini da ogni tabella
    /**
     * Elimina dal database (da tutte le tabelle) un dato oggetto
     * @param item l'oggetto da eliminare dal database
     */
    public void deleteItem(ItemEntity item){
        UserDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                itemDAO.deleteItem(item);
            }
        });
    }


    public LiveData<List<ItemEntity>> getAllItems(){
        return allItemsList;
    }

}
