package com.example.recipe_q.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.recipe_q.R;
import com.example.recipe_q.adapt.AdapterLinearDirectionGroups;
import com.example.recipe_q.adapt.AdapterLinearIngredients;
import com.example.recipe_q.model.Recipe;
import com.example.recipe_q.util.Api;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

import static com.example.recipe_q.activity.ResultsActivity.RECIPES_PARCELABLE;

public class RecipeActivity extends AppCompatActivity implements Api.RecipeListener, Api.RecipeInfoListener {
    private static final int ASSUMED_SIMILAR_LIMIT = 10;

    public static final String RECIPE_PARCELABLE = "recipe_parcelable_one";

    private Recipe mRecipe;
    private ProgressBar mProgress;
    private AdapterLinearIngredients mAdapterIngredients;
    private AdapterLinearDirectionGroups mAdapterDirections;
    private Button mBtnSendToList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent intent = getIntent();
        if (intent != null) {
            mRecipe = intent.getParcelableExtra(RECIPE_PARCELABLE);

            ImageView recipeImage = findViewById(R.id.iv_recipe_image);
            Picasso.get().load(mRecipe.getImage()).placeholder(R.drawable.ic_launcher_background).into(recipeImage);

            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle(mRecipe.getRecipeTitle());
                actionBar.setDisplayHomeAsUpEnabled(true);
            }

            mProgress = findViewById(R.id.progress_bar);

            ArrayList ingredients = mRecipe.getIngredients();
            mAdapterIngredients = new AdapterLinearIngredients(ingredients);
            RecyclerView rvIngredients = findViewById(R.id.rv_ingredients);
            rvIngredients.setLayoutManager(new LinearLayoutManager(this));
            rvIngredients.setAdapter(mAdapterIngredients);

            ArrayList directions = mRecipe.getDirections();
            mAdapterDirections = new AdapterLinearDirectionGroups(directions);
            RecyclerView rvDirections = findViewById(R.id.rv_directions);
            rvDirections.setLayoutManager(new LinearLayoutManager(this));
            rvDirections.setAdapter(mAdapterDirections);

            if (ingredients.size() == 0 && directions.size() == 0) {
                Api api = new Api(this);
                api.getRecipeSpecific(mRecipe.getIdSpoonacular());
                mProgress.setVisibility(View.VISIBLE);
            }

            TextView tvServings = findViewById(R.id.tv_servings_amount);
            tvServings.setText(String.format(Locale.getDefault(), "%1$.2f", mRecipe.getServings()));
            TextView tvReadyIn = findViewById(R.id.tv_ready_in_minutes);
            tvReadyIn.setText(String.format(Locale.getDefault(), "%1$d", mRecipe.getReadyInMinutes()));
            ImageView ivFavorite = findViewById(R.id.iv_favorite);
            ivFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            mBtnSendToList = findViewById(R.id.btn_send_to_list);
            mBtnSendToList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            mBtnSendToList.setEnabled(ingredients.size() != 0);
            Button btnFindSimilar = findViewById(R.id.btn_find_similar);
            btnFindSimilar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    launchSimilarSearch();
                }
            });
            Button btnShowOriginal = findViewById(R.id.btn_show_original);
            btnShowOriginal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    launchBrowser();
                }
            });
        }
    }

    private void launchBrowser() {
        if (mRecipe != null) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mRecipe.getSourceUrl()));
            startActivity(browserIntent);
        }
    }

    private void launchSimilarSearch() {
        if (mRecipe != null) {
            Api api = new Api(this);
            api.getRecipesSimilarTo(mRecipe.getIdSpoonacular(), ASSUMED_SIMILAR_LIMIT);
        }
    }

    @Override
    public void onInternetFailure(Throwable throwable) {
        // TODO: Handle this appropriately
    }

    @Override
    public void onRecipesReturned(ArrayList<Recipe> recipes) {
        if (recipes.size() > 0) {
            Intent intent = new Intent(this, ResultsActivity.class);
            intent.putParcelableArrayListExtra(RECIPES_PARCELABLE, recipes);
            startActivity(intent);
        }
    }

    @Override
    public void onInformationReturned(Recipe recipe) {
        mProgress.setVisibility(View.GONE);
        mRecipe = recipe;
        mAdapterIngredients.setIngredients(recipe.getIngredients());
        mAdapterDirections.setDirections(recipe.getDirections());
        mBtnSendToList.setEnabled(recipe.getIngredients().size() != 0);
    }
}
