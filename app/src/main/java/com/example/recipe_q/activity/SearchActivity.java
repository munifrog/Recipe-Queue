package com.example.recipe_q.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipe_q.R;
import com.example.recipe_q.fragment.SearchCommonFragment;
import com.example.recipe_q.fragment.SearchIngredientFragment;
import com.example.recipe_q.fragment.SearchNutritionFragment;

import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {
    private Switch mSwitchCommon;
    private Switch mSwitchIngredient;
    private Switch mSwitchNutrition;
    private View mFrameCommon;
    private View mFrameIngredient;
    private View mFrameNutrition;
    private SearchCommonFragment mFragmentCommon;
    private SearchIngredientFragment mFragmentIngredient;
    private SearchNutritionFragment mFragmentNutrition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mFragmentCommon = new SearchCommonFragment();
        mFragmentIngredient = new SearchIngredientFragment();
        mFragmentNutrition = new SearchNutritionFragment();

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
        if (isEnabledCommon()) {
            mFragmentCommon.addSearchTerms(searchTerms);
        }
        if (isEnabledIngredient()) {
            mFragmentIngredient.addSearchTerms(searchTerms);
        }
        if (isEnabledNutrition()) {
            mFragmentNutrition.addSearchTerms(searchTerms);
        }
    }
}
