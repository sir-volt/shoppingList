package com.example.shoppinglist.DataBase;

import android.app.Application;
import android.util.Log;

import com.example.shoppinglist.UserEntity;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class UserRepository {

    private static final String LOG_TAG = "UserRepository";
    private final UserDAO userDAO;
    private final ItemDAO itemDAO;


    static int tmp;
    static volatile UserEntity userTemp;
    static Boolean isEmailTaken;
    static List<String> userEntityList;

    public UserRepository(Application application){
        UserDatabase db = UserDatabase.getDatabase(application);
        userDAO = db.userDAO();
        itemDAO = db.itemDAO();
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
                userEntityList = userDAO.getAllUserEmail();
            }
        });
        return userEntityList;
    }

    //TODO convertire questo metodo e quello di signup all'utilizzo di FUTURE
    public Boolean isTaken(String email){
        Log.d(LOG_TAG, "Email to use in query: " + email);
        UserDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                Log.d("INSIDE EXECUTOR", "Email to exe: " + email);

                isEmailTaken = userDAO.isTaken(email);

                if(isEmailTaken==null){
                    Log.d("INSIDE EXECUTOR", "Query returned null, switching to false...");
                    isEmailTaken=false;
                }
                Log.d("INSIDE EXECUTOR", "Query eseguita, il risultato è " + isEmailTaken);
            }

        });
        return isEmailTaken;
    }

     public UserEntity login(String email, String password){
        Log.d(LOG_TAG, "Data to use in query: " + email + " : " +password);
         Future<UserEntity> tmpFuture = UserDatabase.executor.submit(new Callable<UserEntity>() {
             @Override
             public UserEntity call() throws Exception {
                 return userDAO.login(email,password);
             }
         });
         try {
            userTemp = tmpFuture.get();
         }catch (Exception ex){
             Log.e(LOG_TAG + " - login method", "Future variable isn't ready yet");
         }

        return userTemp;
    }
}
