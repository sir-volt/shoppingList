package com.example.shoppinglist.RecyclerView;

import android.app.Application;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.DataBase.ItemRepository;
import com.example.shoppinglist.ItemEntity;
import com.example.shoppinglist.R;

public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String LOG_TAG = "ListViewHolder";

    ImageView itemImageView;
    TextView itemNameTextView;
    TextView itemPriceTextView;

    private OnItemListener itemListener;

    private ItemRepository repository;

    /**
     * Questa versione è per gli oggetti interni ad una lista. Probabilmente la sposterò
     * @param itemView
     * @param listener
     */
    //Todo posso passargli adapter, per fare  getItemSelected(int position)?
    public ListViewHolder(@NonNull View itemView, OnItemListener listener, ListAdapter adapter){
        super(itemView);
        itemImageView = itemView.findViewById(R.id.list_item_icon);
        itemNameTextView = itemView.findViewById(R.id.list_item_text);
        itemPriceTextView = itemView.findViewById(R.id.list_price_text);
        this.itemListener = listener;
        itemView.setOnClickListener(this);
        itemView.findViewById(R.id.add_or_remove_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(itemView.getContext(), itemNameTextView.getText().toString() + "; item pos: " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                ItemEntity itemEntity = adapter.getItemSelected(getAdapterPosition());
                Log.d(LOG_TAG,"Item Selected: " + itemEntity.toString());
                //adapter.insertItemInList(itemEntity);
                repository = ItemRepository.getInstance((Application) itemView.getContext().getApplicationContext());
                repository.addItemTolist(itemEntity);
            }
        });
        /*itemView.findViewById(R.id.check_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO...
            }
        });*/
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
