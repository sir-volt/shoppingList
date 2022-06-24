package com.example.shoppinglist.RecyclerView;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.ListItem;
import com.example.shoppinglist.R;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListViewHolder> {
    private final  List<ListItem> itemList;
    Activity activity;

    public ListAdapter(List<ListItem> itemList, Activity activity) {
        this.itemList = itemList;
        this.activity = activity;
    }


    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ListViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        ListItem currentCard = itemList.get(position);

        holder.itemNameTextView.setText(currentCard.getItemName());
        holder.itemPriceTextView.setText(currentCard.getItemPrice());

        String image = currentCard.getImageResource();
        //al momento abbiamo solo drawable, in futuro ci saranno foto, questo if è per mettere i placeholder draawable
        if(image.contains("ic_")){
            Drawable drawable = AppCompatResources.getDrawable(activity, activity.getResources()
                    .getIdentifier(image, "drawable",activity.getPackageName()));
            holder.itemImageView.setImageDrawable(drawable);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
