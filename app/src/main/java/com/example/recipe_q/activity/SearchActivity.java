package com.example.recipe_q.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.recipe_q.R;
import com.example.recipe_q.fragment.SearchCommonFragment;
import com.example.recipe_q.fragment.SearchIngredientFragment;
import com.example.recipe_q.fragment.SearchNutritionFragment;
import com.example.recipe_q.model.Recipe;
import com.example.recipe_q.model.ViewModel;
import com.example.recipe_q.model.ViewModelFactory;
import com.example.recipe_q.util.Api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.recipe_q.activity.ResultsActivity.RECIPES_PARCELABLE;
import static com.example.recipe_q.activity.ResultsActivity.RECIPES_TITLE;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_COUNT_NUMBER;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_INGREDIENTS_FILL;
import static com.example.recipe_q.util.Api.QUERY_COMPLEX_RECIPE_ADD_INFO;

public class SearchActivity extends AppCompatActivity implements Api.RecipeListener,
        ViewModel.Listener
{
    private static final int RECIPE_COUNT_MAXIMUM = 5;

    private ViewModel mViewModel;
    private Switch mSwitchCommon;
    private Switch mSwitchIngredient;
    private Switch mSwitchNutrition;
    private View mFrameCommon;
    private View mFrameIngredient;
    private View mFrameNutrition;
    private SearchCommonFragment mFragmentCommon;
    private SearchIngredientFragment mFragmentIngredient;
    private SearchNutritionFragment mFragmentNutrition;
    private Api mApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setupViewModel();

        mFragmentCommon = new SearchCommonFragment();
        mFragmentIngredient = new SearchIngredientFragment();
        mFragmentNutrition = new SearchNutritionFragment();
        mApi = new Api(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_search_common, mFragmentCommon)
                    .commit();

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_search_ingredient, mFragmentIngredient)
                    .commit();

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_search_nutrition, mFragmentNutrition)
                    .commit();
        }

        mSwitchCommon = findViewById(R.id.switch_search_common);
        mSwitchCommon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCommonClick();
            }
        });
        mSwitchIngredient = findViewById(R.id.switch_search_ingredient);
        mSwitchIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onIngredientClick();
            }
        });
        mSwitchNutrition = findViewById(R.id.switch_search_nutrition);
        mSwitchNutrition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNutritionClick();
            }
        });

        mFrameCommon = findViewById(R.id.fragment_search_common);
        mFrameIngredient = findViewById(R.id.fragment_search_ingredient);
        mFrameNutrition = findViewById(R.id.fragment_search_nutrition);

        Button btnPerformSearch = findViewById(R.id.btn_execute_search);
        btnPerformSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLaunchClick();
            }
        });
    }

    private void setupViewModel() {
        ViewModelFactory vmf = new ViewModelFactory(getApplication(), this);
        mViewModel = ViewModelProviders.of(this, vmf).get(ViewModel.class);
    }

    private void onCommonClick() {
        setVisibilityCommon();
    }

    private void onIngredientClick() {
        setVisibilityIngredient();
    }

    private void onNutritionClick() {
        setVisibilityNutrition();
    }

    private boolean isEnabledCommon() {
        return mSwitchCommon.isChecked();
    }

    private boolean isEnabledIngredient() {
        return mSwitchIngredient.isChecked();
    }

    private boolean isEnabledNutrition() {
        return mSwitchNutrition.isChecked();
    }

    public void setVisibilityCommon() {
        mFrameCommon.setVisibility(isEnabledCommon() ? View.VISIBLE : View.GONE);
    }

    public void setVisibilityIngredient() {
        mFrameIngredient.setVisibility(isEnabledIngredient() ? View.VISIBLE : View.GONE);
    }

    public void setVisibilityNutrition() {
        mFrameNutrition.setVisibility(isEnabledNutrition() ? View.VISIBLE : View.GONE);
    }

    private void onLaunchClick() {
        Map<String, String> searchTerms = new HashMap<>();
        searchTerms.put(QUERY_COMPLEX_COUNT_NUMBER, Integer.toString(RECIPE_COUNT_MAXIMUM));
        searchTerms.put(QUERY_COMPLEX_INGREDIENTS_FILL, Boolean.toString(true));
        searchTerms.put(QUERY_COMPLEX_RECIPE_ADD_INFO, Boolean.toString(true));
        if (isEnabledCommon()) {
            mFragmentCommon.addSearchTerms(searchTerms);
        }
        if (isEnabledIngredient()) {
            mFragmentIngredient.addSearchTerms(searchTerms);
        }
        if (isEnabledNutrition()) {
            mFragmentNutrition.addSearchTerms(searchTerms);
        }

        performSearch(searchTerms);
    }

    private void performSearch(Map<String, String> searchTerms) {
        mApi.getRecipesComplexSearch(searchTerms);
    }

    @Override
    public void onInternetFailure(Throwable throwable) {
        // TODO: Handle appropriately
    }

    @Override
    public void onRecipesReturned(List<Recipe> recipes) {
        if (recipes.size() > 0) {
            mViewModel.storeRecipes(recipes);
            Intent intent = new Intent(this, ResultsActivity.class);
            intent.putParcelableArrayListExtra(RECIPES_PARCELABLE, (ArrayList<Recipe>) recipes);
            intent.putExtra(RECIPES_TITLE, R.string.activity_results_title);
            startActivity(intent);
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
