package com.example.shoppinglist;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "items")
public class ListItem {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "item_id")
    private int id;

    @ColumnInfo(name = "item_name")
    private String itemName;

    @ColumnInfo(name = "price")
    private String itemPrice;

    @ColumnInfo(name = "description")
    private String itemDescription;

    @ColumnInfo(name = "image")
    private String imageResource;


    public ListItem(String imageResource, String itemName, String itemPrice, String itemDescription){
        this.imageResource = imageResource;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemDescription = itemDescription;
    }

    public String getImageResource() {
        return imageResource;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public Integer getId(){
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }

    @NonNull
    @Override
    public String toString() {
        return "ListItem{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", itemPrice='" + itemPrice + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", imageResource='" + imageResource + '\'' +
                '}';
    }
}
