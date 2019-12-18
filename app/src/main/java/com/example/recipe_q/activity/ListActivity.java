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

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
        setupToolbarMenu();
        setupFoundRecyclerView();
        setupSoughtRecyclerView();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.switchContainingList(0, LIST_FOUND);
                onListDatabaseUpdated();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list, menu);
        return true;
    }

    private void setupViewModel() {
        ViewModelFactory vmf = new ViewModelFactory(getApplication(), this);
        mViewModel = ViewModelProviders.of(this, vmf).get(ViewModel.class);
    }

    private void setupToolbarMenu() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int action = item.getItemId();
                switch(action) {
                    case R.id.action_delete_all:
                        mViewModel.clearList(LIST_SOUGHT);
                        mViewModel.clearList(LIST_FOUND);
                        onListDatabaseUpdated();
                        return true;
                    case R.id.action_delete_found:
                        mViewModel.clearList(LIST_FOUND);
                        onListDatabaseUpdated();
                        return true;
                    case R.id.action_delete_sought:
                        mViewModel.clearList(LIST_SOUGHT);
                        onListDatabaseUpdated();
                        return true;
                }
                return false;
            }
        });
    }

    private void setupFoundRecyclerView() {
        // https://medium.com/@zackcosborn/step-by-step-recyclerview-swipe-to-delete-and-undo-7bbae1fce27e
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

        RecyclerView rvFound = findViewById(R.id.rv_already_found);
        rvFound.setLayoutManager(new LinearLayoutManager(this));
        mAdapterFound = new AdapterLinearListFound(mViewModel);
        rvFound.setAdapter(mAdapterFound);
        touchHelperFound.attachToRecyclerView(rvFound);
    }

    private void setupSoughtRecyclerView() {
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

        RecyclerView rvSought = findViewById(R.id.rv_to_find);
        rvSought.setLayoutManager(new LinearLayoutManager(this));
        mAdapterSought = new AdapterLinearListSought(mViewModel);
        rvSought.setAdapter(mAdapterSought);
        touchHelperSought.attachToRecyclerView(rvSought);
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
