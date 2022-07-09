package com.example.shoppinglist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import androidx.appcompat.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.DataBase.UserRepository;
import com.example.shoppinglist.RecyclerView.ShoppingListAdapter;
import com.example.shoppinglist.ViewModel.ListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.shoppinglist.RecyclerView.ListAdapter;
import com.example.shoppinglist.RecyclerView.OnItemListener;


import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements OnItemListener {

    private static final String LOG_TAG = "Home Fragment";
    private ShoppingListAdapter adapter;
    private RecyclerView recyclerView;
    private UserRepository repository;
    private Session session;
    private ListViewModel listViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        repository = new UserRepository(getActivity().getApplication());
        session = new Session(getContext());

        Log.d(LOG_TAG, "onCreate chiamato!");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentActivity activity = getActivity();
        if(activity != null){
            //TODO usare String resources
            Utilities.setUpToolbar((AppCompatActivity) activity, getString(R.string.home_page_name));
            setRecyclerView(activity);

            /*Riferimento ed inizializzazione del nuovo ViewModel (DOPO setup della RecyclerView),
            * facente uso di un observer su una lista
            * */
            listViewModel = new ViewModelProvider(activity).get(ListViewModel.class);
            //listViewModel = new ListViewModel(activity.getApplication(), repository);
            listViewModel.getShoppingLists().observe(activity, new Observer<List<ListEntity>>() {
                @Override
                public void onChanged(List<ListEntity> listEntities) {
                    adapter.setData(listEntities);
                }
            });

            FloatingActionButton actionButton = view.findViewById(R.id.fab_add_list);

            actionButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    //Utilities.insertFragment((AppCompatActivity) activity,new AddFragment(), AddFragment.class.getSimpleName());
                    final View customDialog = getLayoutInflater().inflate(R.layout.custom_dialog, null);
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                    dialogBuilder.setTitle(R.string.enter_list_name);
                    dialogBuilder.setView(customDialog);
                    final EditText newListName = new EditText(getContext());
                    newListName.setInputType(InputType.TYPE_CLASS_TEXT);
                    //TODO introdurre listener per fare si che Invio faccia cliccare il tasto DONE
                    //dialogBuilder.setView(newListName);
                    dialogBuilder.setPositiveButton(R.string.done, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //TODO
                            EditText et = (EditText) customDialog.findViewById(R.id.dialog_et);
                            Log.d(LOG_TAG, "Nome lista: " + et.getText());
                            ListEntity newList = new ListEntity(et.getText().toString());
                            newList.setUserCreatorId(session.getUserId());
                            Log.d(LOG_TAG, "User ID: " + session.getUserId() + " User name: " + session.getUsername() + "Login Status: " +session.getLoginStatus());

                            repository.insertList(newList);
                        }
                    });
                    dialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    dialogBuilder.show();
                }
            });

        }else{
            Log.e(LOG_TAG,"Activity is Null");
        }

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem item = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) item.getActionView();
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
        recyclerView = activity.findViewById(R.id.home_recycler_view);
        recyclerView.setHasFixedSize(true);
        //Nuovo tipo di chiamata a ShoppingListAdapter, che fa uso di un listener
        final OnItemListener listener = this;
        adapter = new ShoppingListAdapter(listener, activity);

        recyclerView.setAdapter(adapter);
    }

    //Definisce il comportamento quando una lista viene cliccata
    @Override
    public void onItemClick(int position) {
        Activity activity = getActivity();
        if (activity!=null){
            //TODO creare un fragment per mostrare il contenuto di una lista (DetailsFragment?) (pagina 43 lez 4)

            Bundle bundle = new Bundle();
            bundle.putInt("listId", adapter.getListSelected(position).getListId());
            bundle.putString("listName", adapter.getListSelected(position).getListName());
            ListDetailsFragment fragment = new ListDetailsFragment();
            fragment.setArguments(bundle);
            Utilities.insertFragment((AppCompatActivity) activity, fragment, ListDetailsFragment.class.getSimpleName());
            listViewModel.setListSelected(adapter.getListSelected(position));
        }
    }
}
