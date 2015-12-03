package com.diet.user.reminderdiet;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.diet.user.reminderdiet.lib.AlarmReceiverMakan;
import com.diet.user.reminderdiet.lib.SQLHelper;

import java.util.Calendar;

public class JamPersiapan extends AppCompatActivity {
    private TextView txtPerPagi, txtPerSiang, txtPerMalam;
    public Button btnPerPagi, btnSetPerSiang, btnSetPerMalam, btnSimpan;
    SQLHelper dbHelper;
    SQLiteDatabase dbRead, dbEdit;
    private int hour;
    private int minute;
    private PendingIntent pendingIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jam_persiapan);

        txtPerPagi = (TextView) findViewById(R.id.txtPerPagi);
        txtPerSiang = (TextView) findViewById(R.id.txtPerSiang);
        txtPerMalam = (TextView) findViewById(R.id.txtPerMalam);
        btnSimpan = (Button) findViewById(R.id.btnSimpanPer);
        dbHelper = new SQLHelper(JamPersiapan.this);
        dbRead = dbHelper.getReadableDatabase();
        dbEdit = dbHelper.getWritableDatabase();

        addButtonClickListener();
        setValue();
    }

    private void setValue() {

        Cursor dt;
        dt = dbRead.rawQuery("SELECT * FROM alarm limit 1", null);
        if (dt.moveToFirst()) {
            txtPerPagi.setText(dt.getString(dt.getColumnIndex("perpagi")));
            txtPerSiang.setText(dt.getString(dt.getColumnIndex("persiang")));
            txtPerMalam.setText(dt.getString(dt.getColumnIndex("permalam")));
        }
    }

    private void addButtonClickListener() {
        btnPerPagi = (Button) findViewById(R.id.btnPerPagi);
        btnPerPagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(1);
            }
        });
        btnSetPerSiang = (Button) findViewById(R.id.btnSetPerSiang);
        btnSetPerSiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(2);
            }
        });
        btnSetPerMalam = (Button) findViewById(R.id.btnSetPerMalam);
        btnSetPerMalam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(3);
            }
        });
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String perPagi = txtPerPagi.getText().toString();
                String perSiang = txtPerSiang.getText().toString();
                String perMalam = txtPerMalam.getText().toString();

                if (perPagi.equals("unset") || perSiang.equals("unset") || perMalam.equals("unset")) {
                    Toast.makeText(JamPersiapan.this, "Data Alarm Harus Di Set Waktunya!", Toast.LENGTH_SHORT).show();
                } else {

                    String query = "update alarm set perpagi='" + perPagi + "', persiang='" + perSiang + "', permalam='" + perMalam + "'";
                    dbEdit.execSQL(query);
                    String[] dt = {perPagi, perSiang, perMalam};
                    Integer x = 20;
                    for (Integer i = 0; i < dt.length; i++) {
                        String[] cek = dt[i].split(" ");
                        String[] jam = cek[0].split(":");
                        startAlarm(Integer.parseInt(jam[0]), Integer.parseInt(jam[1]), x);
                        x++;
                    }
                    Toast.makeText(
                            getApplicationContext(), "Data Berhasil Disimpan", Toast.LENGTH_SHORT)
                            .show();

                    // Log.d("tag",query);
                    finish();
                    Intent i = new Intent(getApplicationContext(), JamPersiapan.class);
                    startActivity(i);
                }
            }
        });
    }

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 1:
                return new TimePickerDialog(this, timePickerListener1, hour, minute,
                        false);
            case 2:
                return new TimePickerDialog(this, timePickerListener2, hour, minute,
                        false);
            case 3:
                return new TimePickerDialog(this, timePickerListener3, hour, minute,
                        false);
        }
        return null;
    }

    private TimePickerDialog.OnTimeSetListener timePickerListener1 = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
            // TODO Auto-generated method stub
            hour = hourOfDay;
            minute = minutes;
            updateTime(hour, minute, 1);

        }

    };
    private TimePickerDialog.OnTimeSetListener timePickerListener2 = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
            // TODO Auto-generated method stub
            hour = hourOfDay;
            minute = minutes;
            updateTime(hour, minute, 2);

        }

    };
    private TimePickerDialog.OnTimeSetListener timePickerListener3 = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
            // TODO Auto-generated method stub
            hour = hourOfDay;
            minute = minutes;
            updateTime(hour, minute, 3);

        }

    };

    private void updateTime(int hours, int mins, int cek) {

        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        // Append in a StringBuilder
        String aTime = new StringBuilder().append(hours).append(':')
                .append(minutes).toString();

        switch (cek) {
            case 1:
                txtPerPagi.setText(aTime);
                break;
            case 2:
                txtPerSiang.setText(aTime);
                break;
            case 3:
                txtPerMalam.setText(aTime);
                break;

        }


    }

    public void startAlarm(int hourOfDay, int minute, int count) {

        Intent alarmIntent = new Intent(getBaseContext(), AlarmReceiverMakan.class);
        pendingIntent = PendingIntent.getBroadcast(getBaseContext(), count, alarmIntent, 0);
        cancel(pendingIntent);
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Log.d("cek", "test" + hourOfDay + "-----" + minute);

        //manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000 * 60 * 60 * 24, pendingIntent);
    }

    public void cancel(PendingIntent pi) {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pi);
        //Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
    }
}
