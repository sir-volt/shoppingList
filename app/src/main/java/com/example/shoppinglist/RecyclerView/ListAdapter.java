package com.example.shoppinglist.RecyclerView;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;
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
import java.util.Locale;

public class ListAdapter extends RecyclerView.Adapter<ListViewHolder> implements Filterable {

    private final static String LOG_TAG = "ListAdapter";
    private List<ItemEntity> itemList = new ArrayList<>();
    private List<ItemEntity> itemListNotFiltered = new ArrayList<>();
    Activity activity;
    private OnItemListener listener;

    public ListAdapter(OnItemListener listener, List<ItemEntity> itemList, Activity activity) {
        this.listener = listener;
        this.itemList = new ArrayList<>(itemList);
        this.itemListNotFiltered = new ArrayList<>(itemList);
        this.activity = activity;
    }

    /**
     * Nuovo costruttore, che prende in input solo listener e activity.
     * Per assegnare itemList viene usato il metodo pubblico setData()
     * @param listener listener
     * @param activity activity
     */
    public ListAdapter(OnItemListener listener, Activity activity){
        this.listener = listener;
        this.activity = activity;
    }


    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //todo questo layoutview prima aveva come layout "item_layout", ci ho messo item_in_list_card_layout
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ListViewHolder(layoutView,listener);
    }

    //TODO usare i dati del database
    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        ItemEntity currentCard = itemList.get(position);

        holder.itemNameTextView.setText(currentCard.getItemName());
        holder.itemPriceTextView.setText(currentCard.getItemPrice().toString());

        String image = currentCard.getImageResource();
        //al momento abbiamo solo drawable, in futuro ci saranno foto, questo if è per mettere i placeholder draawable
        if(image!=null){    //Ho fatto questa modifica perché se la stringa è null allora crasha chiamando .contains()
        //if(image.contains("ic_")){
            Drawable drawable = AppCompatResources.getDrawable(activity, activity.getResources()
                    .getIdentifier(image, "drawable",activity.getPackageName()));
            holder.itemImageView.setImageDrawable(drawable);
        }
    }

    @Override
    public int getItemCount() {
        Log.d(LOG_TAG, "getItemCount: itemList: " + itemList.toString());
        return itemList.size();
    }

    //Non so se questo metodo verrà usato ma penso di si
    public ItemEntity getItemSelected(int position){
        return itemList.get(position);
    }

    /**
     * Metodo per settare gli oggetti all'interno di una lista / tutti gli oggetti disponibili
     * @param items l'array di oggetti da mostrare all'utente
     */
    public void setData(List<ItemEntity> items){
        final ListItemDiffCallback diffCallback =
                new ListItemDiffCallback(this.itemList, items);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.itemList = new ArrayList<>(items);
        Log.d(LOG_TAG, this.itemList.toString());
        this.itemListNotFiltered = new ArrayList<>(items);

        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public Filter getFilter() {
        return listFilter;
    }

    private final Filter listFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<ItemEntity> filteredList = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0){
                filteredList.addAll(itemListNotFiltered);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for(ItemEntity item: itemListNotFiltered){
                    if(item.getItemName().toLowerCase().contains(filterPattern)){
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
