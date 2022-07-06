package com.example.shoppinglist;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "items")
public class ItemEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "item_id")
    private int id;

    @ColumnInfo(name = "item_name")
    private String itemName;

    @ColumnInfo(name = "price")
    private Double itemPrice;

    @ColumnInfo(name = "image")
    private String imageResource;


    public ItemEntity(String imageResource, String itemName, Double itemPrice){
        this.imageResource = imageResource;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }

    public String getImageResource() {
        return imageResource;
    }

    public String getItemName() {
        return itemName;
    }

    public Double getItemPrice() {
        return itemPrice;
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

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }

    @NonNull
    @Override
    public String toString() {
        return "ItemEntity{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", itemPrice='" + itemPrice + '\'' +
                ", imageResource='" + imageResource + '\'' +
                '}';
    }
}
