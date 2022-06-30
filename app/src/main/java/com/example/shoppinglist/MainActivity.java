package com.example.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";
    private final Session session;

    public MainActivity() {
        this.session = new Session(getApplicationContext());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO vedere cos'è
        if(savedInstanceState==null)
            Utilities.insertFragment(this, new HomeFragment(),HomeFragment.class.getSimpleName());

        Boolean isLoggedIn = session.getLoginStatus();
        Log.d(LOG_TAG, "isLoggedIn: " + isLoggedIn);
        if(!isLoggedIn){
            Log.d(LOG_TAG, "User is not logged in, redirecting to LoginSignupActivity...");
            Intent i = new Intent(getApplicationContext(), LoginSignupActivity.class);
            startActivity(i);
        } else {
            Log.d(LOG_TAG, "User is logged in");
        }

    }


}