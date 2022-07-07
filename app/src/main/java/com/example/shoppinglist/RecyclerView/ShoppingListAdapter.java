package com.example.shoppinglist.RecyclerView;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.ListEntity;
import com.example.shoppinglist.R;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListViewHolder> {
    private List<ListEntity> shoppingLists;
    private List<ListEntity> shoppingListsNotFiltered = new ArrayList<>();  //TODO forse non lo uso
    private Activity activity;
    private OnItemListener listener;

    public ShoppingListAdapter(List<ListEntity> shoppingLists, Activity activity) {
        this.shoppingLists = shoppingLists;
        this.activity = activity;
    }

    public ShoppingListAdapter(OnItemListener listener, Activity activity){
        this.listener = listener;
        this.activity = activity;

    }
    //TODO finire questo
    //TODO trovare un altro nome invece di ListEntity
    @NonNull
    @Override
    public ShoppingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_card_layout, parent, false);
        return new ShoppingListViewHolder(layoutView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListViewHolder holder, int position) {
        ListEntity currentShoppingList = shoppingLists.get(position);
        holder.listNameTextview.setText(currentShoppingList.getListName());
        //holder.remainingTextview.setText(currentShoppingList.getRemainingItems() + "/" + currentShoppingList.getTotalItems());
        holder.remainingTextview.setText(0 + "/" + 10);
        //activity.getApplicationContext().getString(R.string.remaining,currentShoppingList.getRemainingItems(),currentShoppingList.getTotalItems());

    }

    @Override
    public int getItemCount() {
        return shoppingLists.size();
    }

    public ListEntity getListSelected(int position){
        return shoppingLists.get(position);
    }

    public void setData(List<ListEntity> list){
        this.shoppingLists = new ArrayList<>(list);
        this.shoppingListsNotFiltered = new ArrayList<>(list);

        final ListEntityDiffCallback diffCallback = new ListEntityDiffCallback(this.shoppingLists, list);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        diffResult.dispatchUpdatesTo(this);
    }
}
