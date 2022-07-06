package com.example.shoppinglist.RecyclerView;


import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.shoppinglist.ItemEntity;

import java.util.List;

public class ListItemDiffCallback extends DiffUtil.Callback {
    private final List<ItemEntity> oldItemEntity;
    private final List<ItemEntity> newItemEntity;

    public ListItemDiffCallback(List<ItemEntity> oldItemEntity, List<ItemEntity> newItemEntity){
        this.oldItemEntity = oldItemEntity;
        this.newItemEntity = newItemEntity;
    }

    @Override
    public int getOldListSize() {
        return this.oldItemEntity.size();
    }

    @Override
    public int getNewListSize() {
        return this.newItemEntity.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return this.oldItemEntity.get(oldItemPosition) == this.newItemEntity.get(newItemPosition);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final ItemEntity oldItem = oldItemEntity.get(oldItemPosition);
        final ItemEntity newItem = newItemEntity.get(newItemPosition);

        return oldItem.getItemName().equals(newItem.getItemName()) &&
                oldItem.getItemPrice().equals(newItem.getItemPrice());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition){
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
