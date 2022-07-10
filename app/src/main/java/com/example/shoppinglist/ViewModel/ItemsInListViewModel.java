package com.example.shoppinglist.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.shoppinglist.DataBase.ItemRepository;
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
        Log.d(LOG_TAG, "Sto per chiamare loadItemsFromList");
        ItemRepository repository = ItemRepository.getInstance(application);
        repository.loadItemsFromList();
        itemsInList = repository.getItemsInList();
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
