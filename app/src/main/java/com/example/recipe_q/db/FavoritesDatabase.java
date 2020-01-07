package com.example.recipe_q.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.recipe_q.model.FavoriteRecipe;

@Database(entities = {FavoriteRecipe.class}, version = 1, exportSchema = false)
public abstract class FavoritesDatabase extends RoomDatabase {
    private static final Object LOCK = new Object();
    private static final String DB_NAME = "remembered_recipes";
    private static FavoritesDatabase sInstance;

    public static FavoritesDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(
                        context,
                        FavoritesDatabase.class,
                        DB_NAME
                )
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return sInstance;
    }

    public abstract FavoritesDataAccessObject dao();
}
