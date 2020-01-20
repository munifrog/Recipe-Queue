package com.example.recipe_q.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.recipe_q.R;
import com.example.recipe_q.model.FavoriteRecipe;
import com.example.recipe_q.model.ViewModel;
import com.example.recipe_q.model.ViewModelFactory;
import com.example.recipe_q.util.Api;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.recipe_q.activity.RecipeActivity.RECIPE_PARCELABLE;
import static com.example.recipe_q.activity.ResultsActivity.FAVORITE_PARCELABLE;
import static com.example.recipe_q.activity.ResultsActivity.RECIPES_TITLE;
import static com.example.recipe_q.service.MessagingService.FIREBASE_RECEIVED_RECIPE_ID;
import static com.example.recipe_q.service.MessagingService.FIREBASE_RECEIVED_RECIPE_IMAGE;
import static com.example.recipe_q.service.MessagingService.FIREBASE_RECEIVED_RECIPE_NAME;

public class MainActivity extends AppCompatActivity implements Api.JokeListener, ViewModel.Listener {
    private static final String LATEST_STORED_JOKE = "latest_stored_joke";
    private static final long INVALID_RECIPE_ID = 0;
    private static final String INVALID_RECIPE_NAME = "Name not retrieved";
    private static final String INVALID_RECIPE_IMAGE = "Image not retrieved";

    private ViewModel mViewModel;
    private String mLatestJoke;
    private TextView mTvJoke;
    private ProgressBar mProgress;
    private boolean mIsRetrieving;
    private FavoriteRecipe mFirebaseRecipe;
    private FrameLayout mFmPushedRecipe;
    private ImageView mPushedRecipe;
    private ImageView mPushedScrim;
    private TextView mTvRecipeName;
    private boolean mFirebaseRecipeChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViewModel();

        mFmPushedRecipe = findViewById(R.id.iv_pushed_recipe_frame);
        mPushedScrim = findViewById(R.id.iv_pushed_scrim);
        mTvRecipeName = findViewById(R.id.tv_recipe_name);
        mPushedRecipe = findViewById(R.id.iv_pushed_recipe);
        mPushedRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyScrim(true);
                launchPushed();
            }
        });
        mPushedRecipe.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                applyScrim(false);
            }
        });

        mTvJoke = findViewById(R.id.joke_display);
        if (savedInstanceState != null && savedInstanceState.containsKey(LATEST_STORED_JOKE)) {
            mLatestJoke = savedInstanceState.getString(LATEST_STORED_JOKE);
        } else {
            mLatestJoke = getString(R.string.joke_caution);
        }
        mTvJoke.setText(mLatestJoke);
        mProgress = findViewById(R.id.progress_bar);
        mIsRetrieving = false;
        showProgressBar();

        Button launchShoppingList = findViewById(R.id.btn_launch_list);
        launchShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchShoppingList();
            }
        });

        Button launchFavoritesDisplay = findViewById(R.id.btn_launch_favorites);
        launchFavoritesDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchFavoritesDisplay();
            }
        });

        Button launchJokeRetrieval = findViewById(R.id.btn_show_joke);
        launchJokeRetrieval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchJokeRetrieval();
            }
        });

        Button launchSearch = findViewById(R.id.btn_launch_search);
        launchSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchSearch();
            }
        });

        Button launchHistory = findViewById(R.id.btn_launch_history);
        launchHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHistory();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveRecipePushed();
        if (mFirebaseRecipeChanged) {
            if (mFirebaseRecipe != null) {
                Picasso.get().load(mFirebaseRecipe.getImage()).placeholder(R.drawable.preparation).into(mPushedRecipe);
                mTvRecipeName.setText(mFirebaseRecipe.getRecipeTitle());
                mFmPushedRecipe.setVisibility(View.VISIBLE);
            } else {
                mFmPushedRecipe.setVisibility(View.GONE);
            }
        }
        applyScrim(false);
    }

    private void applyScrim(boolean clicked) {
        int color;
        if (mPushedRecipe.hasFocus()) {
            if (clicked) {
                color = R.color.scrim_pressed;
            } else {
                color = R.color.scrim_focused;
            }
        } else {
            color = R.color.scrim_neutral;
        }
        mPushedScrim.setBackgroundColor(getResources().getColor(color));
    }

    private void retrieveRecipePushed() {
        long oldId = mFirebaseRecipe == null ? 0 : mFirebaseRecipe.getIdSpoonacular();
        Intent intent = getIntent();
        if (intent.hasExtra(FIREBASE_RECEIVED_RECIPE_ID)) {
            String reference = intent.getStringExtra(FIREBASE_RECEIVED_RECIPE_ID);
            long id = Long.parseLong(reference);
            mFirebaseRecipeChanged = oldId != id;
            if (mFirebaseRecipeChanged) {
                String title = intent.getStringExtra(FIREBASE_RECEIVED_RECIPE_NAME);
                String image = intent.getStringExtra(FIREBASE_RECEIVED_RECIPE_IMAGE);
                mFirebaseRecipe = new FavoriteRecipe(id, title, image);
                saveRecipe(mFirebaseRecipe);
            }
        } else {
            mFirebaseRecipe = retrieveRecipeSaved();
            if (mFirebaseRecipe != null) {
                long id = mFirebaseRecipe.getIdSpoonacular();
                mFirebaseRecipeChanged = oldId != id;
            }
        }
    }

    private FavoriteRecipe retrieveRecipeSaved() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        FavoriteRecipe recipe = null;
        if (prefs.contains(FIREBASE_RECEIVED_RECIPE_ID)) {
            long id = prefs.getLong(FIREBASE_RECEIVED_RECIPE_ID, INVALID_RECIPE_ID);
            String title = prefs.getString(FIREBASE_RECEIVED_RECIPE_NAME, INVALID_RECIPE_NAME);
            String image = prefs.getString(FIREBASE_RECEIVED_RECIPE_IMAGE, INVALID_RECIPE_IMAGE);
            recipe = new FavoriteRecipe(id, title, image);
        }
        return recipe;
    }

    private void saveRecipe(FavoriteRecipe recipe) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putLong(FIREBASE_RECEIVED_RECIPE_ID, recipe.getIdSpoonacular());
        edit.putString(FIREBASE_RECEIVED_RECIPE_NAME, recipe.getRecipeTitle());
        edit.putString(FIREBASE_RECEIVED_RECIPE_IMAGE, recipe.getImage());
        edit.apply();
    }

    private void setupViewModel() {
        ViewModelFactory vmf = new ViewModelFactory(getApplication(), this);
        mViewModel = ViewModelProviders.of(this, vmf).get(ViewModel.class);
    }

    private void toggleProgress() {
        mIsRetrieving = !mIsRetrieving;
        showProgressBar();
    }

    @Override
    public void onInternetFailure(Throwable throwable) {
        if (mIsRetrieving) {
            toggleProgress();
        }

        // https://www.androidhive.info/2015/09/android-material-design-snackbar-example/
        Snackbar.make(
                findViewById(R.id.ll_buttons),
                getString(R.string.error_failure_internet),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.error_retry_joke_retrieval), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        launchJokeRetrieval();
                    }
                })
                .setActionTextColor(Color.YELLOW)
                .show()
        ;
    }

    @Override
    public void onJokeRetrieved(String joke) {
        mIsRetrieving = false;
        showProgressBar();
        mLatestJoke = joke;
        mTvJoke.setText(mLatestJoke);
    }

    private void showProgressBar() {
        mProgress.setVisibility(mIsRetrieving ? View.VISIBLE : View.GONE);
    }

    private void launchFavoritesDisplay() {
        Intent intent = new Intent(this, ResultsFlavorActivity.class);
        intent.putParcelableArrayListExtra(
                FAVORITE_PARCELABLE,
                (ArrayList<FavoriteRecipe>) mViewModel.getFavorites()
        );
        intent.putExtra(RECIPES_TITLE, R.string.activity_favorites_title);
        startActivity(intent);
    }

    private void launchPushed() {
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra(RECIPE_PARCELABLE, mFirebaseRecipe);
        startActivity(intent);
    }

    private void launchShoppingList() {
        startActivity(new Intent(this, ListActivity.class));
    }

    private void launchSearch() {
        startActivity(new Intent(this, SearchActivity.class));
    }

    private void launchHistory() {
        startActivity(new Intent(this, ResultsFlavorActivity.class));
    }

    private void launchJokeRetrieval() {
        if (!mIsRetrieving) {
            mIsRetrieving = true;
            showProgressBar();
            new Api(this).getRandomJoke();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(LATEST_STORED_JOKE, mLatestJoke);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mViewModel != null) {
            mViewModel.removeListener(this);
        }
    }
}
