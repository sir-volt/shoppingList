package com.example.shoppinglist.RecyclerView;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.ListEntity;
import com.example.shoppinglist.R;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListViewHolder> implements Filterable {
    private final static String LOG_TAG = "ShoppingListAdapter";
    private List<ListEntity> shoppingLists = new ArrayList<>();
    private List<ListEntity> shoppingListsNotFiltered = new ArrayList<>();
    private Activity activity;
    private OnItemListener listener;

    public ShoppingListAdapter(List<ListEntity> shoppingLists, Activity activity) {
        this.shoppingLists = shoppingLists;
        this.activity = activity;
    }

    public ShoppingListAdapter(OnItemListener listener, Activity activity){
        Log.d(LOG_TAG, "CONSTRUCTOR CALL");
        this.listener = listener;
        this.activity = activity;

    }

    @NonNull
    @Override
    public ShoppingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_card_layout, parent, false);
        return new ShoppingListViewHolder(layoutView, listener);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * This method should update the contents of the RecyclerView.ViewHolder.itemView to reflect
     * the item at the given position.
     *
     * @param holder ViewHolder which should be updated to represent the contents of the item at
     *               the given position in the data set.
     * @param position position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ShoppingListViewHolder holder, int position) {
        ListEntity currentShoppingList = shoppingLists.get(position);
        holder.listNameTextview.setText(currentShoppingList.getListName());
        //holder.remainingTextview.setText(currentShoppingList.getRemainingItems() + "/" + currentShoppingList.getTotalItems());
        //holder.remainingTextview.setText(0 + "/" + 0);

        //activity.getApplicationContext().getString(R.string.remaining,currentShoppingList.getRemainingItems(),currentShoppingList.getTotalItems());

    }

    @Override
    public int getItemCount() {
        //Log.d(LOG_TAG, "getItemCount: count: " + shoppingLists.size() + " shoppingLists: " + shoppingLists.toString());
        //String className = new Exception().getStackTrace()[1].getClassName();
        //Log.d(LOG_TAG, "Who called me? " + className);
        return shoppingLists.size();
    }

    public ListEntity getListSelected(int position){
        return shoppingLists.get(position);
    }


    /**
     * Method that set the list in the Home
     * @param list the list to display in the home
     */
    public void setData(List<ListEntity> list){
        Log.d(LOG_TAG, "IN SETDATA");
        final ListEntityDiffCallback diffCallback =
                new ListEntityDiffCallback(this.shoppingLists, list);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.shoppingLists = new ArrayList<>(list);
        Log.d(LOG_TAG, this.shoppingLists.toString());
        this.shoppingListsNotFiltered = new ArrayList<>(list);
        Log.d(LOG_TAG,"setData: " + this.shoppingLists);
        Log.d(LOG_TAG,"setData notFiltered: " + this.shoppingListsNotFiltered);

        diffResult.dispatchUpdatesTo(this);
    }

    public void updateShoppingLists(List<ListEntity> filteredList) {

        Log.d(LOG_TAG, "UPDATE LIST ITEMS");

        final ListEntityDiffCallback diffCallback =
                new ListEntityDiffCallback(this.shoppingLists, filteredList);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.shoppingLists = new ArrayList<>(filteredList);
        diffResult.dispatchUpdatesTo(this);
    }

    /**
     *
     * @return il filtro da usare per la ricerca
     */
    @Override
    public Filter getFilter() {
        return listCardFilter;
    }

    private final Filter listCardFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            Log.d(LOG_TAG, "FILTER->PERFORM FILTERING");
            //charSequence = "Inserisci Qualcosa";
            Log.d(LOG_TAG, "CharSequence: " + charSequence.toString());
            List<ListEntity> filteredList = new ArrayList<>();

            //if you have no constraint --> return the full list
            if (charSequence == null || charSequence.length() == 0) {
                Log.d(LOG_TAG, "CharSequence is null");
                filteredList.addAll(shoppingListsNotFiltered);
            } else {
                //else apply the filter and return a filtered list
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (ListEntity listEntity : shoppingListsNotFiltered) {
                    if (listEntity.getListName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(listEntity);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            Log.d(LOG_TAG, "FILTER->PUBLISH RESULT");
            //charSequence = "Inserisci Qualcosa";
            Log.d(LOG_TAG, "CharSequence: " + charSequence.toString() + "; filterResults: " + filterResults);
            List<ListEntity> filteredList = new ArrayList<>();
            List<?> result = (List<?>) filterResults.values;
            for (Object object : result) {
                if (object instanceof ListEntity) {
                    filteredList.add((ListEntity) object);
                }
            }

            //warn the adapter that the data are changed after the filtering
            updateShoppingLists(filteredList);
        }
    };

}
