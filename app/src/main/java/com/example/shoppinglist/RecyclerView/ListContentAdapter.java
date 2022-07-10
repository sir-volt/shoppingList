package com.example.shoppinglist.RecyclerView;

import android.app.Activity;
import android.content.ClipData;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.ItemEntity;
import com.example.shoppinglist.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter utilizzato per il contenuto di una lista della spesa (NON PER VISUALIZZARE TUTTI GLI ITEMS NEL DB)
 */
public class ListContentAdapter extends RecyclerView.Adapter<ListContentViewHolder> implements Filterable {

    private final static String LOG_TAG = "ListContentAdapter";
    private List<ItemEntity> itemList = new ArrayList<>();
    private List<ItemEntity> itemListNotFiltered = new ArrayList<>();
    private final Activity activity;
    private final OnItemListener listener;

    /**
     * Nuovo costruttore, che prende in input solo listener e activity.
     * Per assegnare itemList viene usato il metodo pubblico setData()
     * @param listener listener
     * @param activity activity
     */
    public ListContentAdapter(OnItemListener listener, Activity activity){
        Log.d(LOG_TAG, "CONSTRUCTOR CALL");
        this.listener = listener;
        this.activity = activity;
    }


    @NonNull
    @Override
    public ListContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_in_list_card_layout, parent, false);
        return new ListContentViewHolder(layoutView,listener);
    }

    //TODO usare i dati del database
    @Override
    public void onBindViewHolder(@NonNull ListContentViewHolder holder, int position) {
        ItemEntity currentCard = itemList.get(position);
        String price = activity.getApplicationContext().getString(R.string.item_price_2, currentCard.getItemPrice());
        holder.itemNameTextView.setText(currentCard.getItemName());
        holder.itemPriceTextView.setText(price);

        String image = currentCard.getImageResource();
        //al momento abbiamo solo drawable, in futuro ci saranno foto, questo if è per mettere i placeholder draawable
        if(image==null){
            //if(image.contains("ic_")){
            int placeholderImageId  = R.drawable.ic_baseline_image_not_supported_24;
            holder.itemImageView.setImageResource(placeholderImageId);

        }
    }

    @Override
    public int getItemCount() {
        Log.d(LOG_TAG, "getItemCount: count: " + itemList.size() +  " itemList: " + itemList);
        //String className = new Exception().getStackTrace()[1].getClassName();
        //Log.d(LOG_TAG, "Who called me? " + className);
        return itemList.size();
    }

    //Non so se questo metodo verrà usato ma penso di si
    public ItemEntity getItemSelected(int position){
        return itemList.get(position);
    }

    /**
     * Metodo per settare gli oggetti all'interno di una lista
     * @param items l'array di oggetti da mostrare all'utente
     */
    public void setData(List<ItemEntity> items){
        Log.d(LOG_TAG, "IN SETDATA");
        final ListItemDiffCallback diffCallback =
                new ListItemDiffCallback(this.itemList, items);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.itemList = new ArrayList<>(items);
        this.itemListNotFiltered = new ArrayList<>(items);
        Log.d(LOG_TAG,"setData: " + this.itemList);
        Log.d(LOG_TAG,"setData notFiltered: " + this.itemListNotFiltered);
        diffResult.dispatchUpdatesTo(this);
    }

    private void updateListItems(List<ItemEntity> filteredResults) {

        Log.d(LOG_TAG, "UPDATE LIST ITEMS");

        final ListItemDiffCallback diffCallback =
                new ListItemDiffCallback(this.itemList, filteredResults);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.itemList = new ArrayList<>(filteredResults);
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public Filter getFilter() {
        return listFilter;
    }

    private final Filter listFilter = new Filter() {
        /**
         * Called to filter the data according to the constraint
         * @param constraint constraint used to filtered the data
         * @return the result of the filtering
         */
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            Log.d(LOG_TAG, "FILTER->PERFORM FILTERING");
            //constraint = "Inserisci qualcosa";
            Log.d(LOG_TAG, "CharSequence: " + constraint.toString());

            List<ItemEntity> filteredList = new ArrayList<>();

            //if you have no constraint --> return the full list
            if (constraint == null || constraint.length() == 0) {
                Log.d(LOG_TAG, "CharSequence is null");
                filteredList.addAll(itemListNotFiltered);
            } else {
                //else apply the filter and return a filtered list
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ItemEntity item : itemListNotFiltered) {
                    if (item.getItemName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        /**
         * Called to publish the filtering results in the user interface
         * @param constraint constraint used to filter the data
         * @param results the result of the filtering
         */
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            Log.d(LOG_TAG, "FILTER->PUBLISH RESULT");
            //constraint = "Inserisci qualcosa";
            Log.d(LOG_TAG, "CharSequence: " + constraint.toString() + "; filterResults: " + results.toString());
            if (constraint == null || constraint.length() == 0) {
                Log.d(LOG_TAG, "CharSequence is null");
            }
            List<ItemEntity> filteredList = new ArrayList<>();
            List<?> result = (List<?>) results.values;
            for (Object object : result) {
                if (object instanceof ItemEntity) {
                    filteredList.add((ItemEntity) object);
                }
            }

            //warn the adapter that the data are changed after the filtering
            updateListItems(filteredList);
        }
    };

}

