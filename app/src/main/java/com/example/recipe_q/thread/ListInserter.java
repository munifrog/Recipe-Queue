package com.example.recipe_q.thread;

import android.os.AsyncTask;

import java.util.List;

import com.example.recipe_q.model.ListItem;
import com.example.recipe_q.model.ViewModel;

public class ListInserter extends AsyncTask<List<ListItem>, Void, Void> {
    private ViewModel mViewModel;
    private Listener mListener;

    public ListInserter(ViewModel viewModel, Listener listener) {
        mViewModel = viewModel;
        mListener = listener;
    }

    public interface Listener {
        void onListLoaded();
    }

    @Override
    protected Void doInBackground(List<ListItem>... items) {
        mViewModel.getListDatabase().dao().insertItems(items[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void ignored) {
        super.onPostExecute(ignored);
        mListener.onListLoaded();
    }
}

