package com.example.recipe_q.model;

class ListenerManagerFactory {
    private static final Object LOCK = new Object();
    private static ListenerManager sInstance;

    static ListenerManager getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new ListenerManager();
            }
        }
        return sInstance;
    }
}
