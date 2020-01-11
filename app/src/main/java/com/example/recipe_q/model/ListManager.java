package com.example.recipe_q.model;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.recipe_q.db.ListDataBase;
import com.example.recipe_q.thread.ListInserter;
import com.example.recipe_q.thread.ListRemover;
import com.example.recipe_q.thread.ListSwipeUpdater;
import com.example.recipe_q.util.ListItemCombiner;

import java.util.ArrayList;
import java.util.List;

public class ListManager implements ListInserter.Listener, ListSwipeUpdater.Listener {
    public static final int LIST_SOUGHT = 1;
    public static final int LIST_FOUND = 2;

    private ListDataBase mDatabaseList;
    private Listener mListener;

    private Observer mObserverSoughtList;
    private boolean mReadySoughtList;
    private Observer mObserverFoundList;
    private boolean mReadyFoundList;

    private LiveData<List<ListItem>> mListSoughtLive;
    private List<ListItem> mSoughtListRaw;
    private List<ListItemCombined> mSoughtListCombined;

    private LiveData<List<ListItem>> mListFoundLive;
    private List<ListItem> mFoundListRaw;
    private List<ListItemCombined> mFoundListCombined;

    public interface Listener {
        void onListDatabaseUpdated();
        void onSwipeComplete();
    }

    ListManager(Context context, Listener listener) {
        mListener = listener;

        mDatabaseList = ListDataBase.getInstance(context);

        mObserverSoughtList = new Observer<List<ListItem>>() {
            @Override
            public void onChanged(List<ListItem> list) {
                mSoughtListRaw = list;
                mReadySoughtList = true;
                if (mReadyFoundList) {
                    finalizeLists();
                }
            }
        };

        mObserverFoundList = new Observer<List<ListItem>>() {
            @Override
            public void onChanged(List<ListItem> list) {
                mFoundListRaw = list;
                mReadyFoundList = true;
                if (mReadySoughtList) {
                    finalizeLists();
                }
            }
        };

        reloadList();
    }

    private void finalizeLists() {
        combineRepeatItems();
        mListener.onListDatabaseUpdated();
    }

    private void combineRepeatItems() {
        combineSoughtItems();
        combineFoundItems();
    }

    private void combineSoughtItems() {
        mSoughtListCombined = ListItemCombiner.combineSimilar(mSoughtListRaw);
    }

    private void combineFoundItems() {
        mFoundListCombined = ListItemCombiner.combineSimilar(mFoundListRaw);
    }

    private void removeObserverSoughtList() {
        if (mListSoughtLive != null) {
            // noinspection unchecked
            mListSoughtLive.removeObserver(mObserverSoughtList);
        }
    }

    private void removeObserverFoundList() {
        if (mListFoundLive != null) {
            // noinspection unchecked
            mListFoundLive.removeObserver(mObserverFoundList);
        }
    }

    private void loadSoughtList() {
        removeObserverSoughtList();
        mReadySoughtList = false;
        mListSoughtLive = mDatabaseList.dao().signaledLoadSoughtItems();
        // noinspection unchecked
        mListSoughtLive.observeForever(mObserverSoughtList);
    }

    private void loadFoundList() {
        removeObserverFoundList();
        mReadyFoundList = false;
        mListFoundLive = mDatabaseList.dao().signaledLoadFoundItems();
        // noinspection unchecked
        mListFoundLive.observeForever(mObserverFoundList);
    }

    List<ListItemCombined> getSoughtListItems() { return mSoughtListCombined; }
    List<ListItemCombined> getFoundListItems() { return mFoundListCombined; }

    void addListItems(List<ListItem> listItems) {
        // noinspection unchecked
        new ListInserter(mDatabaseList, this).execute(listItems);
    }

    private void reloadList() {
        loadSoughtList();
        loadFoundList();
    }

    void switchContainingList(int position, int list) {
        if (list == LIST_SOUGHT) {
            if (mSoughtListCombined != null) {
                if (mSoughtListCombined.size() > position) {
                    ListItemCombined switched = mSoughtListCombined.remove(position);
                    switched.setTimestamp();
                    mFoundListCombined.add(insertionPoint(switched, LIST_FOUND), switched);
                    new ListSwipeUpdater(mDatabaseList, this).execute(switched);
                }
            }
        } else if (list == LIST_FOUND) {
            if (mFoundListCombined != null) {
                if (mFoundListCombined.size() > position) {
                    ListItemCombined switched = mFoundListCombined.remove(position);
                    switched.resetTimestamp();
                    mSoughtListCombined.add(insertionPoint(switched, LIST_SOUGHT), switched);
                    new ListSwipeUpdater(mDatabaseList, this).execute(switched);
                }
            }
        }
    }

    private int insertionPoint(ListItemCombined item, int list) {
        if (list == LIST_FOUND) {
            // Switching to the found list should always update the timestamp and place first
            return 0;
        } else if (list == LIST_SOUGHT) {
            // We need to find the insertion location - using binary search for efficiency
            int insertionPoint = 0;

            if (mSoughtListCombined != null) {
                // https://www.javatpoint.com/binary-search-in-java
                int first = 0;
                int last = mSoughtListCombined.size() - 1;
                int mid = (first + last) / 2;

                String insertionName = item.getName();
                String currentName;
                while (first <= last) {
                    currentName = mSoughtListCombined.get(mid).getName();
                    // Assume placement immediately before the midpoint
                    insertionPoint = mid;
                    if (currentName.compareTo(insertionName) < 0) {
                        first = mid + 1;
                        insertionPoint++;
                    } else if (currentName.compareTo(insertionName) > 0) {
                        last = mid - 1;
                    } else {
                        break;
                    }
                    mid = (first + last) / 2;
                }

                // Sanitize
                if (insertionPoint < 0) {
                    insertionPoint = 0;
                } else if (insertionPoint > mSoughtListCombined.size()) {
                    insertionPoint = mSoughtListCombined.size();
                }
            }

            return insertionPoint;
        } else {
            // Shouldn't happen; insertion at the end
            return mSoughtListCombined.size();
        }
    }

    void removeFromList(int list, int position) {
        if (list == LIST_SOUGHT) {
            if (mSoughtListCombined != null) {
                if (mSoughtListCombined.size() > position) {
                    ListItemCombined removed = mSoughtListCombined.remove(position);
                    List<ListItemCombined> removedList = new ArrayList<>();
                    removedList.add(removed);
                    // noinspection unchecked
                    new ListRemover(mDatabaseList).execute(removedList);
                }
            }
        } else if (list == LIST_FOUND) {
            if (mFoundListCombined != null) {
                if (mFoundListCombined.size() > position) {
                    ListItemCombined removed = mFoundListCombined.remove(position);
                    List<ListItemCombined> removedList = new ArrayList<>();
                    removedList.add(removed);
                    // noinspection unchecked
                    new ListRemover(mDatabaseList).execute(removedList);
                }
            }
        }
    }

    void clearList(int list) {
        if (list == LIST_SOUGHT) {
            if (mSoughtListCombined != null && mSoughtListCombined.size() > 0) {
                List<ListItemCombined> toRemove = mSoughtListCombined;
                mSoughtListCombined = new ArrayList<>();
                // noinspection unchecked
                new ListRemover(mDatabaseList).execute(toRemove);
            }
        } else if (list == LIST_FOUND) {
            if (mFoundListCombined != null && mFoundListCombined.size() > 0) {
                List<ListItemCombined> toRemove = mFoundListCombined;
                mFoundListCombined = new ArrayList<>();
                // noinspection unchecked
                new ListRemover(mDatabaseList).execute(toRemove);
            }
        }
    }

    @Override
    public void onListLoaded() {
        mListener.onListDatabaseUpdated();
    }

    @Override
    public void onSwipeComplete() {
        mListener.onSwipeComplete();
    }
}
