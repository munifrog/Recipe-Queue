package com.example.recipe_q.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipe_q.R;
import com.example.recipe_q.fragment.SearchCommonFragment;
import com.example.recipe_q.fragment.SearchNutritionFragment;

public class SearchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_search_common, new SearchCommonFragment())
                    .commit();

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_search_nutrition, new SearchNutritionFragment())
                    .commit();
        }
    }

    public void onCommonClick(View view) {
        Switch toggle = findViewById(R.id.switch_search_common);
        View fragment = findViewById(R.id.fragment_search_common);
        fragment.setVisibility(toggle.isChecked() ? View.VISIBLE : View.GONE);
    }

    public void onNutritionClick(View view) {
        Switch toggle = findViewById(R.id.switch_search_nutrition);
        View fragment = findViewById(R.id.fragment_search_nutrition);
        fragment.setVisibility(toggle.isChecked() ? View.VISIBLE : View.GONE);
    }
}
