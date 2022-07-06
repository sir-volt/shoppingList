package com.example.shoppinglist;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Utilities {

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
            activity.getSupportActionBar().setTitle(title);
        }
    }
}
