package com.example.recipe_q.thread;

import android.os.AsyncTask;

import java.util.List;

import com.example.recipe_q.db.RecipeDatabase;
import com.example.recipe_q.model.Recipe;

public class RecipeInserter extends AsyncTask<List<Recipe>, Void, Void> {
    private RecipeDatabase mDatabase;
    private Listener mListener;

    public RecipeInserter(RecipeDatabase database, Listener listener) {
        mDatabase = database;
        mListener = listener;
    }

    public interface Listener {
        void onDatabaseUpdated();
    }

    @Override
    protected Void doInBackground(List<Recipe>... lists) {
        for (List<Recipe> recipes : lists) {
            mDatabase.dao().insertRecipes(recipes);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void ignored) {
        super.onPostExecute(ignored);
        mListener.onDatabaseUpdated();
    }
}
