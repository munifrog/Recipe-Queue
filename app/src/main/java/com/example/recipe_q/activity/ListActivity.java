package com.example.recipe_q.activity;

import android.os.Bundle;

import com.example.recipe_q.adapt.AdapterLinearListFound;
import com.example.recipe_q.adapt.AdapterLinearListSought;
import com.example.recipe_q.model.ViewModel;
import com.example.recipe_q.model.ViewModelFactory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe_q.R;

public class ListActivity extends AppCompatActivity implements ViewModel.Listener {
    private ViewModel mViewModel;

    private AdapterLinearListSought mAdapterSought;
    private AdapterLinearListFound mAdapterFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupViewModel();

        RecyclerView rvSought = findViewById(R.id.rv_to_find);
        rvSought.setLayoutManager(new LinearLayoutManager(this));
        mAdapterSought = new AdapterLinearListSought(mViewModel);
        rvSought.setAdapter(mAdapterSought);

        RecyclerView rvFound = findViewById(R.id.rv_already_found);
        rvFound.setLayoutManager(new LinearLayoutManager(this));
        mAdapterFound = new AdapterLinearListFound(mViewModel);
        rvFound.setAdapter(mAdapterFound);
    }

    private void setupViewModel() {
        ViewModelFactory vmf = new ViewModelFactory(getApplication(), this);
        mViewModel = ViewModelProviders.of(this, vmf).get(ViewModel.class);
    }

    @Override
    public void onInternetFailure(Throwable throwable) {
        // Do nothing: only the database is involved and it does not require internet
    }

    @Override
    public void onListDatabaseUpdated() {
        mAdapterSought.onUpdated();
        mAdapterFound.onUpdated();
    }
}
