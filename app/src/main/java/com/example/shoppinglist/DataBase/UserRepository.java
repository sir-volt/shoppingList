package com.example.shoppinglist.DataBase;

import android.app.Application;

import com.example.shoppinglist.UserEntity;

public class UserRepository {

    private final UserDAO userDAO;

    public UserRepository(Application application){
        UserDatabase db = UserDatabase.getDatabase(application);
        userDAO = db.userDAO();

    }

    public void registerUser(UserEntity newUser){
        UserDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                userDAO.registerUser(newUser);
            }
        });
    }
}
