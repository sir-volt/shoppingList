package com.example.shoppinglist.RecyclerView;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import com.example.shoppinglist.Utilities;
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
    private int position;


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
        //Ho fatto questa modifica perché se la stringa è null allora crasha, quindi se è nulla ci metto il placeholder
        if(image==null){
            image = "ic_baseline_image_not_supported_24";
        }
        if(image.contains("ic_")) {
            //if(image.contains("ic_")){
            Drawable drawable = AppCompatResources.getDrawable(activity, activity.getResources()
                    .getIdentifier(image, "drawable", activity.getPackageName()));
            holder.itemImageView.setImageDrawable(drawable);
        } else{
                Bitmap bitmap = Utilities.getImageBitmap(activity, Uri.parse(image));
                if (bitmap != null){
                    holder.itemImageView.setImageBitmap(bitmap);
                }
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //setPosition(holder.getPosition());
                Log.d(LOG_TAG, "Long Click on " + holder.getAdapterPosition());
                setPosition(holder.getAdapterPosition());
                return false;
            }
        });

    }

    @Override
    public void onViewRecycled(@NonNull ListViewHolder holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
