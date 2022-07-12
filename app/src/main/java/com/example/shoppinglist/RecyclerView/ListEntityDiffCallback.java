package com.example.shoppinglist.RecyclerView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.shoppinglist.ListEntity;

import java.util.List;

public class ListEntityDiffCallback extends DiffUtil.Callback {

    private final List<ListEntity> oldShoppingLists;
    private final List<ListEntity> newShoppingLists;


    public ListEntityDiffCallback(List<ListEntity> oldShoppingLists, List<ListEntity> newShoppingLists) {
        this.oldShoppingLists = oldShoppingLists;
        this.newShoppingLists = newShoppingLists;
    }

    @Override
    public int getOldListSize() {
        return oldShoppingLists.size();
    }

    @Override
    public int getNewListSize() {
        return newShoppingLists.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldShoppingLists.get(oldItemPosition) == newShoppingLists.get(newItemPosition);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final ListEntity oldList = oldShoppingLists.get(oldItemPosition);
        final ListEntity newList = newShoppingLists.get(newItemPosition);

        return oldList.getListName().equals(newList.getListName()) && oldList.getListId() == newList.getListId()
                && oldList.getUserCreatorId() == newList.getUserCreatorId();
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition){
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
