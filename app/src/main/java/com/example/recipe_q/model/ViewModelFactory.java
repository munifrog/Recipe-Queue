package com.example.recipe_q.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private Application mApplication;
    private ViewModel.Listener mListener;

    public ViewModelFactory(@NonNull Application application, ViewModel.Listener listener) {
        mApplication = application;
        mListener = listener;
    }

    @NonNull
    @Override
    public <T extends androidx.lifecycle.ViewModel> T create(@NonNull Class<T> modelClass) {
        // noinspection unchecked
        return (T) new ViewModel(mApplication, mListener);
    }
}