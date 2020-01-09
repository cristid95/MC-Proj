package com.mc.reminderapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class LoginActivity extends AppCompatActivity {

    private Button mLoginButton;
    private EditText mInsertEmail;
    private EditText mInsertPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);

        // Initialize and set up toolbar
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.login_activity_title);

        // Initialize box that allows user to insert email
        mInsertEmail = findViewById(R.id.insert_email);
        // Initialize box that allows user to insert password
        mInsertPassword = findViewById(R.id.insert_password);

        // Initialize login button
        mLoginButton = findViewById(R.id.login_btn);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // for the moment, perform no validation on user/password
                String email = mInsertEmail.getText().toString();
                String password = mInsertPassword.getText().toString();
                if (!email.isEmpty() && !password.isEmpty()) {
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    intent.putExtra("email", email);
                    intent.putExtra("password", password);
                    startActivity(intent);
                }
                mInsertEmail.setText("");
                mInsertPassword.setText("");
            }
        });
    }
}
