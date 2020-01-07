package com.example.recipe_q.thread;

import android.os.AsyncTask;

import com.example.recipe_q.db.FavoritesDatabase;
import com.example.recipe_q.model.FavoriteRecipe;

public class FavoriteInserter extends AsyncTask<FavoriteRecipe, Void, Void> {
    private FavoritesDatabase mDatabase;
    private Listener mListener;

    public FavoriteInserter(FavoritesDatabase database, Listener listener) {
        mDatabase = database;
        mListener = listener;
    }

    public interface Listener {
        void onFavoriteInserted();
    }

    @Override
    protected Void doInBackground(FavoriteRecipe... recipes) {
        for (FavoriteRecipe recipe : recipes) {
            mDatabase.dao().insertFavorite(recipe);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void ignored) {
        super.onPostExecute(ignored);
        mListener.onFavoriteInserted();
    }
}
