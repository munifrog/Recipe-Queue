package com.example.recipe_q.model;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.recipe_q.db.RecipeDatabase;

import java.util.List;

public class RecipeManager {
    private RecipeDatabase mDatabase;
    private Listener mListener;
    private Observer mObserver;

    private LiveData<List<Recipe>> mRecipesLive;
    private List<Recipe> mRecipes;

    public interface Listener {
        void onRecipeDatabaseUpdated();
    }

    RecipeManager(Context context, Listener listener) {
        mDatabase = RecipeDatabase.getInstance(context);
        mListener = listener;
        mObserver = new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                mRecipes = recipes;
                mListener.onRecipeDatabaseUpdated();
            }
        };
        loadRecipes();
    }

    private void stopObserving() {
        if (mRecipesLive != null) {
            // noinspection unchecked
            mRecipesLive.removeObserver(mObserver);
        }
    }

    void loadRecipes() {
        stopObserving();
        mRecipesLive = mDatabase.dao().loadAllRecipesSignaled();
        // noinspection unchecked
        mRecipesLive.observeForever(mObserver);
    }

    void storeRecipes(final List<Recipe> recipes) {
    }

    List<Recipe> getRecipes() { return mRecipes; }
    LiveData<List<Recipe>> getLiveRecipes() { return mRecipesLive; }
}
