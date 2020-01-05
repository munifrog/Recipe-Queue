package com.example.recipe_q.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.recipe_q.model.Recipe;

import java.util.List;

@Dao
public interface RecipeDataAccessObject {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long [] insertItems(List<Recipe> recipes);

    @Query("SELECT * FROM recipes ORDER BY timestamp DESC")
    LiveData<List<Recipe>> loadAllRecipesSignaled();
    @Query("SELECT * FROM recipes ORDER BY timestamp DESC")
    List<Recipe> loadAllRecipesImmediate();

    @Query("UPDATE recipes SET timestamp=:retrieval_time WHERE id_api IN (:id_array)")
    void updateTimestamp(long [] id_array, long retrieval_time);

    @Query("DELETE FROM recipes WHERE timestamp < :timestamp_cutoff")
    void removeExpired(long timestamp_cutoff);
}
