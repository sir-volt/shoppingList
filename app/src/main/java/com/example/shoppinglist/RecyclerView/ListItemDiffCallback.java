package com.example.shoppinglist.RecyclerView;


import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.shoppinglist.ListItem;

import java.util.List;

public class ListItemDiffCallback extends DiffUtil.Callback {
    private final List<ListItem> oldListItem;
    private final List<ListItem> newListItem;

    public ListItemDiffCallback(List<ListItem> oldListItem, List<ListItem> newListItem){
        this.oldListItem = oldListItem;
        this.newListItem = newListItem;
    }

    @Override
    public int getOldListSize() {
        return this.oldListItem.size();
    }

    @Override
    public int getNewListSize() {
        return this.newListItem.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return this.oldListItem.get(oldItemPosition) == this.newListItem.get(newItemPosition);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final ListItem oldItem = oldListItem.get(oldItemPosition);
        final ListItem newItem = newListItem.get(newItemPosition);

        return oldItem.getItemName().equals(newItem.getItemName()) &&
                oldItem.getItemPrice().equals(newItem.getItemPrice());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition){
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
