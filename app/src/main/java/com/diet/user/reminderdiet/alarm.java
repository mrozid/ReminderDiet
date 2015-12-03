package com.diet.user.reminderdiet;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.diet.user.reminderdiet.lib.AlarmReceiverMakan;

import java.util.Calendar;

public class alarm extends AppCompatActivity {

    Button SetAlarm, btn_Cancel;
    TextView textAlarm;
    TimePickerDialog timePickerDialog;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        textAlarm = (TextView) findViewById(R.id.txt_alarm);

        SetAlarm = (Button) findViewById(R.id.btn_setAlarm);
        SetAlarm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                textAlarm.setText("");
                openTimePickerDialog(false);

            }

        });
        btn_Cancel = (Button) findViewById(R.id.btn_Cancel);
        btn_Cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                textAlarm.setText("");
                openTimePickerDialog(false);

            }

        });
    }

    private void openTimePickerDialog(boolean is24r) {
        Calendar calendar = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog(alarm.this, onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.setTitle("Set Waktu Alarm");
        timePickerDialog.show();
    }

    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            //penin();
            startAt10(hourOfDay,minute);
            //SetAlarm(hourOfDay, minute);
        }

    };

    private void SetAlarm(int hourOfDay, int minute) {

        Calendar calNow = Calendar.getInstance();
        Calendar calSet = (Calendar) calNow.clone();
        calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calSet.set(Calendar.MINUTE, minute);
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);
     /*   if (calSet.compareTo(calNow) <= 0) {
            calSet.add(Calendar.DATE, 1);
        }*/
        int req = 1;
        textAlarm.setText("Set Alarm :" + calSet.getTime());
        Intent intent = new Intent(getBaseContext(), AlarmReceiverMakan.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), req, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), pendingIntent);

        calSet = (Calendar) calNow.clone();
        calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calSet.set(Calendar.MINUTE, minute + 1);
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);

        pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 2, intent, 0);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), pendingIntent);

        calSet = (Calendar) calNow.clone();
        calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calSet.set(Calendar.MINUTE, minute + 2);
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);

        pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 3, intent, 0);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), pendingIntent);

    }


    public void start() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 8000;
        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
    }

    public void cancel() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
    }

    public void startAt10(int hourOfDay, int minute) {
               /* Retrieve a PendingIntent that will perform a broadcast */
        Intent alarmIntent = new Intent(getBaseContext(), AlarmReceiverMakan.class);
        pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 0, alarmIntent, 0);

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        /* Set the alarm to start at 10:30 AM */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Log.d("cek", "test" + hourOfDay + "-----" + minute);
        /* Repeating on every 20 minutes interval */
        manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
       /* manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000 * 60 * 60 * 24, pendingIntent);*/
    }

    void penin() {
        Log.d("cek", "Start in");

    }

}
