package com.example.recipe_q.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

public class ViewModel extends AndroidViewModel implements ListManager.Listener,
        RecipeManager.Listener, FavoritesManager.Listener
{
    private ListManager mListManager;
    private ListenerManager mListenerManager;
    private RecipeManager mRecipeManager;
    private FavoritesManager mFavoritesManager;

    public interface Listener {
        void onInternetFailure(Throwable throwable);
    }

    public interface FavoritesListener extends Listener {
        void onFavoritesUpdated();
    }

    public interface ListListener extends Listener {
        void onListDatabaseUpdated();
        void onSwipeComplete();
    }

    public interface RecipeListener extends Listener {
        void onRecipeDatabaseUpdated();
    }

    ViewModel(@NonNull Application application, Listener listener) {
        super(application);
        mListenerManager = ListenerManagerFactory.getInstance();
        mListenerManager.addListener(listener);
        ListManagerFactory lmf = new ListManagerFactory(application, this);
        mListManager = lmf.getInstance();
        RecipeManagerFactory rmf = new RecipeManagerFactory(application, this);
        mRecipeManager = rmf.getInstance();
        FavoritesManagerFactory fmf = new FavoritesManagerFactory(application, this);
        mFavoritesManager = fmf.getInstance();
    }

    @Override
    public void onListDatabaseUpdated() {
        for (Listener listener : mListenerManager.getListeners()) {
            if (listener instanceof ListListener) {
                ((ListListener) listener).onListDatabaseUpdated();
            }
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
    public void updateRecipe(Recipe recipe) { mRecipeManager.updateRecipe(recipe); }
    public Recipe getRecipeMatch(long id) { return mRecipeManager.getRecipeMatch(id); }
    public List<Recipe> getRecipes() { return mRecipeManager.getRecipes(); }

    @Override
    public void onRecipeDatabaseUpdated() {
        for (Listener listener : mListenerManager.getListeners()) {
            if (listener instanceof RecipeListener) {
                ((RecipeListener) listener).onRecipeDatabaseUpdated();
            }
        }
    }

    public FavoriteRecipe getFavoriteMatch(long id) { return mFavoritesManager.getFavoriteMatch(id); }
    public List<FavoriteRecipe> getFavorites() { return mFavoritesManager.getFavorites(); }
    public void addFavorite(FavoriteRecipe favorite) { mFavoritesManager.addFavorite(favorite); }
    public void removeFavorites(long [] ids) { mFavoritesManager.removeFavorites(ids); }

    @Override
    public void onFavoritesUpdated() {
        for (Listener listener : mListenerManager.getListeners()) {
            if (listener instanceof FavoritesListener) {
                ((FavoritesListener) listener).onFavoritesUpdated();
            }
        }
    }

    public void removeListener(ViewModel.Listener listener) { mListenerManager.removeListener(listener); }

    @Override
    public void onSwipeComplete() {
        for (Listener listener : mListenerManager.getListeners()) {
            if (listener instanceof ListListener) {
                ((ListListener) listener).onSwipeComplete();
            }
        }
    }
}
