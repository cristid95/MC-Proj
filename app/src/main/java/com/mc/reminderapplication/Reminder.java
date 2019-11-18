package com.mc.reminderapplication;

import android.app.Notification;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reminder implements Parcelable {
    public String title;
    public String description;
    public int startYear;
    public int startMonth;
    public int startDay;
    public int startHour;
    public int startMinute;
    public long repetition; // in seconds
    Notification notification;

    public Reminder() {
        this.title = "";
        this.description = "";
        this.startYear = 0;
        this.startMonth = 0;
        this.startDay = 0;
        this.startHour = 0;
        this.startMinute = 0;
        this.repetition = 0;
        this.notification = null;
    }

    public Reminder(String title, String description, int year, int month, int  day,
                    int hour, int minute, String repetition) {
        this.title = title;
        this.description = description;
        this.startYear = year;
        this.startMonth = month;
        this.startDay = day;
        this.startHour = hour;
        this.startMinute = minute;
        this.repetition = parse(repetition);
        this.notification = null;
    }

    public Reminder(String title, String description, int year, int month, int  day,
                    int hour, int minute, long repetition) {
        this.title = title;
        this.description = description;
        this.startYear = year;
        this.startMonth = month;
        this.startDay = day;
        this.startHour = hour;
        this.startMinute = minute;
        this.repetition = repetition;
        this.notification = null;
    }

    protected Reminder(Parcel in) {
        this.title = in.readString();
        this.description = in.readString();
        this.startYear = in.readInt();
        this.startMonth = in.readInt();
        this.startDay = in.readInt();
        this.startHour = in.readInt();
        this.startMinute = in.readInt();
        this.repetition = in.readLong();
    }

    public static final Creator<Reminder> CREATOR = new Creator<Reminder>() {
        @Override
        public Reminder createFromParcel(Parcel in) {
            return new Reminder(in);
        }

        @Override
        public Reminder[] newArray(int size) {
            return new Reminder[size];
        }
    };

    public long parse(String repetition) {
        // Repetition follows the format described in @string/notification_repetition_expl
        long repetitionSeconds = 0;
        int years = 0, months = 0, days = 0, hours = 0, minutes = 0, seconds = 0;
        String aux;
        String regex = "(\\d+(Y|y))?(\\d+M)?(\\d+(D|d))?(\\d+(H|h))?(\\d+m)?(\\d+(S|s))?";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(repetition);

        if (!m.find()) {
            return repetitionSeconds;
        }

        // 10 groups are returned
        // group 1 contains the number of years (if selected); format: xy or xY
        if (m.group(1) != null) {
            aux = m.group(1);
            years = Integer.parseInt(aux.substring(0, aux.length() - 1));
        }
        // group 3 contains the number of months (if selected); format: xM
        if (m.group(3) != null) {
            aux = m.group(3);
            months = Integer.parseInt(aux.substring(0, aux.length() - 1));
        }
        // group 4 contains the number of days (if selected); format: xd or xD
        if (m.group(4) != null) {
            aux = m.group(4);
            days = Integer.parseInt(aux.substring(0, aux.length() - 1));
        }
        // group 6 contains the number of hours (if selected); format: xh or xH
        if (m.group(6) != null) {
            aux = m.group(6);
            hours = Integer.parseInt(aux.substring(0, aux.length() - 1));
        }
        // group 8 contains the number of minutes (if selected); format: xm
        if (m.group(8) != null) {
            aux = m.group(8);
            minutes = Integer.parseInt(aux.substring(0, aux.length() - 1));
        }
        // group 9 contains the number of seconds (if selected); format: xs or xS
        if (m.group(9) != null) {
            aux = m.group(9);
            seconds = Integer.parseInt(aux.substring(0, aux.length() - 1));
        }

        Log.d("INFO", years + " years");
        Log.d("INFO", months + " months");
        Log.d("INFO", days + " days");
        Log.d("INFO", hours + " hours");
        Log.d("INFO", minutes + " minutes");
        Log.d("INFO", seconds + " seconds");

        repetitionSeconds += years * 31556926; // an average for the number of seconds in a year
        repetitionSeconds += months * 2629743; // an average for the number of seconds in a month
        repetitionSeconds += days * 86400;
        repetitionSeconds += hours * 3600;
        repetitionSeconds += minutes * 60;
        repetitionSeconds += seconds;

        return repetitionSeconds;
    }

    @Override
    public String toString() {
        return this.title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.title);
        out.writeString(this.description);
        out.writeInt(this.startYear);
        out.writeInt(this.startMonth);
        out.writeInt(this.startDay);
        out.writeInt(this.startHour);
        out.writeInt(this.startMinute);
        out.writeLong(this.repetition);
    }
}
