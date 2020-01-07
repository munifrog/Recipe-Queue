package com.example.recipe_q.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
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
import com.example.recipe_q.model.FavoriteRecipe;
import com.example.recipe_q.model.Recipe;
import com.example.recipe_q.model.ViewModel;
import com.example.recipe_q.model.ViewModelFactory;
import com.example.recipe_q.util.Api;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.recipe_q.activity.ResultsActivity.RECIPES_PARCELABLE;

public class RecipeActivity extends AppCompatActivity implements Api.RecipeListener,
        Api.RecipeInfoListener, ViewModel.Listener
{
    private static final int ASSUMED_SIMILAR_LIMIT = 10;

    public static final String RECIPE_PARCELABLE = "recipe_parcelable_one";

    private ViewModel mViewModel;
    private Recipe mRecipe;
    private ProgressBar mProgress;
    private AdapterLinearIngredients mAdapterIngredients;
    private AdapterLinearDirectionGroups mAdapterDirections;
    private Button mBtnSendToList;
    private boolean mIsFavorite;
    private TextView mTvServings;
    private TextView mTvReadyIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        setupViewModel();

        Intent intent = getIntent();
        if (intent != null) {
            mProgress = findViewById(R.id.progress_bar);
            try {
                FavoriteRecipe favorite = intent.getParcelableExtra(RECIPE_PARCELABLE);
                Recipe fullRecipe = getRecipeInfo(favorite.getIdSpoonacular());
                if (fullRecipe != null) {
                    mRecipe = fullRecipe;
                } else {
                    mRecipe = favorite.asFullRecipe();
                }
                mIsFavorite = true;
            } catch (RuntimeException e) {
                mRecipe = intent.getParcelableExtra(RECIPE_PARCELABLE);
                FavoriteRecipe favorite = mViewModel.getFavoriteMatch(mRecipe.getIdSpoonacular());
                mIsFavorite = favorite != null;
            }
            showFavoriteStatus();

            ImageView recipeImage = findViewById(R.id.iv_recipe_image);
            Picasso.get().load(mRecipe.getImage()).placeholder(R.drawable.ic_launcher_background).into(recipeImage);

            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle(mRecipe.getRecipeTitle());
                actionBar.setDisplayHomeAsUpEnabled(true);
            }

            List ingredients = mRecipe.getIngredients();
            List directions = mRecipe.getDirections();
            if (ingredients.size() == 0 && directions.size() == 0) {
                Recipe recipe = getRecipeInfo(mRecipe.getIdSpoonacular());
                if (recipe != null) {
                    mRecipe = recipe;
                }
            }

            // noinspection unchecked
            mAdapterIngredients = new AdapterLinearIngredients(ingredients);
            RecyclerView rvIngredients = findViewById(R.id.rv_ingredients);
            rvIngredients.setLayoutManager(new LinearLayoutManager(this));
            rvIngredients.setAdapter(mAdapterIngredients);

            // noinspection unchecked
            mAdapterDirections = new AdapterLinearDirectionGroups(directions);
            RecyclerView rvDirections = findViewById(R.id.rv_directions);
            rvDirections.setLayoutManager(new LinearLayoutManager(this));
            rvDirections.setAdapter(mAdapterDirections);

            mTvServings = findViewById(R.id.tv_servings_amount);
            mTvReadyIn = findViewById(R.id.tv_ready_in_minutes);
            updateAmounts();

            ImageView ivFavorite = findViewById(R.id.iv_favorite);
            ivFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleFavoriteStatus();
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

    private Recipe getRecipeInfo(long id) {
        Recipe recipeMatch = mViewModel.getRecipeMatch(id);
        if (recipeMatch != null) {
            return recipeMatch;
        } else {
            Api api = new Api(this);
            api.getRecipeSpecific(id);
            mProgress.setVisibility(View.VISIBLE);
            return null;
        }
    }

    private void launchBrowser() {
        if (mRecipe != null) {
            commitFavoriteStatus();
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
    public void onRecipesReturned(List<Recipe> recipes) {
        if (recipes.size() > 0) {
            commitFavoriteStatus();
            mViewModel.storeRecipes(recipes);
            Intent intent = new Intent(this, ResultsActivity.class);
            intent.putParcelableArrayListExtra(RECIPES_PARCELABLE, (ArrayList<Recipe>) recipes);
            startActivity(intent);
        }
    }

    private void setupViewModel() {
        ViewModelFactory vmf = new ViewModelFactory(getApplication(), this);
        mViewModel = ViewModelProviders.of(this, vmf).get(ViewModel.class);
    }

    private void updateAmounts() {
        mTvServings.setText(String.format(Locale.getDefault(), "%1$.2f", mRecipe.getServings()));
        mTvReadyIn.setText(String.format(Locale.getDefault(), "%1$d", mRecipe.getReadyInMinutes()));
    }

    private void toggleFavoriteStatus() {
        mIsFavorite = !mIsFavorite;
        showFavoriteStatus();
    }

    private void showFavoriteStatus() {
        ImageView favoriteMarker = findViewById(R.id.iv_favorite);
        favoriteMarker.setImageResource(
                // See https://stackoverflow.com/questions/3201643/how-to-use-default-android-drawables
                (mIsFavorite ?
                        android.R.drawable.star_big_on :
                        android.R.drawable.star_big_off)
        );
    }

    private void commitFavoriteStatus() {
        if (mIsFavorite) {
            mViewModel.addFavorite(mRecipe.asFavoriteRecipe());
        } else {
            mViewModel.removeFavorites(new long[] { mRecipe.getIdSpoonacular() });
        }
    }

    @Override
    public void onInformationReturned(Recipe recipe) {
        mProgress.setVisibility(View.GONE);
        mRecipe = recipe;

        List<Recipe> savedRecipe = new ArrayList<>();
        savedRecipe.add(mRecipe);
        mViewModel.storeRecipes(savedRecipe);

        mAdapterIngredients.setIngredients(recipe.getIngredients());
        mAdapterDirections.setDirections(recipe.getDirections());
        mBtnSendToList.setEnabled(recipe.getIngredients().size() != 0);
        mViewModel.updateRecipe(recipe);
        updateAmounts();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        commitFavoriteStatus();
    }
}
