package com.mc.reminderapplication;

import android.app.Notification;

public class Reminder {
    public String title;
    public String description;
    Notification notification;

    public Reminder() {
        this.title = "";
        this.description = "";
        this.notification = null;
    }

    public Reminder(String title, String description) {
        this.title = title;
        this.description = description;
        this.notification = null;
    }

    @Override
    public String toString() {
        return this.title;
    }
}
