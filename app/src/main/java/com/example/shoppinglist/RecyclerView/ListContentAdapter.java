package com.example.shoppinglist.RecyclerView;

import android.app.Activity;
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
    Activity activity;
    private OnItemListener listener;

    /**
     * Nuovo costruttore, che prende in input solo listener e activity.
     * Per assegnare itemList viene usato il metodo pubblico setData()
     * @param listener listener
     * @param activity activity
     */
    public ListContentAdapter(OnItemListener listener, Activity activity){
        this.listener = listener;
        this.activity = activity;
    }


    @NonNull
    @Override
    public ListContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //todo questo layoutview prima aveva come layout "item_layout", ci ho messo item_in_list_card_layout
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_in_list_card_layout, parent, false);
        //return new ListViewHolder(layoutView, listener);
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
                Log.d(LOG_TAG, "charSequence in listFilter is null");
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                Log.d(LOG_TAG, "charSequence in listFilter is not null: " + filterPattern);
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
        /*final ListItemDiffCallback diffCallback =
                new ListItemDiffCallback(this.itemList, filteredResults);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.itemList.clear();
        this.itemList.addAll(filteredResults);
        diffResult.dispatchUpdatesTo(this);*/

        final ListItemDiffCallback diffCallback =
                new ListItemDiffCallback(this.itemList, filteredResults);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.itemList = new ArrayList<>(filteredResults);
        diffResult.dispatchUpdatesTo(this);
    }
}

