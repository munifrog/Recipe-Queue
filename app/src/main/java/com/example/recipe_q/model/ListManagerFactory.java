package com.example.recipe_q.model;

import android.app.Application;

public class ListManagerFactory {
    private static final Object LOCK = new Object();
    private static ListManager sInstance;

    private Application mApplication;
    private ListManager.Listener mListener;

    public ListManagerFactory (Application application, ListManager.Listener listener) {
        mApplication = application;
        mListener = listener;
    }

    public ListManager getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new ListManager(mApplication, mListener);
            }
        }
        return sInstance;
    }

}
