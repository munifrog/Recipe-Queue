package com.example.recipe_q.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe_q.R;
import com.example.recipe_q.adapt.AdapterGridFavorites;
import com.example.recipe_q.adapt.AdapterGridSearchResults;
import com.example.recipe_q.model.FavoriteRecipe;
import com.example.recipe_q.model.Recipe;
import com.example.recipe_q.model.ViewModel;
import com.example.recipe_q.model.ViewModelFactory;

import java.util.List;

import static com.example.recipe_q.activity.RecipeActivity.RECIPE_PARCELABLE;

public class ResultsActivity extends AppCompatActivity implements AdapterGridSearchResults.Listener,
        ViewModel.RecipeListener, AdapterGridFavorites.Listener, ViewModel.FavoritesListener
{
    public static final String FAVORITE_PARCELABLE = "favorites_parcelable_array";
    public static final String RECIPES_PARCELABLE = "recipe_parcelable_array";
    public static final String RECIPES_TITLE = "results_display_type";

    private static final int SPAN_LANDSCAPE = 3;
    private static final int SPAN_PORTRAIT = 1;

    private static final int MODE_FAVORITES = 1;
    private static final int MODE_HISTORY = 2;
    private static final int MODE_FRESH_RESULTS = 3;

    private ViewModel mViewModel;
    private AdapterGridSearchResults mAdapterSearch;
    private AdapterGridFavorites mAdapterFavorites;
    private int mMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        setupViewModel();

        int spanCount = (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE ?
                SPAN_LANDSCAPE :
                SPAN_PORTRAIT
        );
        RecyclerView rvFound = findViewById(R.id.rv_results);
        rvFound.setLayoutManager(new GridLayoutManager(this, spanCount));

        List<Recipe> recipes;
        List<FavoriteRecipe> favorites;
        Intent launchingIntent = getIntent();

        int titleResource = launchingIntent.getIntExtra(RECIPES_TITLE, R.string.activity_history_title);
        switch (titleResource) {
            case R.string.activity_favorites_title:
                mMode = MODE_FAVORITES;
                favorites = launchingIntent.getParcelableArrayListExtra(FAVORITE_PARCELABLE);
                mAdapterFavorites = new AdapterGridFavorites(favorites, this);
                rvFound.setAdapter(mAdapterFavorites);
                break;
            default:
            case R.string.activity_history_title:
                mMode = MODE_HISTORY;
                recipes = mViewModel.getRecipes();
                mAdapterSearch = new AdapterGridSearchResults(recipes, this);
                rvFound.setAdapter(mAdapterSearch);
                break;
            case R.string.activity_results_title:
                mMode = MODE_FRESH_RESULTS;
                recipes = launchingIntent.getParcelableArrayListExtra(RECIPES_PARCELABLE);
                mAdapterSearch = new AdapterGridSearchResults(recipes, this);
                rvFound.setAdapter(mAdapterSearch);
                break;
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(titleResource);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onClick(Recipe recipe) {
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra(RECIPE_PARCELABLE, recipe);
        startActivity(intent);
    }

    @Override
    public void onClick(FavoriteRecipe favorite) {
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra(RECIPE_PARCELABLE, favorite);
        startActivity(intent);
    }

    private void setupViewModel() {
        ViewModelFactory vmf = new ViewModelFactory(getApplication(), this);
        mViewModel = ViewModelProviders.of(this, vmf).get(ViewModel.class);
    }

    @Override
    public void onInternetFailure(Throwable throwable) {
        // This should not happen; all recipes are provided through the launching intent
    }

    @Override
    public void onRecipeDatabaseUpdated() {
        if (mMode == MODE_HISTORY && mAdapterSearch != null) {
            mAdapterSearch.setRecipes(mViewModel.getRecipes());
        }
    }

    @Override
    public void onFavoritesUpdated() {
        if (mAdapterFavorites != null) {
            mAdapterFavorites.setFavorites(mViewModel.getFavorites());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch(itemId) {
            default:
                return super.onOptionsItemSelected(item);
            case android.R.id.home:
                // https://stackoverflow.com/a/28691979
                onBackPressed();
                return true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mViewModel != null) {
            mViewModel.removeListener(this);
        }
    }
}
