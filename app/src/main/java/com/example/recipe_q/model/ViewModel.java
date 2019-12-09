package com.example.recipe_q.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.recipe_q.db.ListDataBase;
import com.example.recipe_q.thread.ListInserter;

import java.util.List;

public class ViewModel extends AndroidViewModel implements ListInserter.Listener {
    private Listener mListener;

    private ListDataBase mDatabaseList;
    private Observer mObserverList;
    private LiveData<List<ListItem>> mListLive;
    private List<ListItem> mList;

    public interface Listener {
        void onInternetFailure(Throwable throwable);
        void onListDatabaseUpdated();
    }

    ViewModel(@NonNull Application application, Listener listener) {
        super(application);
        mListener = listener;

        mDatabaseList = ListDataBase.getInstance(application);
        mObserverList = new Observer<List<ListItem>>() {
            @Override
            public void onChanged(List<ListItem> list) {
                mList = list;
                mListener.onListDatabaseUpdated();
            }
        };
        loadDatabaseList();
    }

    private void removeObserverList() {
        if (mListLive != null) {
            // noinspection unchecked
            mListLive.removeObserver(mObserverList);
        }
    }
    private void loadDatabaseList() {
        removeObserverList();
        mListLive = mDatabaseList.dao().signaledLoadItems();
        // noinspection unchecked
        mListLive.observeForever(mObserverList);
    }

    public ListDataBase getListDatabase() { return mDatabaseList; }

    public List<ListItem> getListItems() { return mList; }
    public LiveData<List<ListItem>> getLiveListItems() { return mListLive; }
    public void addListItems(List<ListItem> listItems) {
        // noinspection unchecked
        new ListInserter(this, this).execute(listItems);
    }

    @Override
    public void onListLoaded() {
        mListener.onListDatabaseUpdated();
    }
}
