package com.diet.user.reminderdiet;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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

public class JamMakan extends AppCompatActivity {
    private TextView txtPagi, txtSelingan, txtSiang, txtOlahraga, txtMalam;
    public Button btnPagi, btnSelingan, btnSetSiang, btnSetOlahraga, btnSetMalam, btnSimpan;
    SQLHelper dbHelper;
    SQLiteDatabase dbRead, dbEdit;
    private int hour;
    private int minute;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jam_makan);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        txtPagi = (TextView) findViewById(R.id.txtPagi);
        txtSelingan = (TextView) findViewById(R.id.txtSelingan);
        txtSiang = (TextView) findViewById(R.id.txtSiang);
        txtOlahraga = (TextView) findViewById(R.id.txtOlahraga);
        txtMalam = (TextView) findViewById(R.id.txtMalam);
        dbHelper = new SQLHelper(JamMakan.this);
        dbEdit = dbHelper.getWritableDatabase();
        dbRead = dbHelper.getReadableDatabase();

        addButtonClickListener();
        setValue();
    }

    private void setValue() {

        Cursor dt;
        dt = dbRead.rawQuery("SELECT * FROM alarm limit 1", null);
        if (dt.moveToFirst()) {
            txtPagi.setText(dt.getString(dt.getColumnIndex("pagi")));
            txtSelingan.setText(dt.getString(dt.getColumnIndex("selingan")));
            txtSiang.setText(dt.getString(dt.getColumnIndex("siang")));
            txtOlahraga.setText(dt.getString(dt.getColumnIndex("olahraga")));
            txtMalam.setText(dt.getString(dt.getColumnIndex("malam")));
        }
    }

    public void addButtonClickListener() {

        btnPagi = (Button) findViewById(R.id.btnPagi);
        btnPagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(1);
            }
        });
        btnSelingan = (Button) findViewById(R.id.btnSetSelingan);
        btnSelingan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(2);
            }
        });
        btnSetSiang = (Button) findViewById(R.id.btnSetSiang);
        btnSetSiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(3);
            }
        });
        btnSetOlahraga = (Button) findViewById(R.id.btnSetOlahraga);
        btnSetOlahraga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(4);
            }
        });
        btnSetMalam = (Button) findViewById(R.id.btnSetMalam);
        btnSetMalam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(5);
            }
        });
        btnSimpan = (Button) findViewById(R.id.btnSimpan);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pagi = txtPagi.getText().toString();
                String selingan = txtSelingan.getText().toString();
                String siang = txtSiang.getText().toString();
                String olahraga = txtOlahraga.getText().toString();
                String malam = txtMalam.getText().toString();

                if (pagi.equals("unset") || selingan.equals("unset") || siang.equals("unset") || olahraga.equals("unset") || malam.equals("unset")) {
                    Toast.makeText(JamMakan.this, "Data Alarm Harus Di Set Waktunya!", Toast.LENGTH_SHORT).show();
                } else {

                    String query = "update alarm set pagi='" + pagi + "', selingan='" + selingan + "', siang='" + siang + "',olahraga='" + olahraga + "',malam='" + malam + "'";
                    dbEdit.execSQL(query);
                    String[] dt = {pagi, selingan, siang, olahraga, malam};
                    Integer x = 10;
                    for (Integer i = 0; i < dt.length; i++) {
                        String[] cek = dt[i].split(" ");
                        String[] jam = cek[0].split(":");

                        startAlarm(Integer.parseInt(jam[0]), Integer.parseInt(jam[1]), x);

                        x++;
                    }

                    Toast.makeText(
                            getApplicationContext(), "Data Berhasil Disimpan", Toast.LENGTH_SHORT)
                            .show();

                    Intent i = new Intent(getApplicationContext(), JamPersiapan.class);
                    startActivity(i);
                }

            }
        });
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
            case 4:
                return new TimePickerDialog(this, timePickerListener4, hour, minute,
                        false);
            case 5:
                return new TimePickerDialog(this, timePickerListener5, hour, minute,
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
    private TimePickerDialog.OnTimeSetListener timePickerListener4 = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
            // TODO Auto-generated method stub
            hour = hourOfDay;
            minute = minutes;
            updateTime(hour, minute, 4);

        }

    };
    private TimePickerDialog.OnTimeSetListener timePickerListener5 = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
            // TODO Auto-generated method stub
            hour = hourOfDay;
            minute = minutes;
            updateTime(hour, minute, 5);

        }

    };


    private void updateTime(int hours, int mins, int cek) {

        /*String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";*/


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
                txtPagi.setText(aTime);
                break;
            case 2:
                txtSelingan.setText(aTime);
                break;
            case 3:
                txtSiang.setText(aTime);
                break;
            case 4:
                txtOlahraga.setText(aTime);
                break;
            case 5:
                txtMalam.setText(aTime);
                break;
        }


    }

    public void cancel(PendingIntent pi) {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pi);
        //Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
    }

/*    void cek(){
        if (chk_monday.isChecked()) {
            forday(2);
        } else if (chk_tuesday.isChecked()) {
            forday(3);
        } else if (chk_wednesday.isChecked()) {
            forday(4);
        } else if (chk_thursday.isChecked()) {
            forday(5);
        } else if (chk_friday.isChecked()) {
            forday(6);
        } else if (chk_sat.isChecked()) {
            forday(7);
        } else if (chk_sunday.isChecked()) {
            forday(1);
        }

    }

    public void forday(int week) {

        calSet.set(Calendar.DAY_OF_WEEK, week);
        calSet.set(Calendar.HOUR_OF_DAY, hour);
        calSet.set(Calendar.MINUTE, minuts);
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                calSet.getTimeInMillis(), 1 * 60 * 60 * 1000, pendingIntent);
    }*/
}
