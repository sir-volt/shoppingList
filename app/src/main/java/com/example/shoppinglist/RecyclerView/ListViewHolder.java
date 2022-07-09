package com.example.shoppinglist.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.R;

public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView itemImageView;
    TextView itemNameTextView;
    TextView itemPriceTextView;

    private OnItemListener itemListener;


    /*public ListViewHolder(@NonNull View itemView, OnItemListener listener) {
        super(itemView);
        itemImageView = itemView.findViewById(R.id.list_item_icon);
        itemNameTextView = itemView.findViewById(R.id.list_item_text);
        itemPriceTextView = itemView.findViewById(R.id.list_price_text);
        itemListener = listener;
        AppCompatImageButton removeOrAddToCartButton = (AppCompatImageButton) itemView.findViewById(R.id.add_or_remove_button);
        removeOrAddToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeOrAddToCartButton.setSelected(!removeOrAddToCartButton.isSelected());

                if(removeOrAddToCartButton.isSelected()){
                    removeOrAddToCartButton.setImageResource(R.drawable.ic_outline_cancel_24);
                } else {
                    removeOrAddToCartButton.setImageResource(R.drawable.ic_baseline_add_24);
                }
            }
        });

        itemView.setOnClickListener(this);
    }*/

    /**
     * Questa versione è per gli oggetti interni ad una lista. Probabilmente la sposterò
     * @param itemView
     * @param listener
     */
    public ListViewHolder(@NonNull View itemView, OnItemListener listener){
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
    /*
            AppCompatImageButton removeOrAddToCartButton = (AppCompatImageButton) itemLayout.findViewById(R.id.add_or_remove_button);
            removeOrAddToCartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeOrAddToCartButton.setSelected(!removeOrAddToCartButton.isSelected());

                    if(removeOrAddToCartButton.isSelected()){
                        removeOrAddToCartButton.setImageResource(R.drawable.ic_outline_cancel_24);
                    } else {
                        removeOrAddToCartButton.setImageResource(R.drawable.ic_baseline_add_24);
                    }
                }
            });*/

    @Override
    public void onClick(View view) {
        itemListener.onItemClick(getAdapterPosition());
    }
}
