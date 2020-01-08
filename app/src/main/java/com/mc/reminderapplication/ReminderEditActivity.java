package com.mc.reminderapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class ReminderEditActivity extends AppCompatActivity {

    private Button mSaveReminderButton;
    private Button mDeleteReminderButton;
    private TextView mSetStartingDateAndTime;
    private EditText mInsertNotificationSubject;
    private EditText mInsertNotificationDescription;
    private EditText mInsertRepetitionInterval;
    private DatePicker mdatePicker;
    private TimePicker mtimePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reminder);
        Toolbar toolbar = findViewById(R.id.toolbar);

        // Initialize and set up toolbar
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.edit_activity_title);

        // Get the reminder the user clicked on
        final Reminder rem = getIntent().getExtras().getParcelable("reminder");
        Log.d("INFO", "Editing reminder with id " + rem.id + " and title " + rem.title);

        // Initialize box that allows user to insert notification subject (title)
        mInsertNotificationSubject = findViewById(R.id.edit_notification_subject);
        mInsertNotificationSubject.setText(rem.title, TextView.BufferType.EDITABLE);
        // Initialize box that allows user to insert notification description (if any)
        mInsertNotificationDescription = findViewById(R.id.edit_notification_description);
        mInsertNotificationDescription.setText(rem.description, TextView.BufferType.EDITABLE);

        // Initialize save changes made to reminder button
        mSaveReminderButton = findViewById(R.id.save_reminder_changes_btn);
        // On clicking the save reminder button
        mSaveReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mInsertNotificationSubject.getText().toString();
                Intent result = new Intent();
                if (title.length() > 0) {
                    rem.title = title;
                    rem.description = mInsertNotificationDescription.getText().toString();
                    rem.startMinute = mtimePicker.getMinute();
                    rem.startHour = mtimePicker.getHour();
                    rem.startDay = mdatePicker.getDayOfMonth();
                    rem.startMonth = mdatePicker.getMonth();
                    rem.startYear = mdatePicker.getYear();
                    rem.repetition = rem.parse(mInsertRepetitionInterval.getText().toString());
                    result.putExtra("reminder", rem);
                    setResult(2, result);
                    Log.d("INFO",
                            "Edit Notification: title: " + rem.title +
                                    " description: " + rem.description +
                                    " repeating: " + rem.repetition +
                                    " start date: " + rem.startDay + "/" + rem.startMonth + "/" + rem.startYear +
                                    " start time: " + rem.startHour + ":" + rem.startMinute);
                } else {
                    setResult(4, result);
                }
                finish();
            }
        });

        // Initialize delete reminder button
        mDeleteReminderButton = findViewById(R.id.delete_reminder_btn);
        // On clicking the delete reminder button
        mDeleteReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Confirm deletion")
                        .setMessage("Are you sure you want to proceed?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent result = new Intent();
                                result.putExtra("reminder", rem);
                                setResult(3, result);
                                finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        mSetStartingDateAndTime = (TextView)findViewById(R.id.edit_date_time);
        // Starting date for notification (default: what is already configured)
        mdatePicker = (DatePicker)findViewById(R.id.edit_datepicker);
        mdatePicker.updateDate(rem.startYear, rem.startMonth, rem.startDay);
        // Starting hour and minute for notification (default: what is already configured)
        mtimePicker = (TimePicker)findViewById(R.id.edit_timepicker);
        mtimePicker.setHour(rem.startHour);
        mtimePicker.setMinute(rem.startMinute);
        // Repeating interval
        mInsertRepetitionInterval = findViewById(R.id.edit_notification_repetition);
        String repetition = Long.toString(rem.repetition) + "s";
        mInsertRepetitionInterval.setText(repetition, TextView.BufferType.EDITABLE);
    }
}
