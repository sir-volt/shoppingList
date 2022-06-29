package com.example.shoppinglist.RecyclerView;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.R;
import com.example.shoppinglist.ShoppingList;

import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListViewHolder> {
    private final List<ShoppingList> shoppingLists;
    private final Activity activity;

    public ShoppingListAdapter(List<ShoppingList> shoppingLists, Activity activity) {
        this.shoppingLists = shoppingLists;
        this.activity = activity;
    }
    //TODO finire questo
    //TODO trovare un altro nome invece di ShoppingList
    @NonNull
    @Override
    public ShoppingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_card_layout, parent, false);
        return new ShoppingListViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListViewHolder holder, int position) {
        ShoppingList currentShoppingList = shoppingLists.get(position);
        holder.listNameTextview.setText(currentShoppingList.getListName());
        holder.remainingTextview.setText(currentShoppingList.getRemainingItems() + "/" + currentShoppingList.getTotalItems());
        //activity.getApplicationContext().getString(R.string.remaining,currentShoppingList.getRemainingItems(),currentShoppingList.getTotalItems());

    }

    @Override
    public int getItemCount() {
        return shoppingLists.size();
    }
}
