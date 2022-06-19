package com.example.shoppinglist.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.shoppinglist.UserEntity;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Dovrei cambiargli il nome, userdatabase è un po' limitante dato che ci staranno anche gli articoli
@Database(entities = {UserEntity.class}, version=1)
public abstract class UserDatabase extends RoomDatabase {

    private static final String dbName = "listappdatabase";
    private static UserDatabase INSTANCE;
    static final ExecutorService executor = Executors.newFixedThreadPool(4);

    public static UserDatabase getUserDatabase(Context context){

        if(INSTANCE == null){
            synchronized (UserDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), UserDatabase.class, dbName).build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract UserDAO userDAO();
}
