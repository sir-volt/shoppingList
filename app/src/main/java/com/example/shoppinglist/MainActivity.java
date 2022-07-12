package com.example.shoppinglist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.shoppinglist.ViewModel.AddViewModel;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";
    private Session session;
    private AddViewModel addViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        session = new Session(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null)
            Utilities.insertFragment(this, new HomeFragment(), HomeFragment.class.getSimpleName());

        addViewModel = new ViewModelProvider(this).get(AddViewModel.class);

        Boolean isLoggedIn = session.getLoginStatus();
        Log.d(LOG_TAG, "isLoggedIn: " + isLoggedIn);
        if (!isLoggedIn) {
            Log.d(LOG_TAG, "User is not logged in, redirecting to LoginSignupActivity...");
            Intent i = new Intent(getApplicationContext(), LoginSignupActivity.class);
            //TODO SE NON LOGGO MA VADO INDIETRO, VEDO LE LISTE VUOTE
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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

    /**
     *
     * @param item MenuItem: The menu item that was selected. This value cannot be null.
     * @return false to allow normal menu processing to proceed, true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.app_bar_settings){
            //TODO rimettere questo giusto
            //Intent intent = new Intent(this, SettingsActivity.class);
            Intent intent = new Intent(this, FoodFactsTestActivity.class);
            this.startActivity(intent);
            return true;
        }
        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Utilities.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            if(extras != null){
                Bitmap imageBitMap = (Bitmap) extras.get("data");
                addViewModel.setImageBitMap(imageBitMap);
            }
        }
    }

    /*
    //Questo metodo servirebbe per aprire i settings/profilo utente
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId() == R.id.app_bar_settings){
            //Intent intent = new Intent(this)
            return true;
        }

        return false;
    }*/
}