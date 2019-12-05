package com.example.recipe_q;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.recipe_q.util.Api;

public class MainActivity extends AppCompatActivity implements Api.Listener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Api(this)
                .getRandomJoke()
        ;
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
}
