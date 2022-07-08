package com.example.shoppinglist.RecyclerView;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shoppinglist.R;

public class ShoppingListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView listNameTextview;
    TextView remainingTextview;

    private OnItemListener listener;

    //VECCHIO COSTRUTTORE, NON FUNZIONA CON IL DATABASE
    public ShoppingListViewHolder(@NonNull View itemView) {
        super(itemView);
        listNameTextview = itemView.findViewById(R.id.list_name_textview);
        remainingTextview = itemView.findViewById(R.id.remaining_textview);
    }

    //NUOVO COSTRUTTORE, SUPPORTA LE LISTE PRESE DAL DATABASE
    public ShoppingListViewHolder(@NonNull View itemView, OnItemListener listener) {
        super(itemView);
        listNameTextview = itemView.findViewById(R.id.list_name_textview);
        remainingTextview = itemView.findViewById(R.id.remaining_textview);
        this.listener = listener;
    }


    @Override
    public void onClick(View view) {
        listener.onItemClick(getAdapterPosition());
    }
}
