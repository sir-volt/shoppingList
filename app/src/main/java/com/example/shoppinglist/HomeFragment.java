package com.example.shoppinglist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.RecyclerView.ListAdapter;
import com.example.shoppinglist.RecyclerView.ShoppingListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final String LOG_TAG = "Home Fragment";
    private ShoppingListAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            setRecyclerView(activity);
            FloatingActionButton actionButton = view.findViewById(R.id.fab_add_list);

            actionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Utilities.insertFragment((AppCompatActivity) activity,new AddFragment(), AddFragment.class.getSimpleName());
                    //TODO usare lo stesso metodo per il Context usato in signupfragment
                    final View customDialog = getLayoutInflater().inflate(R.layout.custom_dialog, null);
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                    dialogBuilder.setTitle(R.string.enter_list_name);
                    dialogBuilder.setView(customDialog);
                    final EditText newListName = new EditText(getContext());
                    newListName.setInputType(InputType.TYPE_CLASS_TEXT);
                    //dialogBuilder.setView(newListName);
                    dialogBuilder.setPositiveButton(R.string.done, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //TODO
                            EditText et = (EditText) customDialog.findViewById(R.id.dialog_et);
                            Log.d(LOG_TAG, "Nome lista: " + et.getText());
                            ShoppingList newList = new ShoppingList(et.getText().toString());
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

    private void setRecyclerView(final Activity activity) {
        recyclerView = activity.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        List<ShoppingList> shoppingLists = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            shoppingLists.add(new ShoppingList("Nome Lista"));
        }
        adapter = new ShoppingListAdapter(shoppingLists, activity);
        recyclerView.setAdapter(adapter);
    }
}
