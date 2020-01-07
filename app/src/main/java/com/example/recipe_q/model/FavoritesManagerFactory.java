package com.example.recipe_q.model;

import android.app.Application;

class FavoritesManagerFactory {
    private static final Object LOCK = new Object();
    private static FavoritesManager sInstance;

    private Application mApplication;
    private FavoritesManager.Listener mListener;

    FavoritesManagerFactory (Application application, FavoritesManager.Listener listener) {
        mApplication = application;
        mListener = listener;
    }

    FavoritesManager getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new FavoritesManager(mApplication, mListener);
            }
        }
        return sInstance;
    }
}
