package com.example.recipe_q.thread;

import android.os.AsyncTask;

import com.example.recipe_q.db.ListDataBase;
import com.example.recipe_q.model.ListItemCombined;

public class ListSwipeUpdater extends AsyncTask<ListItemCombined, Void, Void> {
    private ListDataBase mDatabaseList;

    public ListSwipeUpdater(ListDataBase database) {
        mDatabaseList = database;
    }

    @Override
    protected Void doInBackground(ListItemCombined... items) {
        int lengthInputArray = items.length;
        ListItemCombined currentItem;
        int lengthCurrentId;
        long [] currentIds;
        long currentTimeStamp;
        for (int i = 0; i < lengthInputArray; i++) {
            currentItem = items[i];
            currentIds = currentItem.getIndices();
            currentTimeStamp = currentItem.getTimestamp();
            lengthCurrentId = currentIds.length;
            for (int j = 0; j < lengthCurrentId; j++) {
                mDatabaseList.dao().updateTimestamp(currentIds[j], currentTimeStamp);
            }
        }

        return null;
    }
}