package com.example.recipe_q.thread;

import android.os.AsyncTask;

import com.example.recipe_q.db.ListDataBase;
import com.example.recipe_q.model.ListItemCombined;

import java.util.List;

public class ListRemover extends AsyncTask<List<ListItemCombined>, Void, Void> {
    private ListDataBase mDatabaseList;

    public ListRemover(ListDataBase database) {
        mDatabaseList = database;
    }

    @Override
    protected Void doInBackground(List<ListItemCombined>... lists) {
        int numLists = lists.length;

        int numItems;
        ListItemCombined currentItem;
        long [] currentIds;

        for (int i = 0; i < numLists; i++) {
            numItems = lists[i].size();
            for (int j = 0; j < numItems; j++) {
                currentItem = lists[i].get(j);
                currentIds = currentItem.getIndices();
                mDatabaseList.dao().deleteItems(currentIds);
            }
        }

        return null;
    }

}
