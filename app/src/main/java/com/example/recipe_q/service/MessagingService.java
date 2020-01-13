package com.example.recipe_q.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.recipe_q.R;
import com.example.recipe_q.activity.RecipeActivity;
import com.example.recipe_q.model.FavoriteRecipe;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import static com.example.recipe_q.activity.RecipeActivity.RECIPE_PARCELABLE;

public class MessagingService extends FirebaseMessagingService {
    private static final String TAG = MessagingService.class.getSimpleName();

    public static final String FIREBASE_LATEST_TOKEN = "fmc_token";
    public static final String FIREBASE_RECEIVED_RECIPE_ID = "reference";
    public static final String FIREBASE_RECEIVED_RECIPE_NAME = "title";
    public static final String FIREBASE_RECEIVED_RECIPE_IMAGE = "image";

    @Override
    public void onNewToken(@NonNull String s) {
        Log.v(TAG, "onNewToken: \"" + s + "\"");
        super.onNewToken(s);
        saveToken(s);
    }

    // https://firebase.google.com/docs/cloud-messaging/android/receive?authuser=0
    // "If you want foregrounded apps to receive notification messages or data messages, you’ll need
    // to write code to handle the onMessageReceived callback."
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        Log.v(TAG, "onMessageReceived");
        super.onMessageReceived(remoteMessage);
        FavoriteRecipe receivedRecipe = parseRecipe(remoteMessage.getData());
        sendNotification(remoteMessage.getNotification(), receivedRecipe);
    }

    private FavoriteRecipe parseRecipe(Map<String, String> data) {
        Log.v(TAG, "parseRecipe");
        FavoriteRecipe recipe = null;
        if (data.size() > 0) {
            // When we receive valid information for creating a favorite-type recipe, store it
            String reference = data.get(FIREBASE_RECEIVED_RECIPE_ID);
            String title = data.get(FIREBASE_RECEIVED_RECIPE_NAME);
            String image = data.get(FIREBASE_RECEIVED_RECIPE_IMAGE);
            if (reference != null && !reference.isEmpty() &&
                    title != null && !title.isEmpty() &&
                    image != null && !image.isEmpty()
            ) {
                long id = Long.parseLong(reference);
                recipe = new FavoriteRecipe(id, title, image);
                saveRecipe(recipe);
            }
        }
        return recipe;
    }

    private void saveToken(String newToken) {
        Log.v(TAG, "saveToken");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString(FIREBASE_LATEST_TOKEN, newToken);
        edit.apply();
    }

    private void saveRecipe(FavoriteRecipe recipe) {
        Log.v(TAG, "saveRecipe");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putLong(FIREBASE_RECEIVED_RECIPE_ID, recipe.getIdSpoonacular());
        edit.putString(FIREBASE_RECEIVED_RECIPE_NAME, recipe.getRecipeTitle());
        edit.putString(FIREBASE_RECEIVED_RECIPE_IMAGE, recipe.getImage());
        edit.apply();
    }

    // https://github.com/firebase/quickstart-android/blob/master/messaging/app/src/main/java/com/google/firebase/quickstart/fcm/java/MyFirebaseMessagingService.java
    private void sendNotification(RemoteMessage.Notification notify, FavoriteRecipe recipe) {
        Log.v(TAG, "sendNotification");
        PendingIntent pending = null;
        if (recipe != null) {
            Intent intent = new Intent(this, RecipeActivity.class);
            intent.putExtra(RECIPE_PARCELABLE, recipe);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            pending = PendingIntent.getActivity(
                    this,
                    0,
                    intent,
                    PendingIntent.FLAG_ONE_SHOT
            );
        }

        String channelId = getString(R.string.notify_firebase_channel_id);
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(
                this,
                channelId
        )
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
        ;

        notifyBuilder.setContentTitle(notify != null ? notify.getTitle() :
                getString(R.string.notify_firebase_recipe_received_title));
        notifyBuilder.setContentText(notify != null ? notify.getBody() :
                recipe != null ? recipe.getRecipeTitle() :
                        getString(R.string.notify_firebase_recipe_received_body)
        );

        if (pending != null) {
            notifyBuilder.setContentIntent(pending);
        }

        NotificationManager notifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notifyChannel = new NotificationChannel(
                    channelId,
                    "Post-reception of Firebase message",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notifyManager.createNotificationChannel(notifyChannel);
        }

        notifyManager.notify(0, notifyBuilder.build());
    }
}
