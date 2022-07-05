package com.example.shoppinglist.DataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.shoppinglist.ListEntity;

import java.util.List;

@Dao
public interface ListDAO {

    @Transaction
    @Insert
    void insertList(ListEntity newList);

    @Transaction
    @Query("SELECT * FROM lists WHERE user_id=(:userId) ORDER BY list_name")
    LiveData<List<ListEntity>> getAllListsFromUser(int userId);

    @Delete
    void deleteList(ListEntity listEntity);
}
