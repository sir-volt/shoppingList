package com.example.shoppinglist;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "items")
public class ListItem {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "item_name")
    private final String itemName;

    @ColumnInfo(name = "price")
    private final String itemPrice;

    @ColumnInfo(name = "description")
    private final String itemDescription;

    @ColumnInfo(name = "image")
    private final String imageResource;


    public ListItem(String image, String name, String price, String description){
        this.imageResource = image;
        this.itemName = name;
        this.itemPrice = price;
        this.itemDescription = description;
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
