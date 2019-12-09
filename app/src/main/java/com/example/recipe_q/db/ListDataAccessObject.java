package com.example.recipe_q.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.recipe_q.model.ListItem;

import java.util.List;

@Dao
public interface ListDataAccessObject {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long [] insertItems(List<ListItem> items);

    @Delete
    void deleteItems(List<ListItem> items);

    @Query("SELECT * FROM items ORDER BY item_name ASC")
    LiveData<List<ListItem>> signaledLoadItems();
    @Query("SELECT * FROM items ORDER BY item_name ASC")
    List<ListItem> immediateLoadItems();

    @Query("SELECT * FROM items WHERE item_swiped > 0 ORDER BY item_swiped DESC")
    LiveData<List<ListItem>> signaledLoadSwipedItems();
    @Query("SELECT * FROM items WHERE item_swiped > 0 ORDER BY item_swiped DESC")
    List<ListItem> immediateLoadSwipedItems();
}