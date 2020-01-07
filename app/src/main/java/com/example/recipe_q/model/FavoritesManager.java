package com.example.recipe_q.model;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.recipe_q.db.FavoritesDatabase;
import com.example.recipe_q.thread.FavoriteInserter;
import com.example.recipe_q.thread.FavoriteRemover;

import java.util.List;

public class FavoritesManager implements FavoriteInserter.Listener, FavoriteRemover.Listener {
    private FavoritesDatabase mDatabase;
    private Listener mListener;
    private Observer mObserver;

    private LiveData<List<FavoriteRecipe>> mFavoritesLive;
    private List<FavoriteRecipe> mFavorites;

    public interface Listener {
        void onFavoritesUpdated();
    }

    FavoritesManager(Application application, Listener listener) {
        mDatabase = FavoritesDatabase.getInstance(application);
        mListener = listener;
        mObserver = new Observer<List<FavoriteRecipe>>() {
            @Override
            public void onChanged(List<FavoriteRecipe> recipes) {
                mFavorites = recipes;
                mListener.onFavoritesUpdated();
            }
        };
        loadFavorites();
    }

    private void stopObserving() {
        if (mFavoritesLive != null) {
            // noinspection unchecked
            mFavoritesLive.removeObserver(mObserver);
        }
    }

    private void loadFavorites() {
        stopObserving();
        mFavoritesLive = mDatabase.dao().loadAllFavoritesSignaled();
        // noinspection unchecked
        mFavoritesLive.observeForever(mObserver);
    }

    FavoriteRecipe getFavoriteMatch(long id) {
        for (FavoriteRecipe recipe : mFavorites) {
            if (recipe.getIdSpoonacular() == id) {
                return recipe;
            }
        }
        return null;
    }

    void addFavorite(FavoriteRecipe favorite) {
        new FavoriteInserter(mDatabase, this).execute(favorite);
    }

    void removeFavorites(long [] ids) { new FavoriteRemover(mDatabase, this).execute(ids); }

    @Override
    public void onFavoriteInserted() { mListener.onFavoritesUpdated(); }

    @Override
    public void onFavoritesRemoved() { mListener.onFavoritesUpdated(); }

    List<FavoriteRecipe> getFavorites() { return mFavorites; }
}
