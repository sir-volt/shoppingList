package com.example.shoppinglist.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.shoppinglist.ItemEntity;
import com.example.shoppinglist.ListAndItemCrossRef;
import com.example.shoppinglist.ListEntity;
import com.example.shoppinglist.UserEntity;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Dovrei cambiargli il nome, userdatabase è un po' limitante dato che ci staranno anche gli articoli
@Database(entities = {UserEntity.class, ListEntity.class, ItemEntity.class, ListAndItemCrossRef.class}, version=2)
public abstract class UserDatabase extends RoomDatabase {

    private static final String dbName = "listappdatabase";
    private static volatile UserDatabase INSTANCE;
    static final ExecutorService executor = Executors.newFixedThreadPool(4);
    public abstract UserDAO userDAO();
    public abstract ItemDAO itemDAO();
    public abstract ListDAO listDAO();

    public static UserDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            //Synchronized per evitare istanze multiple
            synchronized (UserDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), UserDatabase.class, dbName).build();
                    //TODO aggiungere prima di .build() la chiamata a .createFromAsset("database/myapp.db") per popolarlo
                }
            }
        }
        return INSTANCE;
    }

}
