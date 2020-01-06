package com.example.recipe_q.service;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.example.recipe_q.db.RecipeDatabase;
import com.example.recipe_q.model.Recipe;

import java.util.Calendar;
import java.util.List;

import static com.example.recipe_q.model.RecipeManager.RECIPE_CACHE_TIME_LIMIT_MILLIS;

// https://developer.android.com/reference/androidx/core/app/JobIntentService
public class CleanupService extends JobIntentService {
    public static final String ACTION_REMOVE_EXPIRED_IMMEDIATELY = "com.example.recipe_q.service.cleanup_now";
    public static final String ACTION_REMOVE_EXPIRED_SCHEDULED = "com.example.recipe_q.service.cleanup_planned";
    public static final String EXTRA_TIMESTAMP_CUTOFF = "com.example.recipe_q.service.timestamp_cutoff";

    private static final int JOB_ID = 12321;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, CleanupService.class, JOB_ID, work);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        String action = intent.getAction();
        if (action != null) {
            if (ACTION_REMOVE_EXPIRED_IMMEDIATELY.equals(action)) {
                removeExpiredNow();
            } else if (ACTION_REMOVE_EXPIRED_SCHEDULED.equals(action)) {
                long now = Calendar.getInstance().getTimeInMillis();
                long when = intent.getLongExtra(EXTRA_TIMESTAMP_CUTOFF, now);
                if (when > now) {
                    scheduleFutureRemoval(when);
                } else {
                    removeExpiredNow();
                }
            }
        }
    }

    private void removeExpiredNow() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Application application = getApplication();
                RecipeDatabase database = RecipeDatabase.getInstance(application);
                database.dao().removeExpired(
                        Calendar.getInstance().getTimeInMillis() - RECIPE_CACHE_TIME_LIMIT_MILLIS
                );
                List<Recipe> remaining = database.dao().loadRemainingRecipesImmediate();
                if (remaining.size() > 0) {
                    scheduleFutureRemoval(remaining.get(0).getRetrievalTime() + RECIPE_CACHE_TIME_LIMIT_MILLIS);
                }
            }
        }).start();
    }

    private void scheduleFutureRemoval(long targetTimestamp) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                removeExpiredNow();
            }
        }, targetTimestamp - Calendar.getInstance().getTimeInMillis());
    }
}
