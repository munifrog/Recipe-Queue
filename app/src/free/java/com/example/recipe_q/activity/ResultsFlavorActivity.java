package com.example.recipe_q.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.recipe_q.BuildConfig;
import com.example.recipe_q.R;
import com.example.recipe_q.model.FavoriteRecipe;
import com.example.recipe_q.model.Recipe;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import static com.example.recipe_q.activity.RecipeActivity.RECIPE_PARCELABLE;

public class ResultsFlavorActivity extends ResultsActivity {
    private InterstitialAd mInterstitialAd;
    private ProgressBar mProgressBar;
    private boolean mAdsReady;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProgressBar = findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.GONE);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(BuildConfig.ADMOB_KEY_INTERSTITIAL_CODE);
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mAdsReady = true;
                updateProgressBarVisibility();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                mAdsReady = true;
                updateProgressBarVisibility();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                loadNewAd();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNewAd();
    }

    private void loadNewAd() {
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mAdsReady = false;
        updateProgressBarVisibility();
    }

    private void updateProgressBarVisibility() {
        mProgressBar.setVisibility(mAdsReady ? View.GONE : View.VISIBLE);
    }

    private void showInterstitialAd() {
        // Force loaded readiness when possible; Otherwise do not display
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    @Override
    public void onClick(Recipe recipe) {
        if (mAdsReady) {
            showInterstitialAd();
            Intent intent = new Intent(this, RecipeActivity.class);
            intent.putExtra(RECIPE_PARCELABLE, recipe);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(FavoriteRecipe favorite) {
        if (mAdsReady) {
            showInterstitialAd();
            Intent intent = new Intent(this, RecipeActivity.class);
            intent.putExtra(RECIPE_PARCELABLE, favorite);
            startActivity(intent);
        }
    }
}
