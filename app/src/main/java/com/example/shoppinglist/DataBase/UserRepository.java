package com.example.shoppinglist.DataBase;

import android.app.Application;
import android.os.AsyncTask;
import android.service.autofill.UserData;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.shoppinglist.UserEntity;

import java.util.List;

public class UserRepository {

    private static final String LOG_TAG = "UserRepository";
    private final UserDAO userDAO;


    static int tmp;
    static UserEntity userTemp;
    static Boolean isEmailTaken;
    static List<String> userEntityList;

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

    public List<String> getUserList(){
        UserDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                userEntityList = userDAO.getAllUsers();
            }
        });
        return userEntityList;
    }

    /*public boolean checkEmail(String email){
        Log.e(LOG_TAG, "Email to check is: " + email);
        UserDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                tmp = userDAO.inDatabase(email);
            }
        });
        Log.e(LOG_TAG, "N. OF ROWS: " + tmp);
        if (tmp==0){
            Log.e(LOG_TAG, "Email is valid");
            return false;
        } else {
            Log.e(LOG_TAG, "Email was found");
            return true;
        }
    }*/

    /*public UserEntity inDatabase(String email){
        Log.e(LOG_TAG, "Email to use in query: " + email);
        //final UserEntity userTemp;

        UserDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                Log.e("INSIDE EXECUTOR", "Email to exe: " + email);
                userTemp = userDAO.isTaken(email);
            }

        });
        return userTemp;
    }*/

    public Boolean isTaken(String email){
        Log.e(LOG_TAG, "Email to use in query: " + email);
        UserDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                Log.e("INSIDE EXECUTOR", "Email to exe: " + email);

                isEmailTaken = userDAO.isTaken(email);

                if(isEmailTaken==null){
                    Log.e("INSIDE EXECUTOR", "Query returned null, switching to false...");
                    isEmailTaken=false;
                }
                Log.e("INSIDE EXECUTOR", "Query eseguita, il risultato è " + isEmailTaken);
            }

        });
        return isEmailTaken;
    }
}
