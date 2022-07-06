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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.RecyclerView.ListAdapter;
import com.example.shoppinglist.RecyclerView.OnItemListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements OnItemListener {

    private ListAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_items_to_buy, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Activity activity = getActivity();
        if(activity != null){
            Utilities.setUpToolbar((AppCompatActivity) activity, "List of items");
            setRecyclerView(activity);

            Button addItemButton = view.findViewById(R.id.addproduct);
            addItemButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utilities.insertFragment((AppCompatActivity) activity,new AddFragment(), AddFragment.class.getSimpleName());
                }
            });

            /*
            View itemLayout = view.findViewById(R.id.item_layout);
            AppCompatImageButton removeOrAddToCartButton = (AppCompatImageButton) itemLayout.findViewById(R.id.add_or_remove_button);
            removeOrAddToCartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeOrAddToCartButton.setSelected(!removeOrAddToCartButton.isSelected());

                    if(removeOrAddToCartButton.isSelected()){
                        removeOrAddToCartButton.setImageResource(R.drawable.ic_outline_cancel_24);
                    } else {
                        removeOrAddToCartButton.setImageResource(R.drawable.ic_baseline_add_24);
                    }
                }
            });*/

        }else{
            Log.e("HomeFragment","Activity is Null");
        }

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem item = menu.findItem(R.id.app_bar_search);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) item.getActionView();
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

    private void setRecyclerView(final Activity activity) {
        recyclerView = activity.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        List<ItemEntity> itemList = new ArrayList<>();
        for(int i = 0; i < 7; i++){
            itemList.add(new ItemEntity("ic_baseline_settings_24","generic Item",
                    "Correct Price"));
        }
        for(int i = 0; i < 3; i++){
            itemList.add(new ItemEntity("ic_baseline_add_a_photo_24","special item",
                    "Big Money"));
        }
        final OnItemListener listener = this;
        adapter = new ListAdapter(listener, itemList, activity);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {

    }
}
