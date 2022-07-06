package com.example.shoppinglist.RecyclerView;


import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.shoppinglist.ItemEntity;
import com.example.shoppinglist.ListEntity;

import java.util.List;

public class ListItemDiffCallback extends DiffUtil.Callback {
    private final List<ItemEntity> oldItemList;
    private final List<ItemEntity> newItemList;

    public ListItemDiffCallback(List<ItemEntity> oldItemEntity, List<ItemEntity> newItemEntity){
        this.oldItemList = oldItemEntity;
        this.newItemList = newItemEntity;
    }

    @Override
    public int getOldListSize() {
        return this.oldItemList.size();
    }

    @Override
    public int getNewListSize() {
        return this.newItemList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return this.oldItemList.get(oldItemPosition) == this.newItemList.get(newItemPosition);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final ItemEntity oldItem = oldItemList.get(oldItemPosition);
        final ItemEntity newItem = newItemList.get(newItemPosition);

        return oldItem.getItemName().equals(newItem.getItemName()) &&
                oldItem.getItemPrice().equals(newItem.getItemPrice());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition){
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
