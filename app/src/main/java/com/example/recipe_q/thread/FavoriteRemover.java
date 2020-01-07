package com.example.recipe_q.thread;

import android.os.AsyncTask;

import com.example.recipe_q.db.FavoritesDatabase;

public class FavoriteRemover extends AsyncTask<long [], Void, Void> {
    private FavoritesDatabase mDatabase;
    private Listener mListener;

    public FavoriteRemover(FavoritesDatabase database, Listener listener) {
        mDatabase = database;
        mListener = listener;
    }

    public interface Listener {
        void onFavoritesRemoved();
    }

    @Override
    protected Void doInBackground(long []... recipes) {
        for (long [] ids : recipes) {
            mDatabase.dao().removeFavorites(ids);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void ignored) {
        super.onPostExecute(ignored);
        mListener.onFavoritesRemoved();
    }
}
