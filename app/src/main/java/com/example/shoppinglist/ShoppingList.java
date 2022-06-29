package com.example.shoppinglist;

import java.util.List;

//TODO versione temporanea; totalitems va calcolato dinamicamente e itemlist deve poter essere aggiornato
public class ShoppingList {

    private final String listName;
    private int totalItems;
    private int remainingItems;
    private List<ListItem> itemList;

    public ShoppingList(String listName, int totalItems, int remainingItems, List<ListItem> itemList){
        this.listName = listName;
        this.itemList = itemList;
        this.totalItems = totalItems;
        this.remainingItems = remainingItems;
    }

    public String getListName() {
        return listName;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public int getRemainingItems() {
        return remainingItems;
    }

    public List<ListItem> getItemList() {
        return itemList;
    }

}
