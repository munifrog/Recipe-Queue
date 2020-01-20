package com.example.recipe_q.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.recipe_q.R;
import com.example.recipe_q.adapt.AdapterLinearDirectionGroups;
import com.example.recipe_q.adapt.AdapterLinearIngredients;
import com.example.recipe_q.model.FavoriteRecipe;
import com.example.recipe_q.model.Ingredient;
import com.example.recipe_q.model.ListItem;
import com.example.recipe_q.model.Recipe;
import com.example.recipe_q.model.ViewModel;
import com.example.recipe_q.model.ViewModelFactory;
import com.example.recipe_q.util.Api;
import com.example.recipe_q.widget.WidgetProvider;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.recipe_q.activity.ResultsActivity.RECIPES_PARCELABLE;

public class RecipeActivity extends AppCompatActivity implements Api.RecipeListener,
        Api.RecipeInfoListener, ViewModel.Listener
{
    private static final int ASSUMED_SIMILAR_LIMIT = 10;

    private static final int LAST_INTERNET_OPERATION_NONE = 0;
    private static final int LAST_INTERNET_OPERATION_SIMILAR = 1;
    private static final int LAST_INTERNET_OPERATION_INFORMATION = 2;

    public static final String RECIPE_PARCELABLE = "recipe_parcelable_one";
    public static final String SAVE_INGREDIENT_SENT_STATE = "ingredients_were_sent";

    private ViewModel mViewModel;
    private Recipe mRecipe;
    private ProgressBar mProgress;
    private AdapterLinearIngredients mAdapterIngredients;
    private AdapterLinearDirectionGroups mAdapterDirections;
    private Button mBtnSendToList;
    private boolean mIsFavorite;
    private TextView mTvServings;
    private TextView mTvReadyIn;
    private boolean mIngredientsSent;
    private int mLastInternetOperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        setupViewModel();

        if (savedInstanceState != null && savedInstanceState.containsKey(SAVE_INGREDIENT_SENT_STATE)) {
            mIngredientsSent = savedInstanceState.getBoolean(SAVE_INGREDIENT_SENT_STATE);
        } else {
            mIngredientsSent = false;
        }

        mLastInternetOperation = LAST_INTERNET_OPERATION_NONE;

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
            } catch (RuntimeException e) {
                mRecipe = intent.getParcelableExtra(RECIPE_PARCELABLE);
            }
            FavoriteRecipe favorite = mViewModel.getFavoriteMatch(mRecipe.getIdSpoonacular());
            mIsFavorite = favorite != null;
            showFavoriteStatus();

            ImageView recipeImage = findViewById(R.id.iv_recipe_image);
            Picasso.get().load(mRecipe.getImage()).placeholder(R.drawable.preparation).into(recipeImage);

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
                    sendIngredientsToList();
                }
            });
            mBtnSendToList.setEnabled(ingredients.size() != 0);
            setSendButtonText();

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
        Recipe toReturn = mViewModel.getRecipeMatch(id);
        if (toReturn == null ||
                (toReturn.getIngredients().size() == 0 && toReturn.getDirections().size() == 0))
        {
            mLastInternetOperation = LAST_INTERNET_OPERATION_INFORMATION;

            Api api = new Api(this);
            api.getRecipeSpecific(id);
            mProgress.setVisibility(View.VISIBLE);
        }
        return toReturn;
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
            mLastInternetOperation = LAST_INTERNET_OPERATION_SIMILAR;

            Api api = new Api(this);
            api.getRecipesSimilarTo(mRecipe.getIdSpoonacular(), ASSUMED_SIMILAR_LIMIT);
            mProgress.setVisibility(View.VISIBLE);
        }
    }

    private void sendIngredientsToList() {
        if (mRecipe != null) {
            if (!mIngredientsSent) {
                String recipeName = mRecipe.getRecipeTitle();
                long id = mRecipe.getIdSpoonacular();

                List<Ingredient> ingredients = mRecipe.getIngredients();
                if (ingredients.size() > 0) {
                    List<ListItem> shoppingList = new ArrayList<>();
                    for (Ingredient ingredient : ingredients) {
                        shoppingList.add(new ListItem(
                                ingredient.getIngredientName(),
                                ingredient.getUnit(),
                                ingredient.getAmount(),
                                0,
                                recipeName,
                                id
                        ));
                    }
                    mViewModel.addListItems(shoppingList);
                    mIngredientsSent = true;
                    setSendButtonText();
                    notifyWidget();
                }
            } else {
                startActivity(new Intent(this, ListActivity.class));
            }
        }
    }

    private void setSendButtonText() {
        mBtnSendToList.setText(mIngredientsSent ? R.string.btn_recipe_open_list : R.string.btn_recipe_send_to_list);
    }

    @Override
    public void onInternetFailure(Throwable throwable) {
        mProgress.setVisibility(View.INVISIBLE);
        // https://www.androidhive.info/2015/09/android-material-design-snackbar-example/
        Snackbar snackbar = Snackbar.make(
                findViewById(R.id.frame_recipe_image),
                getString(R.string.error_failure_internet),
                Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(Color.YELLOW)
        ;
        switch(mLastInternetOperation) {
            default:
            case LAST_INTERNET_OPERATION_NONE:
                break;
            case LAST_INTERNET_OPERATION_SIMILAR:
                snackbar.setAction(getString(R.string.error_retry_joke_similar), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Currently cannot refresh when info fails; running again is cheap though
                        getRecipeInfo(mRecipe.getIdSpoonacular());
                        launchSimilarSearch();
                    }
                });
                break;
            case LAST_INTERNET_OPERATION_INFORMATION:
                snackbar.setAction(getString(R.string.error_retry_joke_information), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getRecipeInfo(mRecipe.getIdSpoonacular());
                    }
                });
                break;
        }
        snackbar.show();
    }

    @Override
    public void onRecipesReturned(List<Recipe> recipes) {
        mProgress.setVisibility(View.GONE);
        mLastInternetOperation = LAST_INTERNET_OPERATION_NONE;
        if (recipes.size() > 0) {
            commitFavoriteStatus();
            mViewModel.storeRecipes(recipes);
            Intent intent = new Intent(this, ResultsFlavorActivity.class);
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

    private void notifyWidget() {
        int [] appWidgetIds = AppWidgetManager.getInstance(this).getAppWidgetIds(
                new ComponentName(this, WidgetProvider.class)
        );
        Intent intent = new Intent(this, WidgetProvider.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        sendBroadcast(intent);
    }

    @Override
    public void onInformationReturned(Recipe recipe) {
        mProgress.setVisibility(View.GONE);
        mRecipe = recipe;
        mLastInternetOperation = LAST_INTERNET_OPERATION_NONE;

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
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVE_INGREDIENT_SENT_STATE, mIngredientsSent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        commitFavoriteStatus();
        if (mViewModel != null) {
            mViewModel.removeListener(this);
        }
    }
}
