package com.example.shoppinglist;

//TODO finire questo + relative classi e mergiare (richi suggerisce elementiLista)

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

//@Entity(tableName = "items_in_list", foreignKeys = {@ForeignKey(entity = Item)})
public class ListWithItems {

    @Embedded
    public ListEntity list;

    @Relation(
            parentColumn = "list_id",
            entityColumn = "item_id",
            associateBy = @Junction(ListAndItemCrossRef.class)
    )
    //TODO potrei potenziare questa classe e introdurre la mappa con oggetti e quantità
    public List<ListItem> itemList;

    /*private int itemId;

    private int listId;

    private Integer quantity;*/

}
