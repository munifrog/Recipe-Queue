package com.example.recipe_q.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.recipe_q.R;
import com.example.recipe_q.model.FavoriteRecipe;
import com.example.recipe_q.model.ListItem;
import com.example.recipe_q.model.ListItemCombined;
import com.example.recipe_q.model.ViewModel;
import com.example.recipe_q.model.ViewModelFactory;
import com.example.recipe_q.util.Api;

import java.util.ArrayList;
import java.util.List;

import static com.example.recipe_q.activity.ResultsActivity.FAVORITE_PARCELABLE;
import static com.example.recipe_q.activity.ResultsActivity.RECIPES_TITLE;

public class MainActivity extends AppCompatActivity implements Api.JokeListener, ViewModel.ListListener {
    ViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViewModel();

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

        Button recipe01 = findViewById(R.id.btn_add_recipe_01);
        recipe01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRecipe01();
            }
        });

        Button recipe02 = findViewById(R.id.btn_add_recipe_02);
        recipe02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRecipe02();
            }
        });
    }

    private void setupViewModel() {
        ViewModelFactory vmf = new ViewModelFactory(getApplication(), this);
        mViewModel = ViewModelProviders.of(this, vmf).get(ViewModel.class);
    }

    @Override
    public void onInternetFailure(Throwable throwable) {
        // Do nothing for now
    }

    @Override
    public void onJokeRetrieved(String joke) {
        TextView tv = findViewById(R.id.hello_world);
        tv.setText(joke);
    }

    @Override
    public void onListDatabaseUpdated() {
        List<ListItemCombined> list = mViewModel.getSoughtListItems();

        if (list != null) {
            ListItemCombined current;
            String text = "";
            for (int i = 0; i < list.size(); i++) {
                current = list.get(i);
                text += "\n" +
                        current.getQuantity() + " " +
                        current.getUnit() + " " +
                        current.getName() + " (" +
                        current.getSourceName() + ")"
                ;
            }
            TextView tv = findViewById(R.id.hello_world);
            tv.setText(text);
        }
    }

    private void launchFavoritesDisplay() {
        Intent intent = new Intent(this, ResultsActivity.class);
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
        startActivity(new Intent(this, ResultsActivity.class));
    }

    private void launchJokeRetrieval() {
        new Api(this).getRandomJoke();
    }

    private void addRecipe01() {
        // (2006) White & Farrow, "Best Ever Three & Four Ingredient Cookbook", p.231
        String recipeName = "Penne with Cream and Smoked Salmon";
        List<ListItem> newList = new ArrayList<>();
        newList.add( new ListItem(
                "dried penne",
                "cups",
                3,
                recipeName
        ));
        newList.add( new ListItem(
                "thinly sliced smoked salmon",
                "oz",
                4,
                recipeName
        ));
        newList.add( new ListItem(
                "fresh thyme",
                "sprigs",
                2.5f,
                recipeName
        ));
        newList.add( new ListItem(
                "extra-thick single cream",
                "cup",
                0.66667f,
                recipeName
        ));
        newList.add( new ListItem(
                "butter",
                "tbsp",
                2,
                recipeName
        ));
        newList.add( new ListItem(
                "salt and pepper",
                "serving",
                1,
                recipeName
        ));
        mViewModel.addListItems(newList);
    }

    private void addRecipe02() {
        // (2006) White & Farrow, "Best Ever Three & Four Ingredient Cookbook", p.432
        String recipeName = "Pitta Bread";
        List<ListItem> newList = new ArrayList<>();
        newList.add( new ListItem(
                "bread flour",
                "cups",
                5,
                recipeName
        ));
        newList.add( new ListItem(
                "easy-blend (rapid rise) dried yeast",
                "tsp",
                2.5f,
                recipeName
        ));
        newList.add( new ListItem(
                "olive oil",
                "tbsp",
                1,
                recipeName
        ));
        newList.add( new ListItem(
                "salt",
                "tbsp",
                1,
                recipeName
        ));
        mViewModel.addListItems(newList);
    }
}
