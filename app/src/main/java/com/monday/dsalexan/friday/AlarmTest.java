package com.monday.dsalexan.friday;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Time;
import java.util.Calendar;

public class AlarmTest extends AppCompatActivity {

    AlarmManager alarm_manager;
    Calendar calendar;
    Intent intent;
    PendingIntent pending_intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_test);

        this.alarm_manager = (AlarmManager)getSystemService(ALARM_SERVICE);
        calendar = Calendar.getInstance();

        intent = new Intent(this, AlarmReceiver.class);
    }

    public void turnAlarmOn(View view){
        Log.d("friday", "turning alarm on");

        TextView alarmStatus = (TextView)findViewById(R.id.text_status);
        alarmStatus.setText("on");

        TimePicker alarmTime = (TimePicker)findViewById(R.id.time_alarm);
        calendar.set(Calendar.HOUR_OF_DAY, alarmTime.getHour());
        calendar.set(Calendar.MINUTE, alarmTime.getMinute());
        calendar.set(Calendar.SECOND, 9);

        pending_intent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarm_manager.set(AlarmManager.RTC_WAKEUP,  calendar.getTimeInMillis(), pending_intent);
    }

    public void turnAlarmOff(View view){
        TextView alarmStatus = (TextView)findViewById(R.id.text_status);
        alarmStatus.setText("off");

        alarm_manager.cancel(pending_intent);
    }
}
