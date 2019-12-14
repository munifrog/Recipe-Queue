package com.example.recipe_q.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.recipe_q.R;
import com.example.recipe_q.model.ListItemCombined;
import com.example.recipe_q.model.ViewModel;
import com.example.recipe_q.model.ViewModelFactory;
import com.example.recipe_q.util.Api;

import java.util.List;

public class MainActivity extends AppCompatActivity implements Api.Listener, ViewModel.Listener {
    ViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViewModel();

        new Api(this)
//                .getRandomJoke()
        ;

        Button launchShoppingList = findViewById(R.id.btn_launch_list);
        launchShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchShoppingList();
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

    private void launchShoppingList() {
        startActivity(new Intent(this, ListActivity.class));
    }
}
