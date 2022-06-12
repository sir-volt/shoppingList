package com.example.shoppinglist.RecyclerView;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.CardItem;
import com.example.shoppinglist.R;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardViewHolder> {
    private final List<CardItem> cardItemList;
    Activity activity;

    public CardAdapter(List<CardItem> cardList, Activity activity) {
        this.cardItemList = cardList;
        this.activity = activity;
    }


    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_card_layout, parent, false);
        return new CardViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        CardItem currentCard = cardItemList.get(position);

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
        return cardItemList.size();
    }
}
