package com.example.shoppinglist;

public class ItemEntity {
    private String imageResource;
    private String itemName;
    private String itemPrice;

    public ItemEntity(String image, String name, String price){
        this.imageResource = image;
        this.itemName = name;
        this.itemPrice = price;
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
}
