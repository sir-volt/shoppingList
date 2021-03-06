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
import android.view.inputmethod.EditorInfo;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ListDetailsFragment extends Fragment implements OnItemListener {

    private static final String LOG_TAG = "ListDetailsFragment";
    private ListContentAdapter adapter;
    private RecyclerView recyclerView;
    private ItemRepository itemRepository;
    private Session session;
    private ItemsInListViewModel itemsInListViewModel;
    private SearchView searchView;

    private Integer listId;
    private String listName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        this.listId = getArguments().getInt("listId",0);
        this.listName = getArguments().getString("listName","List Name");
        itemRepository = ItemRepository.getInstance(getActivity().getApplication());
        Log.d(LOG_TAG, "ListId da settare: " + listId);
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
        FragmentActivity activity = (AppCompatActivity) getActivity();
        if(activity != null){
            Utilities.setUpToolbar((AppCompatActivity) activity, this.listName);

            setRecyclerView(activity);

            itemsInListViewModel = new ViewModelProvider(this).get(ItemsInListViewModel.class);
            itemsInListViewModel.getItemsInList().observe(activity, new Observer<List<ItemEntity>>() {
                @Override
                public void onChanged(List<ItemEntity> itemEntities) {
                    Log.d(LOG_TAG, "Setting up adapter... Item Entities: " + itemEntities.toString());
                    adapter.setData(itemEntities);
                    //adapter.notifyDataSetChanged();
                }
            });

            FloatingActionButton actionButton = view.findViewById(R.id.fab_add_item_to_list);
            actionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("listId", listId);
                    AddToListFragment fragment = new AddToListFragment();
                    fragment.setArguments(bundle);
                    searchView.setIconified(true);
                    searchView.setIconified(true);
                    Utilities.insertFragment((AppCompatActivity) activity,fragment, AddToListFragment.class.getSimpleName());
                }
            });

        }
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);

        MenuItem item = menu.findItem(R.id.app_bar_search);
        searchView = (SearchView) item.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        //Queste due righe servono per evitare che la searchView vada a schermo intero
        // quando abbiamo il dispositivo in orizzontale
        int options = searchView.getImeOptions();
        searchView.setImeOptions(options| EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        //Log.d(LOG_TAG, "Testo inserito nella ricerca: " + searchView.getQuery().toString());
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            /*
             * chiamo questo metodo quando utente fa la query, quando preme un bottone sulla tastiera
             * o quello specifico di invio
             * @param query ?? il testo della query
             * @return true se la query ?? stata gestita dal listener, false in caso contrario e faccio
             * s?? che SearchView svolga un'azione default
             */
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            /*
             *chiamo questa funzione quando utente modifica testo della query
             * @param newText ?? il nuovo testo presente nel campo
             * @return true se azione gestita da listener, altrimenti false e quindi
             * SearchView fa l'azione standard
             */
            @Override
            public boolean onQueryTextChange(String newText) {
                String lifecycle = getLifecycle().getCurrentState().name();
                Log.d("ListContentAdapter->ListDetailsFragment","Lifecycle: " + lifecycle);

                Log.d("ListContentAdapter->ListDetailsFragment","Called adapter.getFilter with text " + newText);
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
        recyclerView = activity.findViewById(R.id.list_recycler_view);
        recyclerView.setHasFixedSize(true);
        final OnItemListener listener = this;
        adapter = new ListContentAdapter(listener, activity);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public boolean onItemLongClick(int position) {
        return false;
    }

    //searchView viene collassata quando torno indietro dal fragment per
    // evitare di vederla ancora sul frammento precetente
    @Override
    public void onDestroy() {
        if(searchView!=null){
            searchView.setIconified(true);
            searchView.setIconified(true);
        }
        super.onDestroy();
    }
}
