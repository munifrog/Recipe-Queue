package com.example.recipe_q.activity;

import android.os.Bundle;

import com.example.recipe_q.adapt.AdapterLinearListFound;
import com.example.recipe_q.adapt.AdapterLinearListSought;
import com.example.recipe_q.model.ViewModel;
import com.example.recipe_q.model.ViewModelFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.example.recipe_q.R;

import static com.example.recipe_q.model.ListManager.LIST_FOUND;
import static com.example.recipe_q.model.ListManager.LIST_SOUGHT;

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

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.switchContainingList(0, LIST_FOUND);
                onListDatabaseUpdated();
            }
        });

        // https://medium.com/@zackcosborn/step-by-step-recyclerview-swipe-to-delete-and-undo-7bbae1fce27e
        ItemTouchHelper touchHelperSought = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.ACTION_STATE_IDLE,
                ItemTouchHelper.END | ItemTouchHelper.START
        ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target
            ) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mViewModel.switchContainingList(viewHolder.getAdapterPosition(), LIST_SOUGHT);
                onListDatabaseUpdated();
            }
        });

        ItemTouchHelper touchHelperFound = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.ACTION_STATE_IDLE,
                ItemTouchHelper.END | ItemTouchHelper.START
        ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target
            ) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mViewModel.switchContainingList(viewHolder.getAdapterPosition(), LIST_FOUND);
                onListDatabaseUpdated();
            }
        });

        RecyclerView rvSought = findViewById(R.id.rv_to_find);
        rvSought.setLayoutManager(new LinearLayoutManager(this));
        mAdapterSought = new AdapterLinearListSought(mViewModel);
        rvSought.setAdapter(mAdapterSought);
        touchHelperSought.attachToRecyclerView(rvSought);

        RecyclerView rvFound = findViewById(R.id.rv_already_found);
        rvFound.setLayoutManager(new LinearLayoutManager(this));
        mAdapterFound = new AdapterLinearListFound(mViewModel);
        rvFound.setAdapter(mAdapterFound);
        touchHelperFound.attachToRecyclerView(rvFound);
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
