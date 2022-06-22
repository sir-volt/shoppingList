package com.example.shoppinglist.DataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.shoppinglist.UserEntity;

import java.util.List;

@Dao
public interface UserDAO {

    @Transaction
    @Insert
    void registerUser(UserEntity userEntity);

    @Query("SELECT * FROM users WHERE email=(:email) and password=(:password)")
    UserEntity login(String email, String password);

    @Query("SELECT * FROM users WHERE email=(:email)")
    UserEntity inDatabase(String email);

    @Query("SELECT EXISTS (SELECT * from users where email=(:email))")
    Boolean isTaken(String email);

    @Query("SELECT email FROM users")
    List<String> getAllUsers();
}
