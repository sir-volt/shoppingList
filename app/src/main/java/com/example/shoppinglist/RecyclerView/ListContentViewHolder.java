package com.example.shoppinglist.RecyclerView;

import android.app.Application;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
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
    CheckBox checkBox;

    private OnItemListener itemListener;

    private ItemRepository repository;

    /**
     * Questa è la versione per gli oggetti contenuti in una lista
     * @param itemView itemView
     * @param listener listener
     * @param adapter adapter
     */
    public ListContentViewHolder(@NonNull View itemView, OnItemListener listener, ListContentAdapter adapter) {
        super(itemView);
        itemImageView = itemView.findViewById(R.id.list_item_icon);
        itemNameTextView = itemView.findViewById(R.id.list_item_text);
        itemPriceTextView = itemView.findViewById(R.id.list_price_text);
        checkBox = itemView.findViewById(R.id.check_item);
        this.itemListener = listener;
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Necessario per resettare le checkbox riutilizzate (era un bugghino)
                if(checkBox.isChecked()){
                    checkBox.setChecked(false);
                }
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
