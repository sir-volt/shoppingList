package com.example.shoppinglist;

public class CardItem {
    private String imageResource;
    private String itemName;
    private String itemPrice;
    private String itemDescription;

    public CardItem(String image, String name, String price, String description){
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
}
