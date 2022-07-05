package com.example.shoppinglist;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
/*
* Questa classe serve per rappresentare l'entità associativa fra una lista della spesa e gli oggetti
* che può contenere (quindi ListEntity e ListItem), per modellare l'associazione many-to-many.
* È anche detta tabella dei riferimenti incrociati.
* Ogni riga di questa tabella corrisponde a un'assiociazione di un'istanza della Lista e un istanza
* dell'Oggetto.
* */
@Entity(tableName = "list_item_cross_ref", primaryKeys = {"list_id", "item_id"})
public class ListAndItemCrossRef {

    @ColumnInfo(name = "list_id")
    public int listId;

    @ColumnInfo(name = "item_id")
    public int itemId;


    //TODO si potrebbe pure aggiungere la quantià di un oggetto :D
    
}
