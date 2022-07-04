package com.example.shoppinglist;

public class ListItem {
    private String imageResource;
    private String itemName;
    private String itemPrice;

    public ListItem(String image, String name, String price, String description){
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
