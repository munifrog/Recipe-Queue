package com.example.recipe_q.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

public class ViewModel extends AndroidViewModel implements ListManager.Listener {
    private ListManager mListManager;

    private Listener mListener;


    public interface Listener {
        void onInternetFailure(Throwable throwable);
        void onListDatabaseUpdated();
    }

    ViewModel(@NonNull Application application, Listener listener) {
        super(application);
        mListener = listener;

        mListManager = new ListManager(application, this);

    }

    @Override
    public void onListDatabaseUpdated() {
        mListener.onListDatabaseUpdated();
    }

    public void addListItems(List<ListItem> listItems) {
        if (mListManager != null) {
            mListManager.addListItems(listItems);
        }
    }

    public List<ListItemCombined> getSearchListItems() {
        if (mListManager != null) {
            return mListManager.getSoughtListItems();
        } else {
            return null;
        }
    }

    public List<ListItemCombined> getFoundListItems() {
        if (mListManager != null) {
            return mListManager.getFoundListItems();
        } else {
            return null;
        }
    }
}
