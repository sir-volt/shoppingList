package com.example.shoppinglist.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.shoppinglist.DataBase.ItemRepository;
import com.example.shoppinglist.ItemEntity;

import java.util.List;

public class AddToListViewModel extends AndroidViewModel {

    private static final String LOG_TAG = "AddToListViewModel";

    private final MutableLiveData<ItemEntity> itemSelected = new MutableLiveData<>();

    private LiveData<List<ItemEntity>> items;

    private ItemRepository repository;


    //TODO add session inside repository maybe that would fix some things
    public AddToListViewModel(@NonNull Application application) {
        super(application);
        Log.d(LOG_TAG, "Sto per chiamare getAllItems");
        repository = ItemRepository.getInstance(application);
        items = repository.getAllItems();
    }

    public LiveData<List<ItemEntity>> getAllItems() {
        return items;
    }

    public MutableLiveData<ItemEntity> getItemSelected() {
        return itemSelected;
    }

    public void setItemSelected(ItemEntity itemEntity){
        itemSelected.setValue(itemEntity);
    }

    public void insertItemInList(ItemEntity itemEntity){
        //repository addtolist
    }

    public void deleteItem(ItemEntity itemEntity){
        repository.deleteItem(itemEntity);
    }
}
