package com.example.shoppinglist.DataBase;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.shoppinglist.ItemEntity;
import com.example.shoppinglist.ListEntity;
import com.example.shoppinglist.UserEntity;
import com.example.shoppinglist.UserWithLists;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class UserRepository {

    private static final String LOG_TAG = "UserRepository";
    private final UserDAO userDAO;
    private final ItemDAO itemDAO;
    private final ListDAO listDAO;

    private final LiveData<List<ItemEntity>> itemList;
    private LiveData<List<ListEntity>> listEntityList;
    private LiveData<UserWithLists> userWithLists;

    static int tmp;
    static volatile UserEntity userTemp;
    static Boolean isEmailTaken;
    static List<String> userEntityList;

    public UserRepository(Application application){
        UserDatabase db = UserDatabase.getDatabase(application);
        userDAO = db.userDAO();
        itemDAO = db.itemDAO();
        listDAO = db.listDAO();

        itemList = itemDAO.getAllItems();
    }

    /**
     * Costruttore alternativo a cui passo anche l'userID.
     * Principalmente chiamato in ListViewModel per poter effettuare azioni che dipendono dall'userID
     * (come la ricerca di liste create da un dato utente)
     * @param application application
     * @param userId userID da usare nelle query
     */
    public UserRepository(Application application, int userId){
        UserDatabase db = UserDatabase.getDatabase(application);
        userDAO = db.userDAO();
        itemDAO = db.itemDAO();
        listDAO = db.listDAO();
        listEntityList = listDAO.getAllListsFromUser(userId);
        itemList = itemDAO.getAllItems();

    }

    public void registerUser(UserEntity newUser){
        UserDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                userDAO.registerUser(newUser);
            }
        });
    }

    //TODO controllare che non esista già
    public void insertItem(ItemEntity newItem){
        UserDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                itemDAO.insertItem(newItem);
            }
        });
    }

    //TODO rimuovere questo metodo
    public List<String> getUserList(){
        UserDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                userEntityList = userDAO.getAllUserEmail();
            }
        });
        return userEntityList;
    }



    //TODO eliminare il commentato
    public Boolean isTaken(String email){
        Log.d(LOG_TAG, "Email to use in query: " + email);
        /*UserDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                Log.d("INSIDE EXECUTOR", "Email to exe: " + email);

                isEmailTaken = userDAO.isTaken(email);

                if(isEmailTaken==null){
                    Log.d("INSIDE EXECUTOR", "Query returned null, switching to false...");
                    isEmailTaken=false;
                }
                Log.d("INSIDE EXECUTOR", "Query eseguita, il risultato è " + isEmailTaken);
            }

        });*/

        Future<UserEntity> tmpFuture = UserDatabase.executor.submit(new Callable<UserEntity>() {
            @Override
            public UserEntity call() throws Exception {
                return userDAO.inDatabase(email);
            }
        });
        try {
            userTemp = tmpFuture.get();
        }catch (Exception ex){
            Log.e(LOG_TAG + " - isTaken method", "Future variable isn't ready yet");
        }

        if(userTemp == null){
            return false;
        } else {
            return true;
        }

    }

     public UserEntity login(String email, String password){
        Log.d(LOG_TAG, "Data to use in query: " + email + " : " +password);
         Future<UserEntity> tmpFuture = UserDatabase.executor.submit(new Callable<UserEntity>() {
             @Override
             public UserEntity call() throws Exception {
                 return userDAO.login(email,password);
             }
         });
         try {
            userTemp = tmpFuture.get();
         }catch (Exception ex){
             Log.e(LOG_TAG + " - login method", "Future variable isn't ready yet");
         }

        return userTemp;
    }

    public void deleteItem(ItemEntity item){
        UserDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                itemDAO.deleteItem(item);
            }
        });
    }



    public void insertList(ListEntity newList){
        UserDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                listDAO.insertList(newList);
            }
        });
    }

    //Non sono sicuro che questo metodo funzionerà, dovrà essere chiamato dal HomeFragment dopo aver loggato
    //Questo metodo "carica" una lista di liste aventi l'user id fornito.
    public void loadListsFromUser(int userId){
        listEntityList = listDAO.getAllListsFromUser(userId);
    }

    public LiveData<List<ListEntity>> getAllListsFromUser(){
        return listEntityList;
        /*Log.d(LOG_TAG, "Data to query: ID " + userId);
        Future<List<ListEntity>> tmpFuture = UserDatabase.executor.submit(new Callable<List<ListEntity>>() {
            @Override
            public List<ListEntity> call() throws Exception {
                return listDAO.getAllListsFromUser(userId);
            }
        });
        List<ListEntity> tmp = new ArrayList<>();
        try {
            tmp = tmpFuture.get();
        }catch (Exception ex){
            Log.e(LOG_TAG + " - getUserWithLists method", "Future variable isn't ready yet");
        }

        return tmp;*/
    }

    public LiveData<List<ItemEntity>> getAllItems(){
        return itemList;
    }

    public void deleteList(ListEntity list){
        UserDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                int listId = list.getListId();
                listDAO.deleteList(list);
                //TODO rimuovere ogni item di una lista dalla tabella ListWithItems che devo ancora creare
            }
        });
    }

    //Questo metodo "carica" un oggetto di tipo UserWithLists, che descrive la relazione fra 1 utente e le sue N liste
    public void loadUserWithLists(int userId){
        userWithLists = listDAO.getListsFromUser(userId);
    }

    public LiveData<UserWithLists> getUserWithLists(int userId){
        return userWithLists;
        /*Log.d(LOG_TAG, "Data to query: ID " + userId);
        Future<UserWithLists> tmpFuture = UserDatabase.executor.submit(new Callable<UserWithLists>() {
            @Override
            public UserWithLists call() throws Exception {
                return listDAO.getListsFromUser(userId);
            }
        });
        UserWithLists tmp = new UserWithLists();
        try {
            tmp = tmpFuture.get();
        }catch (Exception ex){
            Log.e(LOG_TAG + " - getUserWithLists method", "Future variable isn't ready yet");
        }

        return tmp;*/
    }

}
