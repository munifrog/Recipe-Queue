package com.example.recipe_q.model;

import android.content.Context;

public class ListManagerFactory {
    private static final Object LOCK = new Object();
    private static ListManager sInstance;

    private Context mContext;
    private ListManager.Listener mListener;

    public ListManagerFactory (Context context, ListManager.Listener listener) {
        mContext = context;
        mListener = listener;
    }

    public ListManager getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new ListManager(mContext, mListener);
            }
        }
        return sInstance;
    }
}
