package com.example.shoppinglist.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.R;

public class ListContentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ImageView itemImageView;
    TextView itemNameTextView;
    TextView itemPriceTextView;

    private OnItemListener itemListener;

    //NUOVO COSTRUTTORE, SUPPORTA LISTENER
    public ListContentViewHolder(@NonNull View itemView, OnItemListener listener) {
        super(itemView);
        itemImageView = itemView.findViewById(R.id.list_item_icon);
        itemNameTextView = itemView.findViewById(R.id.list_item_text);
        itemPriceTextView = itemView.findViewById(R.id.list_price_text);
        this.itemListener = listener;
        itemView.findViewById(R.id.check_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO...
            }
        });
    }



    @Override
    public void onClick(View view) {
        itemListener.onItemClick(getAdapterPosition());
    }
}
