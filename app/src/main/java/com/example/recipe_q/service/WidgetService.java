package com.example.recipe_q.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.example.recipe_q.widget.WidgetFactory;

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetFactory(getApplication().getApplicationContext());
    }
}
