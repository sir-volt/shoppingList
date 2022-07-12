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
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.DataBase.ItemRepository;
import com.example.shoppinglist.RecyclerView.ListAdapter;
import com.example.shoppinglist.RecyclerView.ListContentAdapter;
import com.example.shoppinglist.RecyclerView.OnItemListener;
import com.example.shoppinglist.ViewModel.AddToListViewModel;
import com.example.shoppinglist.ViewModel.AddViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

//TODO Fare sta roba copiando da ListDetailsFragment
public class AddToListFragment extends Fragment implements OnItemListener{

    private static final String LOG_TAG = "AddToListFragment";
    private ListAdapter adapter;
    private RecyclerView recyclerView;
    private ItemRepository itemRepository;
    private AddToListViewModel viewModel;
    private Integer listId;
    private SearchView searchView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        this.listId = getArguments().getInt("listId",0);
        Log.d(LOG_TAG, "ListId ottenuto: " + this.listId);
        itemRepository = ItemRepository.getInstance(getActivity().getApplication());
        itemRepository.setCurrentListId(listId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_items_to_buy, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if(activity != null){
            Utilities.setUpToolbar((AppCompatActivity) activity, activity.getString(R.string.add_item_to_list));

            setRecyclerView(activity);

            viewModel = new ViewModelProvider(activity).get(AddToListViewModel.class);
            viewModel.getAllItems().observe(activity, new Observer<List<ItemEntity>>() {
                @Override
                public void onChanged(List<ItemEntity> itemEntities) {
                    //Possibile errore con adapter
                    adapter.setData(itemEntities);
                }
            });

            //addButton ci manda alla schermata per creare un nuovo oggetto, saveButton ci fa tornare alla lista
            Button addButton = view.findViewById(R.id.addproduct);
            Button saveButton = view.findViewById(R.id.savebutton);
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utilities.insertFragment(activity,new AddFragment(), AddFragment.class.getSimpleName());
                }
            });
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getParentFragmentManager().popBackStack();
                }
            });

        }else{
            Log.e(LOG_TAG,"Activity is Null");
        }

    }

    //Ex onCreateOptionsMenu
    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.app_bar_settings).setVisible(false);

        MenuItem item = menu.findItem(R.id.app_bar_search);
        searchView = (SearchView) item.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
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
                return true;
            }
        });
    }

    /**
     * Method to set the RecyclerView and the relative adapter
     * @param activity the current activity
     */
    private void setRecyclerView(final Activity activity) {
        /*recyclerView = activity.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        List<ItemEntity> itemList = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            itemList.add(new ItemEntity("ic_baseline_settings_24","generic Item",
                    (double) 0));
        }
        adapter = new ListAdapter(new OnItemListener() {
            @Override
            public void onItemClick(int position) {

            }
        }, itemList, activity);
        recyclerView.setAdapter(adapter);*/
        recyclerView = activity.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        //Nuovo tipo di chiamata a ShoppingListAdapter, che fa uso di un listener
        final OnItemListener listener = this;
        adapter = new ListAdapter(listener, activity, viewModel);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {
        Log.d(LOG_TAG,"onItemClick position: " + position);
        Activity activity = getActivity();
        if (activity!=null){
            int itemSelected = adapter.getItemSelected(position).getId();
            viewModel.setItemSelected(adapter.getItemSelected(position));

        }
    }

    //searchView viene collassata quando torno indietro dal fragment per
    // evitare di vederla ancora sul frammento precetente
    @Override
    public void onDestroy() {
        super.onDestroy();
        searchView.setIconified(true);
        searchView.setIconified(true);
    }
}
