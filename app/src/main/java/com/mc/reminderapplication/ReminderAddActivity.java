package com.mc.reminderapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReminderAddActivity extends AppCompatActivity {

    private Button mSaveReminderButton;
    private EditText mInsertNotificationSubject;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
        Toolbar toolbar = findViewById(R.id.toolbar);

        // Initialize and set up toolbar
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.add_activity_title);

        // Initialize save reminder button
        mSaveReminderButton = findViewById(R.id.save_reminder_btn);
        // On clicking the save reminder button
        mSaveReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = mInsertNotificationSubject.getText().toString();
                if (item.length() > 0) {
                   Reminder rem = new Reminder(item, "Random description");
                   String remId = Integer.toString(rem.hashCode());
                   mDbReference.child("reminderapplicationdb").child(remId).setValue(rem);
                }
                finish();
            }
        });

        // Initialize box that allows user to insert text
        mInsertNotificationSubject = findViewById(R.id.insert_notification_subject);

        // Get Firebase DB reference (activity will write to it)
        mDatabase = FirebaseDatabase.getInstance();
        mDbReference = mDatabase.getReferenceFromUrl("https://reminderapplicationdb.firebaseio.com");
    }
}
