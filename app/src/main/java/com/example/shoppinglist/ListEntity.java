package com.example.shoppinglist;

import androidx.core.util.Pair;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Entity(tableName = "lists")
public class ListEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "list_id")
    private int listId;

    @ColumnInfo(name = "user_creator_id")
    private int userCreatorId;

    @ColumnInfo(name = "list_name")
    private final String listName;


    //TODO rimuovere questi campi i guess per usare il modello di riccardo (spostarli in repository?)
    /*private int totalItems;
    private int remainingItems;
    private final Map<ItemEntity, Integer> itemMap;*/ //Ho dovuto commentarli perché Room non li vuole

    public ListEntity(String listName){
        this.listName = listName;
        //this.itemMap = new HashMap<>();
        //this.totalItems = 0;
        //this.remainingItems = 0;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int id) {
        this.listId = id;
    }

    public int getUserCreatorId() {
        return userCreatorId;
    }

    public void setUserCreatorId(int userId) {
        this.userCreatorId = userId;
    }

    public String getListName() {
        return listName;
    }

    /*public int getTotalItems() {
        return totalItems;
    }

    public int getRemainingItems() {
        return remainingItems;
    }

    public Map<ItemEntity, Integer> getItemMap(){
        return itemMap;
    }*/


    //Forse sti metodi vanno messi nella Rapository
    /*public void addItemToList(ItemEntity listItem){
        if(itemMap.containsKey(listItem)){
            //If listItem is already in itemMap, increase its item count by 1
            Integer itemCount = itemMap.get(listItem);
            itemMap.put(listItem, itemCount + 1);
        } else {
            //Otherwise, simply insert listItem and put its item count at base value (1)
            itemMap.put(listItem, 1);
            totalItems = itemMap.size();
            remainingItems++;
        }
    }

    public void removeItemFromList(ItemEntity listItem){
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
    }*/

}
