package com.example.shoppinglist.RecyclerView;

public interface OnItemListener {
    void onItemClick(int position);

    boolean onItemLongClick(int position);
}
