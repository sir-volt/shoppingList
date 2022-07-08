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

    public UserWithLists userWithLists;

    public LiveData<List<ListEntity>> foo;

    public ListViewModel(@NonNull Application application) {
        super(application);
        Session session = new Session(application);
        //Questo non funziona perchè non è l'istanza di repository nella quale sono state caricate le cose
        Log.d(LOG_TAG, "User ID da cercare: " + session.getUserId());
        UserRepository repository = new UserRepository(application, session.getUserId());
        shoppingLists = repository.getAllListsFromUser();

    }

    /*
     *  Costruttore alternativo a cui passo anche un istanza della classe UserRepository, per fare si che
     *  le sue variabili interne siano le stesse dell'istanza creata in HomeFragment
     */
    //So che, usando il metodo SENZA livedata, mi restituisce le Liste come dovrebbe. Ora, perché CON livedata non si aggiorna l'UI?
    //Posso ottenere in LiveData tutte le liste e poi filtrarle dopo?
    public ListViewModel(@NonNull Application application, UserRepository repository) {
        super(application);
        //shoppingLists = repository.getAllListsFromUser();
        //userWithLists = repository.getUserWithLists(2);
        //shoppingLists = userWithLists.getShoppingLists();
        //List<ListEntity> tmp = repository.getAllListsFromUser(2);
        //Log.d(LOG_TAG, tmp.toString());

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
