package com.example.recipe_q.thread;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import com.example.recipe_q.db.RecipeDatabase;
import com.example.recipe_q.model.DirectionGroup;
import com.example.recipe_q.model.Ingredient;
import com.example.recipe_q.model.Recipe;

public class RecipeUpdater extends AsyncTask<Recipe, Void, Void> {
    private RecipeDatabase mDatabase;
    private Listener mListener;

    public RecipeUpdater(RecipeDatabase database, Listener listener) {
        mDatabase = database;
        mListener = listener;
    }

    public interface Listener {
        void onRecipeUpdated();
    }

    @Override
    protected Void doInBackground(Recipe... recipes) {
        long id;
        List<Recipe> list;
        List<Ingredient> ingredients;
        List<DirectionGroup> directions;
        for (Recipe recipe : recipes) {
            id = recipe.getIdSpoonacular();

            list = new ArrayList<>();
            list.add(recipe);
            mDatabase.dao().insertRecipes(list);

            ingredients = recipe.getIngredients();
            if (ingredients.size() > 0) {
                mDatabase.dao().updateIngredients(id, ingredients);
            }

            directions = recipe.getDirections();
            if (directions.size() > 0) {
                mDatabase.dao().updateDirections(id, directions);
            }

            String sourceOrig = recipe.getSourceUrl();
            if (!sourceOrig.isEmpty()) {
                mDatabase.dao().updateSourceUrlOrig(id, sourceOrig);
            }

            String sourceApi = recipe.getSourceUrlSpoonacular();
            if (!sourceApi.isEmpty()) {
                mDatabase.dao().updateSourceUrlApi(id, sourceApi);
            }

            mDatabase.dao().updateTimestamp(new long[] { id }, recipe.getRetrievalTime());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void ignored) {
        super.onPostExecute(ignored);
        mListener.onRecipeUpdated();
    }
}
