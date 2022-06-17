package com.example.shoppinglist.DataBase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.shoppinglist.UserEntity;

@Dao
public interface UserDAO {

    @Insert
    void registerUser(UserEntity userEntity);

    @Query("SELECT * FROM users WHERE email=(:email) and password=(:password)")
    UserEntity login(String email, String password);
}
