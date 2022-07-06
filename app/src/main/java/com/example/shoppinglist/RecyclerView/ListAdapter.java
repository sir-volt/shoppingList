package com.example.shoppinglist.RecyclerView;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.ItemEntity;
import com.example.shoppinglist.R;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListViewHolder> implements Filterable {
    private final List<ItemEntity> itemList;
    Activity activity;
    private OnItemListener listener;

    private List<ItemEntity> itemListNotFiltered;


    public ListAdapter(OnItemListener listener, List<ItemEntity> itemList, Activity activity) {
        this.listener = listener;
        this.itemList = new ArrayList<>(itemList);
        this.itemListNotFiltered = new ArrayList<>(itemList);
        this.activity = activity;
    }


    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ListViewHolder(layoutView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        ItemEntity currentCard = itemList.get(position);

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


    @Override
    public Filter getFilter() {
        return listFilter;
    }

    private Filter listFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<ItemEntity> filteredList = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0){
                filteredList.addAll(itemListNotFiltered);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for(ItemEntity item: itemListNotFiltered){
                    if(item.getItemName().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            List<ItemEntity> filteredList = new ArrayList<>();
            List<?> result = (List<?>) filterResults.values;
            for(Object obj: result){
                if(obj instanceof ItemEntity){
                    filteredList.add((ItemEntity) obj);
                }
            }

            updateListItems(filteredList);
        }
    };

    private void updateListItems(List<ItemEntity> filteredResults) {
        final ListItemDiffCallback diffCallback =
                new ListItemDiffCallback(this.itemList, filteredResults);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.itemList.clear();
        this.itemList.addAll(filteredResults);
        diffResult.dispatchUpdatesTo(this);
    }
}
