package com.example.recipe_q.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.recipe_q.R;
import com.example.recipe_q.model.Recipe;

public class RecipeActivity extends AppCompatActivity {
    public static final String RECIPE_PARCELABLE = "recipe_parcelable_one";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent intent = getIntent();
        if (intent != null) {
            Recipe recipe = intent.getParcelableExtra(RECIPE_PARCELABLE);
        }
    }
}
