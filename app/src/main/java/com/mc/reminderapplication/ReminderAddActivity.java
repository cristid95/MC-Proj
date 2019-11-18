package com.mc.reminderapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ReminderAddActivity extends AppCompatActivity {

    private Button mSaveReminderButton;
    private TextView mSetStartingDateAndTime;
    private EditText mInsertNotificationSubject;
    private EditText mInsertNotificationDescription;
    private EditText mInsertRepetitionInterval;
    private DatePicker mdatePicker;
    private TimePicker mtimePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
        Toolbar toolbar = findViewById(R.id.toolbar);

        // Initialize and set up toolbar
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.add_activity_title);

        // Initialize box that allows user to insert notification subject (title)
        mInsertNotificationSubject = findViewById(R.id.insert_notification_subject);
        // Initialize box that allows user to insert notification description (if any)
        mInsertNotificationDescription = findViewById(R.id.insert_notification_description);

        // Initialize save reminder button
        mSaveReminderButton = findViewById(R.id.save_reminder_btn);
        // On clicking the save reminder button
        mSaveReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mInsertNotificationSubject.getText().toString();
                String description = mInsertNotificationDescription.getText().toString();
                int minute = mtimePicker.getMinute();
                int hour = mtimePicker.getHour();
                int day = mdatePicker.getDayOfMonth();
                int month = mdatePicker.getMonth();
                int year = mdatePicker.getYear();
                String repetition = mInsertRepetitionInterval.getText().toString();
                Log.d("INFO", "Notification details: title: " + title + " description: " +
                        description + " repeating: " + repetition + " start date: " + day + "/" + month + "/" +
                        year + " start time: " + hour + ":" + minute);
                Intent result = new Intent();
                if (title.length() > 0) {
                    Reminder rem = new Reminder(title, description, year, month, day, hour, minute, repetition);
                    Log.d("INFO", "Notification details: title: " + rem.title + " description: " +
                            rem.description + " repeating: " + rem.repetition + " start date: " + rem.startDay + "/" + rem.startMonth + "/" +
                            rem.startYear + " start time: " + rem.startHour + ":" + rem.startMinute);
                    result.putExtra("reminder", rem);
                    setResult(0, result);
                } else {
                    // TODO print some message to the user
                    setResult(1, result);
                }
                finish();
            }
        });

        mSetStartingDateAndTime = (TextView)findViewById(R.id.set_date_time);
        // Starting date for notification (default: today)
        mdatePicker = (DatePicker)findViewById(R.id.datepicker);
        // Starting hour and minute for notification
        mtimePicker = (TimePicker)findViewById(R.id.timepicker);
        // Repeating interval
        mInsertRepetitionInterval = findViewById(R.id.insert_notification_repetition);
    }
}
