package com.example.recipe_q.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.TextView;

import com.example.recipe_q.R;
import com.example.recipe_q.model.ListItem;
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
        List<ListItem> list = mViewModel.getListItems();

        if (list != null) {
            ListItem current;
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
}
