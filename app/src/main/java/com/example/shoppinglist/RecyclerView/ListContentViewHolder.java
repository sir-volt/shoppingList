package com.example.shoppinglist.RecyclerView;

import android.app.Application;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.DataBase.ItemRepository;
import com.example.shoppinglist.ItemEntity;
import com.example.shoppinglist.R;

public class ListContentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String LOG_TAG = "ListViewHolder";

    ImageView itemImageView;
    TextView itemNameTextView;
    TextView itemPriceTextView;

    private OnItemListener itemListener;

    private ItemRepository repository;

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

    public ListContentViewHolder(@NonNull View itemView, OnItemListener listener, ListContentAdapter adapter) {
        super(itemView);
        itemImageView = itemView.findViewById(R.id.list_item_icon);
        itemNameTextView = itemView.findViewById(R.id.list_item_text);
        itemPriceTextView = itemView.findViewById(R.id.list_price_text);
        this.itemListener = listener;
        itemView.findViewById(R.id.check_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(itemView.getContext(), itemNameTextView.getText().toString() + "; item pos: " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                ItemEntity itemEntity = adapter.getItemSelected(getAdapterPosition());
                Log.d(LOG_TAG,"Item Selected: " + itemEntity.toString());
                repository = ItemRepository.getInstance((Application) itemView.getContext().getApplicationContext());
                repository.removeFromList(itemEntity);

            }
        });
    }



    @Override
    public void onClick(View view) {
        itemListener.onItemClick(getAdapterPosition());
    }
}
