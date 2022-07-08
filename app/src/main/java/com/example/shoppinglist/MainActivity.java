package com.example.shoppinglist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        session = new Session(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null)
            Utilities.insertFragment(this, new HomeFragment(), HomeFragment.class.getSimpleName());

        Boolean isLoggedIn = session.getLoginStatus();
        Log.d(LOG_TAG, "isLoggedIn: " + isLoggedIn);
        if (!isLoggedIn) {
            Log.d(LOG_TAG, "User is not logged in, redirecting to LoginSignupActivity...");
            Intent i = new Intent(getApplicationContext(), LoginSignupActivity.class);
            startActivity(i);

        } else {
            Log.d(LOG_TAG, "User is logged in");

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar, menu);
        return true;
    }

    //Questo metodo servirebbe per aprire i settings/profilo utente
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId() == R.id.app_bar_settings){
            //Intent intent = new Intent(this)
            return true;
        }

        return false;
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            addViewModel.setImageBitmap(imageBitmap);
        }
    }*/
}