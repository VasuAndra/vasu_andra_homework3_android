package com.example.android_tema3_vasu_andra;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

// ****
// https://github.com/google-developer-training/android-fundamentals-apps-v2/blob/master/StandUp/app/src/main/java/com/android/fundamentals/standup/AlarmReceiver.java
// ****

public class AlarmReceiver extends BroadcastReceiver {

    private NotificationManager notificationManager;
    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    @Override
    public void onReceive(Context context, Intent intent) {
        notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        deliverNotification(context);
    }




    private void deliverNotification(Context context) {

        Intent contentIntent = new Intent(context, MainActivity.class);

        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (context, NOTIFICATION_ID, contentIntent, PendingIntent
                        .FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder
                (context, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_heart)
                .setContentTitle("Alarm notification")
                .setContentText("You have an alarm!")
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}

