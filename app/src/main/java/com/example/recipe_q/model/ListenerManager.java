package com.example.recipe_q.model;

import java.util.ArrayList;
import java.util.List;

class ListenerManager {
    private List<ViewModel.Listener> mListeners;

    ListenerManager() { mListeners = new ArrayList<>(); }

    void addListener(ViewModel.Listener newListener) { mListeners.add(newListener); }
    void removeListener(ViewModel.Listener newListener) { mListeners.add(newListener); }

    List<ViewModel.Listener> getListeners() { return mListeners; }
}

