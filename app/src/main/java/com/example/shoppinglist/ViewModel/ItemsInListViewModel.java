package com.example.shoppinglist.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.shoppinglist.DataBase.UserRepository;
import com.example.shoppinglist.ItemEntity;
import com.example.shoppinglist.ListEntity;
import com.example.shoppinglist.Session;

import java.util.List;


/**
 * ViewModel relativo agli oggetti contenuti dentro a una lista
 */
public class ItemsInListViewModel extends AndroidViewModel {

    private static final String LOG_TAG = "ItemsInListViewModel";

    private final MutableLiveData<ItemEntity> itemSelected = new MutableLiveData<>();

    private LiveData<List<ItemEntity>> itemsInList;


    //TODO add session inside repository maybe that would fix some things
    public ItemsInListViewModel(@NonNull Application application) {
        super(application);
        Session session = new Session(application);
        //Questo non funziona perchè non è l'istanza di repository nella quale sono state caricate le cose
        Log.d(LOG_TAG, "User ID da cercare: " + session.getUserId());
        UserRepository repository = new UserRepository(application, session.getUserId());
       // shoppingLists = repository.getAllListsFromUser();
    }

    public LiveData<List<ItemEntity>> getItemsInList() {
        return itemsInList;
    }

    public MutableLiveData<ItemEntity> getItemSelected() {
        return itemSelected;
    }

    public void setItemSelected(ItemEntity itemEntity){
        itemSelected.setValue(itemEntity);
    }
}
