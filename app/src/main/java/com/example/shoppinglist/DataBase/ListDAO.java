package com.example.shoppinglist.DataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.shoppinglist.ListEntity;
import com.example.shoppinglist.ListWithItems;
import com.example.shoppinglist.UserWithLists;

import java.util.List;

@Dao
public interface ListDAO {

    @Transaction
    @Insert
    void insertList(ListEntity newList);

    @Transaction
    @Query("SELECT * FROM lists WHERE user_creator_id=(:userId) ORDER BY list_name")
    LiveData<List<ListEntity>> getAllListsFromUser(int userId);
    //List<ListEntity> getAllListsFromUser(int userId);

    /*
    * Questo metodo utilizza la classe UserWithLists,
    * che sarebbe la classe data dalla relazione 1-N fra users e lists
    * */
    @Transaction
    @Query("SELECT * FROM users WHERE user_id=(:userId)")
    LiveData<UserWithLists> getListsFromUser(int userId);
    //UserWithLists getListsFromUser(int userId);

    /*
     * Questo metodo utilizza la classe ListWithItems,
     * che sarebbe la classe data dalla relazione N-M fra lists e items
     * */
    @Transaction
    @Query("SELECT * FROM lists WHERE list_id=(:listId)")
    LiveData<ListWithItems> getItemsFromList(int listId);

    @Delete
    void deleteList(ListEntity listEntity);
}
