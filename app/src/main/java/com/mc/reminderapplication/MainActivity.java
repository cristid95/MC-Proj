package com.mc.reminderapplication;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton mAddReminderButton;
    private ArrayAdapter adapter;
    private ListView listView;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDbReference;
    private List<Reminder> reminders = new ArrayList<Reminder>();
    public static final String CHANNEL_ID = "4334";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize and set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);

        // Initialize add reminder button
        mAddReminderButton = findViewById(R.id.add_notification_btn);
        // Action fired on clicking the floating action button
        mAddReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ReminderAddActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        // Get Firebase reference
        mDatabase = FirebaseDatabase.getInstance();
        mDbReference = mDatabase.getReferenceFromUrl("https://reminderapplicationdb.firebaseio.com/reminderapplicationdb");
        // Attach a listener to read the data at our firebase reminders db
        mDbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Reminder rem = ds.getValue(Reminder.class);
                    Log.d("INFO in listenersinglevalueevent", "Notification details: title: " + rem.title + " description: " +
                            rem.description + " repeating: " + rem.repetition + " start date: " + rem.startDay + "/" + rem.startMonth + "/" +
                            rem.startYear + " start time: " + rem.startHour + ":" + rem.startMinute);
                    // create the notification for the reminder and bind it to the reminder
                    createNotification(rem);
                    // set the notification for being delivered to the user
                    scheduleNotification(rem);
                    // make sure the new reminder will be shown
                    reminders.add(rem);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        // Display user inserted text or nothing
        adapter = new ArrayAdapter<Reminder>(this, R.layout.activity_listview, reminders);
        listView = findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);

        // Set up notification environment
        createNotificationChannel();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 0) {
            // Make sure the request was successful
            if (resultCode == 0) {
                // fetch the notification
                Reminder rem = data.getExtras().getParcelable("reminder");
                Log.d("INFO in MainActivity", "Notification details: title: " + rem.title + " description: " +
                        rem.description + " repeating: " + rem.repetition + " start date: " + rem.startDay + "/" + rem.startMonth + "/" +
                        rem.startYear + " start time: " + rem.startHour + ":" + rem.startMinute);
                // create the notification for the reminder and bind it to the reminder
                createNotification(rem);
                // set the notification for being delivered to the user
                scheduleNotification(rem);
                // make sure the new reminder will be shown
                reminders.add(rem);
                adapter.notifyDataSetChanged();
                // write the reminder to the Firebase
                mDbReference.child(Integer.toString(rem.hashCode())).setValue(rem);
            }
        }
    }

    private void createNotification(Reminder reminder) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 1, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, MainActivity.CHANNEL_ID)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_light)
                .setContentTitle(reminder.title)
                .setShowWhen(false)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(reminder.description))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_CALL)
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                .setContentIntent(contentIntent);
        reminder.notification = builder.build();
    }

    private void scheduleNotification(Reminder reminder) {
        Intent notificationIntent = new Intent( this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, reminder.hashCode());
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, reminder.notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, reminder.hashCode(), notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, reminder.startYear);
        calendar.set(Calendar.MONTH, reminder.startMonth);
        calendar.set(Calendar.DAY_OF_MONTH, reminder.startDay);
        calendar.set(Calendar.HOUR_OF_DAY, reminder.startHour);
        calendar.set(Calendar.MINUTE, reminder.startMinute);
        Log.d("schedule", "year: " + reminder.startYear);
        Log.d("schedule", "month: " + reminder.startMonth);
        Log.d("schedule", "day: " + reminder.startDay);
        Log.d("schedule", "hour: " + reminder.startHour);
        Log.d("schedule", "minute: " + reminder.startMinute);
        Log.d("addNotification", "Setting up the alarm for generating notifications for reminder " + reminder.title);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        if (reminder.repetition == 0) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
        else {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), reminder.repetition * 1000, pendingIntent);
        }
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_MAX;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            try {
                notificationManager.createNotificationChannel(channel);
            } catch (NullPointerException e) {
                Log.w("Error", "Could not create channel for notifications");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
