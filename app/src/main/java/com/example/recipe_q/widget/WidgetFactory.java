package com.example.recipe_q.widget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.recipe_q.R;
import com.example.recipe_q.model.ListItemCombined;

import java.util.ArrayList;
import java.util.List;

public class WidgetFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private static List<ListItemCombined> mListItems = new ArrayList<>();

    public WidgetFactory(Context context) {
        mContext = context;
    }

    static void refreshList(List<ListItemCombined> newList) {
        if (newList != null) {
            mListItems = newList;
        }
    }

    @Override
    public void onCreate() {}

    @Override
    public void onDataSetChanged() {}

    @Override
    public void onDestroy() {}

    @Override
    public int getCount() {
        return mListItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        ListItemCombined current = mListItems.get(position);
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.item_widget);
        views.setTextViewText(R.id.tv_item_name, current.getName());
        views.setTextViewText(R.id.tv_item_amount, current.getQuantity());
        views.setTextViewText(R.id.tv_item_unit, current.getUnit());
        return views;
    }

    @Override
    public RemoteViews getLoadingView() { return null; }

    @Override
    public int getViewTypeCount() { return 1; }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public boolean hasStableIds() { return true; }
}
