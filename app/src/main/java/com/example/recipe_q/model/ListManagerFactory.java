package com.example.recipe_q.model;

import android.app.Application;

class ListManagerFactory {
    private static final Object LOCK = new Object();
    private static ListManager sInstance;

    private Application mApplication;
    private ListManager.Listener mListener;

    ListManagerFactory (Application application, ListManager.Listener listener) {
        mApplication = application;
        mListener = listener;
    }

    ListManager getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new ListManager(mApplication, mListener);
            }
        }
        return sInstance;
    }

}
