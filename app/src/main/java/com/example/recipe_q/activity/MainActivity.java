package com.example.recipe_q.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.recipe_q.R;
import com.example.recipe_q.model.FavoriteRecipe;
import com.example.recipe_q.model.ViewModel;
import com.example.recipe_q.model.ViewModelFactory;
import com.example.recipe_q.util.Api;

import java.util.ArrayList;

import static com.example.recipe_q.activity.ResultsActivity.FAVORITE_PARCELABLE;
import static com.example.recipe_q.activity.ResultsActivity.RECIPES_TITLE;

public class MainActivity extends AppCompatActivity implements Api.JokeListener, ViewModel.Listener {
    private static final String LATEST_STORED_JOKE = "latest_stored_joke";

    private ViewModel mViewModel;
    private String mLatestJoke;
    private TextView mTvJoke;
    private ProgressBar mProgress;
    private boolean mIsRetrieving;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViewModel();

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

    private void setupViewModel() {
        ViewModelFactory vmf = new ViewModelFactory(getApplication(), this);
        mViewModel = ViewModelProviders.of(this, vmf).get(ViewModel.class);
    }

    @Override
    public void onInternetFailure(Throwable throwable) {
        mIsRetrieving = false;
        showProgressBar();
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
