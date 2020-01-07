package com.example.recipe_q.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.recipe_q.model.FavoriteRecipe;

import java.util.List;

@Dao
public interface FavoritesDataAccessObject {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertFavorite(FavoriteRecipe recipe);

    @Query("SELECT * FROM favorites ORDER BY save_time DESC")
    LiveData<List<FavoriteRecipe>> loadAllFavoritesSignaled();

    @Query("DELETE FROM favorites WHERE id_api IN (:id_array)")
    void removeFavorites(long [] id_array);
}