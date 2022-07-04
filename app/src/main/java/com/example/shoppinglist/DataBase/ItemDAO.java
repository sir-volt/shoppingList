package com.example.shoppinglist.DataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.shoppinglist.ListItem;

import java.util.List;

@Dao
public interface ItemDAO {

    @Transaction
    @Insert
    void insertItem(ListItem listItem);

    @Transaction
    @Query("SELECT * FROM items ORDER BY item_name")
    LiveData<List<ListItem>> getAllItems();

    @Delete
    void deleteItem(ListItem listItem);

}
