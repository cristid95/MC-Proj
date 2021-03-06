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
                Intent result = new Intent();
                if (title.length() > 0) {
                    String description = mInsertNotificationDescription.getText().toString();
                    int minute = mtimePicker.getMinute();
                    int hour = mtimePicker.getHour();
                    int day = mdatePicker.getDayOfMonth();
                    int month = mdatePicker.getMonth();
                    int year = mdatePicker.getYear();
                    String repetition = mInsertRepetitionInterval.getText().toString();
                    Reminder rem = new Reminder(title, description, year, month, day, hour, minute, repetition);
                    Log.d("INFO", "Add Notification: title: " + rem.title +
                            " description: " + rem.description +
                            " repeating: " + rem.repetition +
                            " start date: " + rem.startDay + "/" + rem.startMonth + "/" + rem.startYear +
                            " start time: " + rem.startHour + ":" + rem.startMinute);
                    result.putExtra("reminder", rem);
                    setResult(2, result);
                } else {
                    setResult(3, result);
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
