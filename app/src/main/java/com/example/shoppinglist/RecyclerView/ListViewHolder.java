package com.example.shoppinglist.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.R;

public class ListViewHolder extends RecyclerView.ViewHolder {

    ImageView itemImageView;
    TextView itemNameTextView;
    TextView itemPriceTextView;


    public ListViewHolder(@NonNull View itemView) {
        super(itemView);
        itemImageView = itemView.findViewById(R.id.list_item_icon);
        itemNameTextView = itemView.findViewById(R.id.list_item_text);
        itemPriceTextView = itemView.findViewById(R.id.list_price_text);
    }
}
