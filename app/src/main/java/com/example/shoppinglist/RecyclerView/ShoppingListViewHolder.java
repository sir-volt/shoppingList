package com.example.shoppinglist.RecyclerView;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shoppinglist.R;

public class ShoppingListViewHolder extends RecyclerView.ViewHolder {
    TextView listNameTextview;
    TextView remainingTextview;

    public ShoppingListViewHolder(@NonNull View itemView) {
        super(itemView);
        listNameTextview = itemView.findViewById(R.id.list_name_textview);
        remainingTextview = itemView.findViewById(R.id.remaining_textview);
    }
}
