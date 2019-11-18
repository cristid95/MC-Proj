package com.mc.reminderapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

public class NotificationPublisher extends BroadcastReceiver {
    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";

    public void onReceive(Context context, Intent intent) {
        int notificationId;

        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        notificationId = intent.getIntExtra(NOTIFICATION_ID, 0);
        Log.d(Long.toString(Calendar.getInstance().getTimeInMillis() / 1000), "NotificationPublisher onReceive start " + Integer.toString(notificationId));
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, notification);
        Log.d(Long.toString(Calendar.getInstance().getTimeInMillis() / 1000), "NotificationPublisher onReceive end " + Integer.toString(notificationId));
    }
}
