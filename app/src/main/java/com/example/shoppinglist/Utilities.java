package com.example.shoppinglist;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import pl.coderion.model.Product;
import pl.coderion.model.ProductResponse;
import pl.coderion.service.OpenFoodFactsWrapper;
import pl.coderion.service.impl.OpenFoodFactsWrapperImpl;

public class Utilities {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1001;
    static final int REQUEST_CAMERA_USAGE = 100001;
    static final int RESULT_OK = 11;
    static final int RESULT_FAIL = 12;
    static final String BARCODE_SCAN_RESULT = "barcodeScan";

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

    static String checkProductCode(String productCode){
        String LOG_TAG = "checkProductCode";
        OpenFoodFactsWrapper wrapper;
        ProductResponse productResponse;
        Product product;

        wrapper = new OpenFoodFactsWrapperImpl();
        productResponse = wrapper.fetchProductByCode(productCode);

        if (!productResponse.isStatus()) {
            Log.d(LOG_TAG,"Status: " + productResponse.getStatusVerbose());
            return null;
        }

        product = productResponse.getProduct();

        Log.d(LOG_TAG, "prodotto ottenuto: " + product.getProductName() + "; img: " + product.getImageUrl());
        return product.getProductName();
    }

    /**
     * Condivide una data lista creando l'intent
     * @param listName nome della lista
     * @param listToShare lista di ItemEntity da scrivere
     * @param context context
     */
    static void shareList(String listName, List<ItemEntity> listToShare, Context context){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        String readableList = convertItemList(listName, listToShare, context);
        shareIntent.putExtra(Intent.EXTRA_TEXT, readableList);
        shareIntent.setType("text/plain");
        if (context != null && shareIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(Intent.createChooser(shareIntent, null));
        }
    }

    /**
     * Converte una lista di oggetti in una stringa formattata pronta per essere condivisa
     * @param listName nome lista
     * @param listToShare lista di ItemEntity da scrivere
     * @param context context
     * @return una stringa ben formattata contenente nome lista, nome degli oggetti e il loro prezzo
     */
    private static String convertItemList(String listName, List<ItemEntity> listToShare, Context context){
        String readableList;
        StringBuilder builder = new StringBuilder();
        builder.append(context.getString(R.string.list) + ": " + listName +"\n\n");
        for (ItemEntity item : listToShare){
            builder.append(item.getItemName() + " : " + item.getItemPrice().toString() + "€; \n");
        }
        readableList = builder.toString();
        Log.d("ConvertItemList", readableList);
        return readableList;
    }
}
