package com.example.shoppinglist;

import androidx.core.util.Pair;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO versione temporanea; totalitems va calcolato dinamicamente e itemlist deve poter essere aggiornato

//@Entity(tableName = "lists")
public class ShoppingList {

    //@PrimaryKey(autoGenerate = true)
    private Integer id;

    //@ColumnInfo(name = "list_name")
    private final String listName;


    private int totalItems;
    private int remainingItems;
    private final Map<ListItem, Integer> itemMap;

    public ShoppingList(String listName){
        this.listName = listName;
        this.itemMap = new HashMap<>();
        this.totalItems = 0;
        this.remainingItems = 0;
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

    public Map<ListItem, Integer> getItemMap(){
        return itemMap;
    }


    //Forse sti metodi vanno messi nella Rapository
    public void addItemToList(ListItem listItem){
        if(itemMap.containsKey(listItem)){
            /*If listItem is already in itemMap, increase its item count by 1*/
            Integer itemCount = itemMap.get(listItem);
            itemMap.put(listItem, itemCount + 1);
        } else {
            /*Otherwise, simply insert listItem and put its item count at base value (1)*/
            itemMap.put(listItem, 1);
            totalItems = itemMap.size();
            remainingItems++;
        }
    }

    public void removeItemFromList(ListItem listItem){
        if (itemMap.containsKey(listItem)){
            Integer itemCount = itemMap.get(listItem);
            itemCount = itemCount == 0 ? itemCount : itemCount - 1;
            if (itemCount>0){
                itemCount--;
                itemMap.put(listItem, itemCount);
                if(itemCount==0){
                    remainingItems--;
                }
            }

        }
    }

}
