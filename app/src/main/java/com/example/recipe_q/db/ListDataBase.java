package com.example.recipe_q.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.recipe_q.model.ListItem;

@Database(entities = {ListItem.class}, version = 1, exportSchema = false)
public abstract class ListDataBase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static final String DB_NAME = "list_items";
    private static ListDataBase sInstance;

    public static ListDataBase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(
                        context,
                        ListDataBase.class,
                        DB_NAME
                )
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return sInstance;
    }

    public abstract ListDataAccessObject dao();
}
