package com.example.recipe_q.thread;

import android.os.AsyncTask;

import java.util.List;

import com.example.recipe_q.db.ListDataBase;
import com.example.recipe_q.model.ListItem;

public class ListInserter extends AsyncTask<List<ListItem>, Void, Void> {
    private ListDataBase mDatabaseList;
    private Listener mListener;

    public ListInserter(ListDataBase database, Listener listener) {
        mDatabaseList = database;
        mListener = listener;
    }

    public interface Listener {
        void onListLoaded();
    }

    @Override
    protected Void doInBackground(List<ListItem>... items) {
        mDatabaseList.dao().insertItems(items[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void ignored) {
        super.onPostExecute(ignored);
        mListener.onListLoaded();
    }
}

