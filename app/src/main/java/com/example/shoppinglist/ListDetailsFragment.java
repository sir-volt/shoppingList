package com.example.shoppinglist;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.DataBase.ItemRepository;
import com.example.shoppinglist.RecyclerView.ListAdapter;
import com.example.shoppinglist.RecyclerView.ListContentAdapter;
import com.example.shoppinglist.RecyclerView.OnItemListener;
import com.example.shoppinglist.ViewModel.ItemsInListViewModel;

import java.util.List;

public class ListDetailsFragment extends Fragment implements OnItemListener {

    private static final String LOG_TAG = "ListDetailsFragment";
    private ListContentAdapter adapter;
    private RecyclerView recyclerView;
    private ItemRepository itemRepository;
    private Session session;
    private ItemsInListViewModel itemsInListViewModel;

    private Integer listId;
    private String listName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        //TODO Devo fare un bundle di arguments prima di cambiare fragment e aggiungerlo a transaction.replace come secondo param. (anche nomelista)
        this.listId = getArguments().getInt("listId",0);
        this.listName = getArguments().getString("listName","List Name");
        itemRepository = ItemRepository.getInstance(getActivity().getApplication());
        itemRepository.setCurrentListId(listId);
        session = new Session(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_content, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if(activity != null){
            Utilities.setUpToolbar((AppCompatActivity) activity, this.listName);

            setRecyclerView(activity);

            itemsInListViewModel = new ViewModelProvider(activity).get(ItemsInListViewModel.class);
            itemsInListViewModel.getItemsInList().observe(activity, new Observer<List<ItemEntity>>() {
                @Override
                public void onChanged(List<ItemEntity> itemEntities) {
                    Log.d(LOG_TAG, "Setting up adapter... Item Entities: " + itemEntities.toString());
                    adapter.setData(itemEntities);
                }
            });

        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem item = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) item.getActionView();
        Log.d(LOG_TAG, "Testo inserito nella ricerca: " + searchView.getQuery().toString());
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            /*
             * chiamo questo metodo quando utente fa la query, quando preme un bottone sulla tastiera
             * o quello specifico di invio
             * @param query è il testo della query
             * @return true se la query è stata gestita dal listener, false in caso contrario e faccio
             * sì che SearchView svolga un'azione default
             */
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            /*
             *chiamo questa funzione quando utente modifica testo della query
             * @param newText è il nuovo testo presente nel campo
             * @return true se azione gestita da listener, altrimenti false e quindi
             * SearchView fa l'azione standard
             */
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    /**
     * Method to set the RecyclerView and the relative adapter
     * @param activity the current activity
     */
    private void setRecyclerView(final Activity activity) {
        recyclerView = activity.findViewById(R.id.list_recycler_view);
        recyclerView.setHasFixedSize(true);
        //Nuovo tipo di chiamata a ShoppingListAdapter, che fa uso di un listener
        final OnItemListener listener = this;
        adapter = new ListContentAdapter(listener, activity);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {

    }
}
