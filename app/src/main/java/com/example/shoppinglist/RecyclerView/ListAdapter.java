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
import com.example.shoppinglist.ViewModel.AddToListViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListAdapter extends RecyclerView.Adapter<ListViewHolder> implements Filterable {

    private final static String LOG_TAG = "ListAdapter";
    private List<ItemEntity> itemList = new ArrayList<>();
    private List<ItemEntity> itemListNotFiltered = new ArrayList<>();
    private Activity activity;
    private OnItemListener listener;
    private AddToListViewModel viewModel;


    /**
     * Nuovo costruttore, che prende in input solo listener, activity e viewModel.
     * Per assegnare itemList viene usato il metodo pubblico setData()
     * @param listener listener
     * @param activity activity
     * @param viewModel viewModel per chiamare metodi dalla repository
     */
    public ListAdapter(OnItemListener listener, Activity activity, AddToListViewModel viewModel){
        this.listener = listener;
        this.activity = activity;
        this.viewModel = viewModel;
        //tanto viewmodel torna null quindi niente
    }


    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //todo questo layoutview prima aveva come layout "item_layout", ci ho messo item_in_list_card_layout
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ListViewHolder(layoutView,listener,this);
    }

    //TODO usare i dati del database
    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        ItemEntity currentCard = itemList.get(position);

        holder.itemNameTextView.setText(currentCard.getItemName());
        String price = activity.getApplicationContext().getString(R.string.item_price_2, currentCard.getItemPrice());
        holder.itemPriceTextView.setText(price);

        String image = currentCard.getImageResource();
        //todo vedo sempre le immagini, copiare dal listcontentadapter
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

    public void insertItemInList(ItemEntity itemEntity){
        if (viewModel == null){
            Log.e(LOG_TAG, "viewmodel null");
        }
        viewModel.insertItemInList(itemEntity);
    }
}
