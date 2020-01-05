package com.example.recipe_q.activity;

import android.os.Bundle;

import com.example.recipe_q.adapt.AdapterLinearList;
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

import android.view.ActionMode;
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
    private static final int INVALID_INDEX = -1;

    private ViewModel mViewModel;
    private ActionMode.Callback mContextMenuCallback;
    private AdapterLinearList mAdapterSought;
    private AdapterLinearList mAdapterFound;
    private ActionMode mActionMode;
    private int mSelectionListType;
    private int mSelectionPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupViewModel();
        setupContextMenuCallback();
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupViewModel() {
        ViewModelFactory vmf = new ViewModelFactory(getApplication(), this);
        mViewModel = ViewModelProviders.of(this, vmf).get(ViewModel.class);
    }

    private void setupContextMenuCallback() {
        mActionMode = null;
        mContextMenuCallback = new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.list_context, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                int action = item.getItemId();
                switch(action) {
                    case R.id.action_delete_list:
                        if (mSelectionListType == LIST_SOUGHT) {
                            mViewModel.clearList(LIST_SOUGHT);
                            onListDatabaseUpdated();
                            mode.finish();
                            return true;
                        } else if (mSelectionListType == LIST_FOUND) {
                            mViewModel.clearList(LIST_FOUND);
                            onListDatabaseUpdated();
                            mode.finish();
                            return true;
                        }
                        return false;
                    case R.id.action_delete_selected:
                        if (mSelectionPosition != INVALID_INDEX) {
                            mViewModel.removeFromList(mSelectionListType, mSelectionPosition);
                            onListDatabaseUpdated();
                            mode.finish();
                            return true;
                        } else {
                            return false;
                        }
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                mSelectionListType = INVALID_INDEX;
                mSelectionPosition = INVALID_INDEX;
                mActionMode = null;
            }
        };
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
        mAdapterFound = new AdapterLinearList(mViewModel, LIST_FOUND, this);
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
        mAdapterSought = new AdapterLinearList(mViewModel, LIST_SOUGHT, this);
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

    @Override
    public boolean onLongClick(int position, int list) {
        if (mActionMode == null) {
            mSelectionListType = list;
            mSelectionPosition = position;
            mActionMode = startActionMode(mContextMenuCallback);
            return true;
        }
        return false;
    }
}
