package com.example.recipe_q.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.recipe_q.model.DirectionGroup;
import com.example.recipe_q.model.Ingredient;
import com.example.recipe_q.model.Recipe;

import java.util.List;

@Dao
public interface RecipeDataAccessObject {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long [] insertRecipes(List<Recipe> recipes);

    @Query("SELECT * FROM recipes ORDER BY timestamp DESC")
    LiveData<List<Recipe>> loadAllRecipesSignaled();

    @Query("SELECT * FROM recipes ORDER BY timestamp ASC")
    List<Recipe> loadRemainingRecipesImmediate();

    // https://stackoverflow.com/a/20310838
    @Query("UPDATE recipes SET ingredients=:ingredients WHERE id_api = :id")
    void updateIngredients(long id, List<Ingredient> ingredients);

    @Query("UPDATE recipes SET directions=:directions WHERE id_api = :id")
    void updateDirections(long id, List<DirectionGroup> directions);

    @Query("UPDATE recipes SET source_api=:source_api WHERE id_api = :id")
    void updateSourceUrlApi(long id, String source_api);

    @Query("UPDATE recipes SET source_orig=:source_orig WHERE id_api = :id")
    void updateSourceUrlOrig(long id, String source_orig);

    @Query("UPDATE recipes SET timestamp=:retrieval_time WHERE id_api IN (:id_array)")
    void updateTimestamp(long [] id_array, long retrieval_time);

    @Query("DELETE FROM recipes WHERE timestamp < :timestamp_cutoff")
    void removeExpired(long timestamp_cutoff);
}
