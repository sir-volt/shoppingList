package com.example.shoppinglist.DataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.shoppinglist.ItemEntity;

import java.util.List;

@Dao
public interface ItemDAO {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertItem(ItemEntity itemEntity);

    @Transaction
    @Query("SELECT * FROM items ORDER BY item_name")
    LiveData<List<ItemEntity>> getAllItems();

    @Delete
    void deleteItem(ItemEntity itemEntity);

}
