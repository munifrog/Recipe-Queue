package com.example.recipe_q.model;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.recipe_q.db.RecipeDatabase;
import com.example.recipe_q.service.CleanupService;
import com.example.recipe_q.thread.RecipeInserter;
import com.example.recipe_q.thread.RecipeUpdater;

import java.util.List;

import static com.example.recipe_q.service.CleanupService.ACTION_REMOVE_EXPIRED_IMMEDIATELY;
import static com.example.recipe_q.service.CleanupService.ACTION_REMOVE_EXPIRED_SCHEDULED;
import static com.example.recipe_q.service.CleanupService.EXTRA_TIMESTAMP_CUTOFF;

public class RecipeManager implements RecipeInserter.Listener, RecipeUpdater.Listener {
    // See https://spoonacular.com/food-api/terms
    public static long RECIPE_CACHE_TIME_LIMIT_MILLIS = 3600000; // 1000 ms/sec * 60 sec/min * 60 min/hr * 1 hr

    private Application mApplication;
    private RecipeDatabase mDatabase;
    private Listener mListener;
    private Observer mObserver;

    private LiveData<List<Recipe>> mRecipesLive;
    private List<Recipe> mRecipes;

    public interface Listener {
        void onRecipeDatabaseUpdated();
    }

    RecipeManager(Application application, Listener listener) {
        mApplication = application;
        mDatabase = RecipeDatabase.getInstance(mApplication);
        mListener = listener;
        mObserver = new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                mRecipes = recipes;
                mListener.onRecipeDatabaseUpdated();
            }
        };
        cleanupRecipesNow();
        loadRecipes();
    }

    private void stopObserving() {
        if (mRecipesLive != null) {
            // noinspection unchecked
            mRecipesLive.removeObserver(mObserver);
        }
    }

    private void loadRecipes() {
        stopObserving();
        mRecipesLive = mDatabase.dao().loadAllRecipesSignaled();
        // noinspection unchecked
        mRecipesLive.observeForever(mObserver);
    }

    void storeRecipes(final List<Recipe> recipes) {
        if (recipes.size() > 0) {
            scheduleRecipeCleanup(recipes.get(0).getRetrievalTime() + RECIPE_CACHE_TIME_LIMIT_MILLIS);
            // noinspection unchecked
            new RecipeInserter(mDatabase, this).execute(recipes);
        }
    }

    void updateRecipe(final Recipe recipe) {
        scheduleRecipeCleanup(recipe.getRetrievalTime() + RECIPE_CACHE_TIME_LIMIT_MILLIS);
        new RecipeUpdater(mDatabase, this).execute(recipe);
    }

    Recipe getRecipeMatch(long id) {
        // The most recently updated recipes should appear early in the list
        for (Recipe recipe : mRecipes) {
            if (recipe.getIdSpoonacular() == id) {
                return recipe;
            }
        }
        return null;
    }

    @Override
    public void onDatabaseUpdated() {
        loadRecipes();
    }

    @Override
    public void onRecipeUpdated() {
        loadRecipes();
    }

    private void cleanupRecipesNow() {
        Context context = mApplication.getApplicationContext();
        Intent cleanupService = new Intent(context, CleanupService.class);
        cleanupService.setAction(ACTION_REMOVE_EXPIRED_IMMEDIATELY);
        CleanupService.enqueueWork(context, cleanupService);
    }

    private void scheduleRecipeCleanup(long atTime) {
        Context context = mApplication.getApplicationContext();
        Intent cleanupService = new Intent(context, CleanupService.class);
        cleanupService.setAction(ACTION_REMOVE_EXPIRED_SCHEDULED);
        cleanupService.putExtra(EXTRA_TIMESTAMP_CUTOFF, atTime);
        CleanupService.enqueueWork(context, cleanupService);
    }

    List<Recipe> getRecipes() { return mRecipes; }
}
