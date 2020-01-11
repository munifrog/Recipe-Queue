package com.example.recipe_q.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.recipe_q.R;
import com.example.recipe_q.activity.ListActivity;
import com.example.recipe_q.db.ListDataBase;
import com.example.recipe_q.model.ListItem;
import com.example.recipe_q.model.ListItemCombined;
import com.example.recipe_q.service.WidgetService;
import com.example.recipe_q.thread.SoughtRetriever;
import com.example.recipe_q.util.ListItemCombiner;

import java.util.List;

public class WidgetProvider extends AppWidgetProvider implements SoughtRetriever.Listener {
    private int [] mWidgetIds;
    private ListDataBase mDatabase;
    private AppWidgetManager mAppWidgetManager;

    private void init(Context context) {
        mDatabase = ListDataBase.getInstance(context);
        mAppWidgetManager = AppWidgetManager.getInstance(context);
    }

    private void retrieveItems(Context context) {
        if (mDatabase == null || mAppWidgetManager == null) {
            init(context);
        }
        new SoughtRetriever(mDatabase, this).execute();
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        init(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        retrieveItems(context);
        if (intent != null && intent.hasExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS)) {
            mWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Intent intent = new Intent(context, ListActivity.class);
        PendingIntent pending;
        for (int widgetId : appWidgetIds) {
            Intent serviceIntent = new Intent(context, WidgetService.class);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.content_widget);
            // https://stackoverflow.com/a/10700972
            pending = PendingIntent.getActivity(context, widgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.tv_list_name, pending);
            views.setRemoteAdapter(R.id.lv_list_items, serviceIntent);
            views.setEmptyView(R.id.lv_list_items, R.id.empty_display);

            appWidgetManager.updateAppWidget(widgetId, views);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onListLoaded(List<ListItem> list) {
        List<ListItemCombined> combined = ListItemCombiner.combineSimilar(list);
        WidgetFactory.refreshList(combined);
        if (mWidgetIds != null && mAppWidgetManager != null) {
            mAppWidgetManager.notifyAppWidgetViewDataChanged(
                    mWidgetIds,
                    R.id.lv_list_items
            );
        }
    }
}
