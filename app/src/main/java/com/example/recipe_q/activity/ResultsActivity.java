package com.example.recipe_q.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe_q.R;
import com.example.recipe_q.adapt.AdapterGridSearchResults;
import com.example.recipe_q.model.Recipe;

import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity {
    public static final String RECIPES_PARCELABLE = "recipe_parcelable_array";

    private static final int SPAN_LANDSCAPE = 3;
    private static final int SPAN_PORTRAIT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        ArrayList<Recipe> recipes;
        Intent launchingIntent = getIntent();
        if (launchingIntent == null) {
            recipes = new ArrayList<>();
        } else {
            recipes = launchingIntent.getParcelableArrayListExtra(RECIPES_PARCELABLE);
        }

        int spanCount = (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE ?
                SPAN_LANDSCAPE :
                SPAN_PORTRAIT
        );
        RecyclerView rvFound = findViewById(R.id.rv_results);
        rvFound.setLayoutManager(new GridLayoutManager(this, spanCount));
        AdapterGridSearchResults adapter = new AdapterGridSearchResults(recipes);
        rvFound.setAdapter(adapter);
    }
}
