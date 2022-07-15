package com.example.shoppinglist.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.shoppinglist.DataBase.UserRepository;
import com.example.shoppinglist.ListEntity;
import com.example.shoppinglist.Session;
import com.example.shoppinglist.UserWithLists;

import java.util.List;

/*
* ViewModel relativo alla lista/elenco di Liste della spesa (NON relativo a una singola lista di oggetti)
* */
public class ListViewModel extends AndroidViewModel {

    private static final String LOG_TAG = "ListViewModel";

    private final MutableLiveData<ListEntity> listSelected = new MutableLiveData<>();

    public LiveData<List<ListEntity>> shoppingLists;

    private UserRepository repository;


    public ListViewModel(@NonNull Application application) {
        super(application);
        Session session = new Session(application);
        //Questo non funziona perchè non è l'istanza di repository nella quale sono state caricate le cose
        Log.d(LOG_TAG, "User ID da cercare: " + session.getUserId());
        repository = new UserRepository(application, session.getUserId());
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

    public void deleteList(ListEntity selectedList) {
        repository.deleteList(selectedList);
    }
}
