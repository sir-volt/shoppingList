package com.example.shoppinglist;


import android.app.Activity;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.InputStream;

public class Utilities {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_CAMERA_USAGE = 100001;

    static void insertFragment(AppCompatActivity activity, Fragment fragment, String tag){
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container_view, fragment, tag);

        if(!(fragment instanceof HomeFragment)){
            transaction.addToBackStack(tag);
        }

        transaction.commit();
    }


    static void setUpToolbar(AppCompatActivity activity, String title){
        ActionBar actionBar = activity.getSupportActionBar();
        if(actionBar == null){
            Toolbar toolbar = new Toolbar(activity);
            activity.setSupportActionBar(toolbar);
        } else {
            activity.getSupportActionBar().setDisplayShowHomeEnabled(false);
            activity.getSupportActionBar().setTitle(title);
        }
    }


    /*
     * questo metodo ci permette di trasformare Drawable in Bitmap
     * se il drawable passato è già un BitMapDrawable, prendiamo il suo bitmap
     * altrimenti creiamo la BitMap usando i dati passati dal Drawable (altezza, larghezza...)
     */
    public static Bitmap drawableToBitmap(Drawable drawable){
        if(drawable instanceof BitmapDrawable){
            return ((BitmapDrawable)drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * Utility Method that get the Bitmap from the URI where the image is stored
     * @param activity activity when the method is executed
     * @param currentPhotoUri the URI for the image to get
     * @return the bitmap contained in the URI
     */
    public static Bitmap getImageBitmap(Activity activity, Uri currentPhotoUri){
        ContentResolver resolver = activity.getApplicationContext()
                .getContentResolver();
        try {
            InputStream stream = resolver.openInputStream(currentPhotoUri);
            Bitmap bitmap = BitmapFactory.decodeStream(stream);
            stream.close();
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
