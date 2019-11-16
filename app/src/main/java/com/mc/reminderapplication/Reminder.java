package com.mc.reminderapplication;

import java.io.Serializable;

public class Reminder implements Serializable {
    public String title;
    public String description;

    public Reminder() {
        this.title = "Empty title";
        this.description = "Empty description";
    }

    public Reminder(String title, String description) {
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Reminder [title=" + this.title + ", description=" + this.description + "]";
    }
}
