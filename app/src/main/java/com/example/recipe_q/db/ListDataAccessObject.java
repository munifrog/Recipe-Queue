package com.example.recipe_q.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.recipe_q.model.ListItem;

import java.util.List;

@Dao
public interface ListDataAccessObject {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long [] insertItems(List<ListItem> items);

    // https://stackoverflow.com/a/55483157
    @Query("DELETE FROM items WHERE item_id IN (:id_array)")
    void deleteItems(long [] id_array);

    @Query("SELECT * FROM items ORDER BY item_name ASC")
    LiveData<List<ListItem>> signaledLoadAllItems();
    @Query("SELECT * FROM items ORDER BY item_name ASC")
    List<ListItem> immediateLoadAllItems();

    @Query("SELECT * FROM items WHERE item_swiped == 0 ORDER BY item_name ASC, item_unit ASC")
    LiveData<List<ListItem>> signaledLoadSoughtItems();
    @Query("SELECT * FROM items WHERE item_swiped == 0 ORDER BY item_name ASC, item_unit ASC")
    List<ListItem> immediateLoadSoughtItems();

    // It is unlikely that timestamp will be same for two different names, but sort just in case ...
    @Query("SELECT * FROM items WHERE item_swiped > 0 ORDER BY item_swiped DESC, item_name ASC, item_unit ASC")
    LiveData<List<ListItem>> signaledLoadFoundItems();
    @Query("SELECT * FROM items WHERE item_swiped > 0 ORDER BY item_swiped DESC, item_name ASC, item_unit ASC")
    List<ListItem> immediateLoadFoundItems();

    // https://sqlite.org/lang_update.html
    @Query("UPDATE items SET item_swiped=:swipe_time WHERE item_id == :id")
    void updateTimestamp(long id, long swipe_time);
}