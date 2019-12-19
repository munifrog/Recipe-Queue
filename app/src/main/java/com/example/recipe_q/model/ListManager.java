package com.example.recipe_q.model;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.recipe_q.db.ListDataBase;
import com.example.recipe_q.thread.ListInserter;
import com.example.recipe_q.thread.ListRemover;
import com.example.recipe_q.thread.ListSwipeUpdater;

import java.util.ArrayList;
import java.util.List;

public class ListManager implements ListInserter.Listener {
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
    }

    ListManager(Application application, Listener listener) {
        mListener = listener;

        mDatabaseList = ListDataBase.getInstance(application);

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
        mSoughtListCombined = new ArrayList<>();

        if (mSoughtListRaw.size() > 0) {
            ListItem currentItem = mSoughtListRaw.get(0);
            ArrayList currentGrouping = new ArrayList<>();
            // noinspection unchecked
            currentGrouping.add(currentItem);

            String newName = currentItem.getName();
            String oldName = newName;

            int soughtSize = mSoughtListRaw.size();
            for (int i = 1; i < soughtSize; i++) {
                currentItem = mSoughtListRaw.get(i);
                newName = currentItem.getName();
                if (newName.equals(oldName)) {
                    // noinspection unchecked
                    currentGrouping.add(currentItem);
                } else {
                    // noinspection unchecked
                    mSoughtListCombined.add(new ListItemCombined(currentGrouping));
                    oldName = newName;
                    currentGrouping = new ArrayList<>();
                    // noinspection unchecked
                    currentGrouping.add(currentItem);
                }
            }
            if (soughtSize > 0) {
                // noinspection unchecked
                mSoughtListCombined.add(new ListItemCombined(currentGrouping));
            }
        }
    }

    private void combineFoundItems() {
        mFoundListCombined = new ArrayList<>();

        if (mFoundListRaw.size() > 0) {
            ListItem currentItem = mFoundListRaw.get(0);
            ArrayList currentGrouping = new ArrayList<>();
            // noinspection unchecked
            currentGrouping.add(currentItem);

            String newName = currentItem.getName();
            String oldName = newName;
            long newTimestamp = currentItem.getTimestamp();
            long oldTimestamp = newTimestamp;

            int foundSize = mFoundListRaw.size();
            for (int i = 1; i < foundSize; i++) {
                currentItem = mFoundListRaw.get(i);
                newName = currentItem.getName();
                newTimestamp = currentItem.getTimestamp();
                // The timestamp and name must be the same to combine as similar items
                if (newTimestamp == oldTimestamp && newName.equals(oldName)) {
                    // noinspection unchecked
                    currentGrouping.add(currentItem);
                } else {
                    // noinspection unchecked
                    mFoundListCombined.add(new ListItemCombined(currentGrouping));
                    oldName = newName;
                    oldTimestamp = newTimestamp;
                    currentGrouping = new ArrayList<>();
                    // noinspection unchecked
                    currentGrouping.add(currentItem);
                }
            }
            if (foundSize > 0) {
                // noinspection unchecked
                mFoundListCombined.add(new ListItemCombined(currentGrouping));
            }
        }
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

    public List<ListItemCombined> getSoughtListItems() { return mSoughtListCombined; }
    public LiveData<List<ListItem>> getLiveSoughtListItems() { return mListSoughtLive; }

    public List<ListItemCombined> getFoundListItems() { return mFoundListCombined; }
    public LiveData<List<ListItem>> getLiveFoundListItems() { return mListFoundLive; }

    public void addListItems(List<ListItem> listItems) {
        // noinspection unchecked
        new ListInserter(mDatabaseList, this).execute(listItems);
    }

    public void reloadList() {
        loadSoughtList();
        loadFoundList();
    }

    public void switchContainingList(int position, int list) {
        if (list == LIST_SOUGHT) {
            if (mSoughtListCombined != null) {
                if (mSoughtListCombined.size() > position) {
                    ListItemCombined switched = mSoughtListCombined.remove(position);
                    switched.setTimestamp();
                    mFoundListCombined.add(insertionPoint(switched, LIST_FOUND), switched);
                    new ListSwipeUpdater(mDatabaseList).execute(switched);
                }
            }
        } else if (list == LIST_FOUND) {
            if (mFoundListCombined != null) {
                if (mFoundListCombined.size() > position) {
                    ListItemCombined switched = mFoundListCombined.remove(position);
                    switched.resetTimestamp();
                    mSoughtListCombined.add(insertionPoint(switched, LIST_SOUGHT), switched);
                    new ListSwipeUpdater(mDatabaseList).execute(switched);
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

    public void removeFromList(int list, int position) {
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

    public void clearList(int list) {
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
}
