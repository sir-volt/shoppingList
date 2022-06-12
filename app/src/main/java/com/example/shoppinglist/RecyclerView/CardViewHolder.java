package com.example.shoppinglist.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.R;

public class CardViewHolder extends RecyclerView.ViewHolder {

    ImageView itemImageView;
    TextView itemNameTextView;
    TextView itemPriceTextView;


    public CardViewHolder(@NonNull View itemView) {
        super(itemView);
        itemImageView = itemView.findViewById(R.id.item_imageview);
        itemNameTextView = itemView.findViewById(R.id.itemname_textview);
        itemPriceTextView = itemView.findViewById(R.id.itemprice_textview);
    }
}
