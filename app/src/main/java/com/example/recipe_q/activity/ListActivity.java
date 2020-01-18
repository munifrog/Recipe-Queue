package com.example.recipe_q.activity;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import com.example.recipe_q.adapt.AdapterLinearList;
import com.example.recipe_q.model.ViewModel;
import com.example.recipe_q.model.ViewModelFactory;
import com.example.recipe_q.widget.WidgetProvider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
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

public class ListActivity extends AppCompatActivity implements ViewModel.ListListener,
        AdapterLinearList.Listener
{
    private ViewModel mViewModel;
    private AdapterLinearList mAdapterSought;
    private AdapterLinearList mAdapterFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupViewModel();
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
        fab.setNextFocusRightId(R.id.rv_to_find);
        fab.setNextFocusLeftId(R.id.rv_already_found);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.activity_list_title);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
            case android.R.id.home:
                // https://stackoverflow.com/a/28691979
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupViewModel() {
        ViewModelFactory vmf = new ViewModelFactory(getApplication(), this);
        mViewModel = ViewModelProviders.of(this, vmf).get(ViewModel.class);
    }

    private void performSwipe(int position, int list) {
        mViewModel.switchContainingList(position, list);
        onListDatabaseUpdated();
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
                performSwipe(viewHolder.getAdapterPosition(), LIST_FOUND);
            }
        });

        RecyclerView rvFound = findViewById(R.id.rv_already_found);
        rvFound.setLayoutManager(new LinearLayoutManager(this));
        mAdapterFound = new AdapterLinearList(mViewModel, LIST_FOUND, this);
        rvFound.setAdapter(mAdapterFound);
        rvFound.setNextFocusRightId(R.id.fab);
        rvFound.setNextFocusLeftId(R.id.rv_to_find);
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
                performSwipe(viewHolder.getAdapterPosition(), LIST_SOUGHT);
            }
        });

        RecyclerView rvSought = findViewById(R.id.rv_to_find);
        rvSought.setLayoutManager(new LinearLayoutManager(this));
        mAdapterSought = new AdapterLinearList(mViewModel, LIST_SOUGHT, this);
        rvSought.setAdapter(mAdapterSought);
        rvSought.setNextFocusRightId(R.id.rv_already_found);
        rvSought.setNextFocusLeftId(R.id.fab);
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
        notifyWidgets();
    }

    @Override
    public boolean onLongClick(int position, int list) {
        performSwipe(position, list);
        return true;
    }

    @Override
    public void onSwipeComplete() {
        notifyWidgets();
    }

    private void notifyWidgets() {
        int [] appWidgetIds = AppWidgetManager.getInstance(this).getAppWidgetIds(
                new ComponentName(this, WidgetProvider.class)
        );
        Intent intent = new Intent(this, WidgetProvider.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mViewModel != null) {
            mViewModel.removeListener(this);
        }
    }
}
