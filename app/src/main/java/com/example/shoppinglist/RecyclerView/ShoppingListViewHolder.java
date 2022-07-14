package com.example.shoppinglist.RecyclerView;

import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shoppinglist.R;

public class ShoppingListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, View.OnLongClickListener{
    TextView listNameTextview;
    //TextView remainingTextview;

    private final OnItemListener listener;

    //VECCHIO COSTRUTTORE
    /*public ShoppingListViewHolder(@NonNull View itemView) {
        super(itemView);
        listNameTextview = itemView.findViewById(R.id.list_name_textview);
        remainingTextview = itemView.findViewById(R.id.remaining_textview);
    }*/

    //NUOVO COSTRUTTORE, SUPPORTA LISTENER
    public ShoppingListViewHolder(@NonNull View itemView, OnItemListener listener) {
        super(itemView);
        listNameTextview = itemView.findViewById(R.id.list_name_textview);
        //remainingTextview = itemView.findViewById(R.id.remaining_textview);
        this.listener = listener;
        itemView.setOnCreateContextMenuListener(this);
        itemView.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        listener.onItemClick(getAdapterPosition());
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.add(Menu.NONE, R.id.option_share_list, Menu.NONE, R.string.share_list);
        contextMenu.add(Menu.NONE, R.id.option_delete_list, Menu.NONE, R.string.delete_list);
    }

    @Override
    public boolean onLongClick(View view) {
        return listener.onItemLongClick(getAdapterPosition());
    }
}
