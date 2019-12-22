package com.example.recipe_q.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.recipe_q.R;
import com.example.recipe_q.custom.Dialog3WaySelect;
import com.example.recipe_q.custom.Control3WaySelect;

public class SearchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
    }

    public void onCuisineClick(View view) {
        Control3WaySelect cuisines = findViewById(R.id.custom_cuisine);
        Dialog3WaySelect dialog = cuisines.getDialog();
        if (dialog != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            dialog.show(fragmentManager, SearchActivity.class.getSimpleName());
            cuisines.updateSummaries();
        }
    }

    public void onIngredientClick(View view) {
        Control3WaySelect ingredients = findViewById(R.id.custom_ingredients);
        Dialog3WaySelect dialog = ingredients.getDialog();
        if (dialog != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            dialog.show(fragmentManager, SearchActivity.class.getSimpleName());
            ingredients.updateSummaries();
        }
    }
}
