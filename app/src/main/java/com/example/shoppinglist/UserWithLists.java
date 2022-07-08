package com.example.shoppinglist;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserWithLists {
    @Embedded
    public UserEntity user;

    @Relation(
            parentColumn = "user_id",
            entityColumn = "user_creator_id"
    )

    private List<ListEntity> shoppingLists;

    public List<ListEntity> getShoppingLists() {
        return shoppingLists;
    }

    public void setShoppingLists(List<ListEntity> shoppingLists){
        this.shoppingLists = shoppingLists;
    }

    public void addShoppingLists(ListEntity listEntity){
        this.shoppingLists.add(listEntity);
    }
}
