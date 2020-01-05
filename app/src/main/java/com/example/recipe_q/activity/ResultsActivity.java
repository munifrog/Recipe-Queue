package com.example.recipe_q.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe_q.R;
import com.example.recipe_q.adapt.AdapterGridSearchResults;
import com.example.recipe_q.model.Recipe;
import com.example.recipe_q.model.ViewModel;
import com.example.recipe_q.model.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import static com.example.recipe_q.activity.RecipeActivity.RECIPE_PARCELABLE;

public class ResultsActivity extends AppCompatActivity implements AdapterGridSearchResults.Listener,
        ViewModel.RecipeListener
{
    public static final String RECIPES_PARCELABLE = "recipe_parcelable_array";

    private static final int SPAN_LANDSCAPE = 3;
    private static final int SPAN_PORTRAIT = 1;

    private ViewModel mViewModel;
    private AdapterGridSearchResults mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        setupViewModel();

        List<Recipe> recipes;
        Intent launchingIntent = getIntent();
        if (launchingIntent == null || !launchingIntent.hasExtra(RECIPES_PARCELABLE)) {
            recipes = new ArrayList<>();
            mViewModel.loadRecipes();
        } else {
            recipes = launchingIntent.getParcelableArrayListExtra(RECIPES_PARCELABLE);
        }

        int spanCount = (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE ?
                SPAN_LANDSCAPE :
                SPAN_PORTRAIT
        );
        RecyclerView rvFound = findViewById(R.id.rv_results);
        rvFound.setLayoutManager(new GridLayoutManager(this, spanCount));
        mAdapter = new AdapterGridSearchResults(recipes, this);
        rvFound.setAdapter(mAdapter);
    }

    @Override
    public void onClick(Recipe recipe) {
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra(RECIPE_PARCELABLE, recipe);
        startActivity(intent);
    }

    private void setupViewModel() {
        ViewModelFactory vmf = new ViewModelFactory(getApplication(), this);
        mViewModel = ViewModelProviders.of(this, vmf).get(ViewModel.class);
    }

    @Override
    public void onInternetFailure(Throwable throwable) {
        // TODO: Handle this appropriately
    }

    @Override
    public void onRecipeDatabaseUpdated() {
        mAdapter.setRecipes(mViewModel.getRecipes());
    }
}
