package com.example.recipe_q.thread;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.recipe_q.db.ListDataBase;
import com.example.recipe_q.model.ListItemCombined;
import com.example.recipe_q.widget.WidgetProvider;

public class ListSwipeUpdater extends AsyncTask<ListItemCombined, Void, Void> {
    private ListDataBase mDatabaseList;
    private Listener mListener;

    public interface Listener {
        void onSwipeComplete();
    }

    public ListSwipeUpdater(ListDataBase database, Listener listener) {
        mDatabaseList = database;
        mListener = listener;
    }

    @Override
    protected Void doInBackground(ListItemCombined... items) {
        int lengthCurrentId;
        long [] currentIds;
        long currentTimeStamp;

        for (ListItemCombined currentItem : items) {
            currentIds = currentItem.getIndices();
            currentTimeStamp = currentItem.getTimestamp();
            lengthCurrentId = currentIds.length;
            for (int j = 0; j < lengthCurrentId; j++) {
                mDatabaseList.dao().updateTimestamp(currentIds[j], currentTimeStamp);
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void ignored) {
        super.onPostExecute(ignored);
        mListener.onSwipeComplete();
    }
}