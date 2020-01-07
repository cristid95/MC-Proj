package com.mc.reminderapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class NotificationPublisher extends BroadcastReceiver {
    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";

    public void onReceive(Context context, Intent intent) {
        int notificationId;

        Notification originalNotification = intent.getParcelableExtra(NOTIFICATION);
        Notification notification = originalNotification.clone();
        notification.when = Calendar.getInstance().getTimeInMillis();
        notificationId = intent.getIntExtra(NOTIFICATION_ID, 0);
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, notification);
    }
}
