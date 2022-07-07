package com.example.shoppinglist.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.shoppinglist.DataBase.UserRepository;
import com.example.shoppinglist.ListEntity;

import java.util.List;

public class ListViewModel extends AndroidViewModel {

    private final MutableLiveData<ListEntity> listSelected = new MutableLiveData<>();

    public LiveData<List<ListEntity>> shoppingLists;

    public ListViewModel(@NonNull Application application) {
        super(application);
        UserRepository repository = new UserRepository(application);
        shoppingLists = repository.getAllListsFromUser();

    }

    public LiveData<List<ListEntity>> getShoppingLists(){
        return shoppingLists;
    }

    public MutableLiveData<ListEntity> getListSelected(){
        return listSelected;
    }

    public void setListSelected(ListEntity listEntity){
        listSelected.setValue(listEntity);
    }

}
