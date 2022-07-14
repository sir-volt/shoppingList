package com.example.shoppinglist.DataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.shoppinglist.ItemEntity;
import com.example.shoppinglist.ListAndItemCrossRef;
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

    /*
    * Questo metodo utilizza la classe UserWithLists,
    * che sarebbe la classe data dalla relazione 1-N fra users e lists
    * */
    @Transaction
    @Query("SELECT * FROM users WHERE user_id=(:userId)")
    LiveData<UserWithLists> getListsFromUser(int userId);
    //UserWithLists getListsFromUser(int userId);


    @Transaction
    @Query("SELECT items.item_id, items.item_name, items.image, items.price " +
            "FROM items,list_item_cross_ref " +
            "WHERE list_item_cross_ref.list_id=(:listId) AND list_item_cross_ref.item_id=items.item_id")
    LiveData<List<ItemEntity>> getItemsFromList(int listId);

    @Transaction
    @Query("SELECT items.item_id, items.item_name, items.image, items.price " +
            "FROM items,list_item_cross_ref " +
            "WHERE list_item_cross_ref.list_id=(:listId) AND list_item_cross_ref.item_id=items.item_id")
    List<ItemEntity> getItemsFromListToShare(int listId);


    /*
     * Questo metodo utilizza la classe ListWithItems,
     * che sarebbe la classe data dalla relazione N-M fra lists e items
     * */
    @Transaction
    @Query("SELECT * FROM lists WHERE list_id=(:listId)")
    LiveData<ListWithItems> getListWithItems(int listId);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addItemToList(ListAndItemCrossRef listAndItem);

    @Delete
    void removeItemFromList(ListAndItemCrossRef listAndItem);

    @Query("DELETE FROM list_item_cross_ref WHERE item_id=(:itemId)")
    void removeItemFromAllLists(int itemId);

    @Delete
    void deleteList(ListEntity listEntity);

    @Query("DELETE FROM lists WHERE list_id=(:listId)")
    void deleteListById(int listId);

    @Query("DELETE FROM list_item_cross_ref WHERE list_id=(:listId)")
    void deleteListContent(int listId);
}
