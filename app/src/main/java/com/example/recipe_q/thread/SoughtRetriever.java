package com.example.recipe_q.thread;

import android.os.AsyncTask;

import java.util.List;

import com.example.recipe_q.db.ListDataBase;
import com.example.recipe_q.model.ListItem;

public class SoughtRetriever extends AsyncTask<Void, Void, List<ListItem>> {
    private ListDataBase mDatabase;
    private Listener mListener;

    public SoughtRetriever(ListDataBase database, Listener listener) {
        mDatabase = database;
        mListener = listener;
    }

    public interface Listener {
        void onListLoaded(List<ListItem> list);
    }

    @Override
    protected List<ListItem> doInBackground(Void... ignored) {
        return mDatabase.dao().immediateLoadSoughtItems();
    }

    @Override
    protected void onPostExecute(List<ListItem> list) {
        super.onPostExecute(list);
        mListener.onListLoaded(list);
    }
}
