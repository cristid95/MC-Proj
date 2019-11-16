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









    /*private String title = null;
    private String description = null;
    private int notificationId;

    public NotificationService(String name) { super(name); }
    public NotificationService() { super("Name undefined"); }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d("NotificationService", "Start: onHandleIntent");

        if (title == null) {
            title = intent.getStringExtra("title");
            if (title == null) System.out.println("title is null");
            description = intent.getStringExtra("description");
            if (description == null) System.out.println("description is null");
            notificationId = sumOfCharacters(title) + sumOfCharacters(description);
        }
        Calendar c = Calendar.getInstance();
        long time = c.getTimeInMillis() / 1000;
        Log.d(time + " NotificationService", "Title: " + title);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, MainActivity.CHANNEL_ID)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_light)
                .setContentTitle(title)
                .setContentText(description)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Much longer text that cannot fit one line..."))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, notificationId, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(notificationId, builder.build());

        Log.d("NotificationService", "end: onHandleIntent");
    }

    private int sumOfCharacters(String s) {
        int i, sum = 0;

        for (i = 0; i < s.length(); ++i) {
            sum += s.charAt(i);
        }

        return sum;
    }*/
}
