package com.example.recipe_q.model;

import android.app.Application;

class RecipeManagerFactory {
    private static final Object LOCK = new Object();
    private static RecipeManager sInstance;

    private Application mApplication;
    private RecipeManager.Listener mListener;

    RecipeManagerFactory (Application application, RecipeManager.Listener listener) {
        mApplication = application;
        mListener = listener;
    }

    RecipeManager getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new RecipeManager(mApplication, mListener);
            }
        }
        return sInstance;
    }
}
