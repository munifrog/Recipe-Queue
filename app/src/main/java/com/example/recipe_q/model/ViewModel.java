package com.example.recipe_q.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModel extends AndroidViewModel implements ListManager.Listener, RecipeManager.Listener {
    private ListManager mListManager;
    private Listener mListener;
    private RecipeManager mRecipeManager;

    public interface Listener {
        void onInternetFailure(Throwable throwable);
    }

    public interface ListListener extends Listener {
        void onListDatabaseUpdated();
    }

    public interface RecipeListener extends Listener {
        void onRecipeDatabaseUpdated();
    }

    ViewModel(@NonNull Application application, Listener listener) {
        super(application);
        mListener = listener;
        ListManagerFactory lmf = new ListManagerFactory(application, this);
        mListManager = lmf.getInstance();
        RecipeManagerFactory rmf = new RecipeManagerFactory(application, this);
        mRecipeManager = rmf.getInstance();
    }

    @Override
    public void onListDatabaseUpdated() {
        if (mListener instanceof ListListener) {
            ((ListListener) mListener).onListDatabaseUpdated();
        }
    }

    public void addListItems(List<ListItem> listItems) {
        if (mListManager != null) {
            mListManager.addListItems(listItems);
        }
    }

    public List<ListItemCombined> getSoughtListItems() {
        if (mListManager != null) {
            return mListManager.getSoughtListItems();
        } else {
            return null;
        }
    }

    public List<ListItemCombined> getFoundListItems() {
        if (mListManager != null) {
            return mListManager.getFoundListItems();
        } else {
            return null;
        }
    }

    public void switchContainingList(int position, int list) {
        if (mListManager != null) {
            mListManager.switchContainingList(position, list);
        }
    }

    public void removeFromList(int list, int position) {
        if (mListManager != null) {
            mListManager.removeFromList(list, position);
        }
    }

    public void clearList(int list) {
        if (mListManager != null) {
            mListManager.clearList(list);
        }
    }

    public void storeRecipes(List<Recipe> recipes) { mRecipeManager.storeRecipes(recipes); }
    public List<Recipe> getRecipes() { return mRecipeManager.getRecipes(); }
    public LiveData<List<Recipe>> getLiveRecipes() { return mRecipeManager.getLiveRecipes(); }
    public void loadRecipes() { mRecipeManager.loadRecipes(); }

    @Override
    public void onRecipeDatabaseUpdated() {
        if (mListener instanceof RecipeListener) {
            ((RecipeListener) mListener).onRecipeDatabaseUpdated();
        }
    }
}
