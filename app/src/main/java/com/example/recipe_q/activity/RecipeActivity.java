package com.example.recipe_q.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recipe_q.R;
import com.example.recipe_q.adapt.AdapterLinearDirectionGroups;
import com.example.recipe_q.adapt.AdapterLinearIngredients;
import com.example.recipe_q.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class RecipeActivity extends AppCompatActivity {
    public static final String RECIPE_PARCELABLE = "recipe_parcelable_one";

    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent intent = getIntent();
        if (intent != null) {
            mRecipe = intent.getParcelableExtra(RECIPE_PARCELABLE);

            ImageView recipeImage = findViewById(R.id.iv_recipe_image);
            Picasso.get().load(mRecipe.getImage()).placeholder(R.drawable.ic_launcher_background).into(recipeImage);

            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle(mRecipe.getRecipeTitle());
                actionBar.setDisplayHomeAsUpEnabled(true);
            }

            AdapterLinearIngredients adapterIngredients = new AdapterLinearIngredients(mRecipe.getIngredients());
            RecyclerView rvIngredients = findViewById(R.id.rv_ingredients);
            rvIngredients.setLayoutManager(new LinearLayoutManager(this));
            rvIngredients.setAdapter(adapterIngredients);

            AdapterLinearDirectionGroups adapterDirections = new AdapterLinearDirectionGroups(mRecipe.getDirections());
            RecyclerView rvDirections = findViewById(R.id.rv_directions);
            rvDirections.setLayoutManager(new LinearLayoutManager(this));
            rvDirections.setAdapter(adapterDirections);

            TextView tvServings = findViewById(R.id.tv_servings_amount);
            tvServings.setText(String.format(Locale.getDefault(), "%1$.2f", mRecipe.getServings()));
            TextView tvReadyIn = findViewById(R.id.tv_ready_in_minutes);
            tvReadyIn.setText(String.format(Locale.getDefault(), "%1$d", mRecipe.getReadyInMinutes()));
            ImageView ivFavorite = findViewById(R.id.iv_favorite);
            ivFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            Button btnSendToList = findViewById(R.id.btn_send_to_list);
            btnSendToList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            Button btnFindSimilar = findViewById(R.id.btn_find_similar);
            btnFindSimilar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

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

    private void launchBrowser() {
        if (mRecipe != null) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mRecipe.getSourceUrl()));
            startActivity(browserIntent);
        }
    }
}
