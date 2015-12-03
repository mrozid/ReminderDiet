package com.diet.user.reminderdiet;

import android.app.AlarmManager;
import android.app.PendingIntent;
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
import android.widget.Toast;

import com.diet.user.reminderdiet.lib.AlarmReceiverPersiapan;
import com.diet.user.reminderdiet.lib.SQLHelper;

import java.util.Calendar;

public class AlarmPersiapan extends AppCompatActivity {
    private TextView txtPerPagi, txtPerSiang, txtPerMalam;
    public Button btnSimpan;
    SQLHelper dbHelper;
    SQLiteDatabase dbRead, dbEdit;
    private int hour;
    private int minute;
    private PendingIntent pendingIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_persiapan);
        txtPerPagi = (TextView) findViewById(R.id.txtPerPagi);
        txtPerSiang = (TextView) findViewById(R.id.txtPerSiang);
        txtPerMalam = (TextView) findViewById(R.id.txtPerMalam);
        btnSimpan = (Button) findViewById(R.id.btnSimpanPer);
        dbHelper = new SQLHelper(AlarmPersiapan.this);
        dbRead = dbHelper.getReadableDatabase();
        dbEdit = dbHelper.getWritableDatabase();
        setValue();
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String perPagi = txtPerPagi.getText().toString();
                String perSiang = txtPerSiang.getText().toString();
                String perMalam = txtPerMalam.getText().toString();

                String[] dt = {perPagi, perSiang, perMalam};
                Integer x = 40;
                Cursor data;
                data = dbRead.rawQuery("SELECT * from day_alarm", null);
                if (data.moveToFirst()) {
                    if (data.getString(data.getColumnIndex("senin")).equals("1")) {
                        for (Integer i = 0; i < dt.length; i++) {
                            String[] jam = dt[i].split(":");
                            Log.d("test", cekWaktu(Integer.parseInt(jam[0]), Integer.parseInt(jam[1]), x, 2));
                            //startAlarm(Integer.parseInt(jam[0]), Integer.parseInt(jam[1]), x);
                            x++;
                        }
                    }
                    if (data.getString(data.getColumnIndex("selasa")).equals("1")) {
                        for (Integer i = 0; i < dt.length; i++) {
                            String[] jam = dt[i].split(":");
                            Log.d("test", cekWaktu(Integer.parseInt(jam[0]), Integer.parseInt(jam[1]), x, 3));
                            //startAlarm(Integer.parseInt(jam[0]), Integer.parseInt(jam[1]), x);
                            x++;
                        }
                    }
                    if (data.getString(data.getColumnIndex("rabu")).equals("1")) {
                        for (Integer i = 0; i < dt.length; i++) {
                            String[] jam = dt[i].split(":");
                            Log.d("test", cekWaktu(Integer.parseInt(jam[0]), Integer.parseInt(jam[1]), x, 4));
                            //startAlarm(Integer.parseInt(jam[0]), Integer.parseInt(jam[1]), x);
                            x++;
                        }
                    }
                    if (data.getString(data.getColumnIndex("kamis")).equals("1")) {
                        for (Integer i = 0; i < dt.length; i++) {
                            String[] jam = dt[i].split(":");
                            Log.d("test", cekWaktu(Integer.parseInt(jam[0]), Integer.parseInt(jam[1]), x, 5));
                            //startAlarm(Integer.parseInt(jam[0]), Integer.parseInt(jam[1]), x);
                            x++;
                        }
                    }
                    if (data.getString(data.getColumnIndex("jumat")).equals("1")) {
                        for (Integer i = 0; i < dt.length; i++) {
                            String[] jam = dt[i].split(":");
                            Log.d("test", cekWaktu(Integer.parseInt(jam[0]), Integer.parseInt(jam[1]), x, 6));
                            //startAlarm(Integer.parseInt(jam[0]), Integer.parseInt(jam[1]), x);
                            x++;
                        }
                    }
                    if (data.getString(data.getColumnIndex("sabtu")).equals("1")) {
                        for (Integer i = 0; i < dt.length; i++) {
                            String[] jam = dt[i].split(":");
                            Log.d("test", cekWaktu(Integer.parseInt(jam[0]), Integer.parseInt(jam[1]), x, 7));
                            //startAlarm(Integer.parseInt(jam[0]), Integer.parseInt(jam[1]), x);
                            x++;
                        }
                    }
                    if (data.getString(data.getColumnIndex("minggu")).equals("1")) {
                        for (Integer i = 0; i < dt.length; i++) {
                            String[] jam = dt[i].split(":");
                            Log.d("test", cekWaktu(Integer.parseInt(jam[0]), Integer.parseInt(jam[1]), x, 1));
                            //startAlarm(Integer.parseInt(jam[0]), Integer.parseInt(jam[1]), x);
                            x++;
                        }

                    }
                }

                Toast.makeText(
                        getApplicationContext(), "Data Berhasil Disimpan", Toast.LENGTH_SHORT)
                        .show();
                finish();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
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

    String cekWaktu(int hourOfDay, int minute, int count, int day) {

        Intent alarmIntent = new Intent(getBaseContext(), AlarmReceiverPersiapan.class);
        pendingIntent = PendingIntent.getBroadcast(getBaseContext(), count, alarmIntent, 0);
        cancel(pendingIntent);
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.DAY_OF_WEEK, day);
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        String cek = "";
        if (cal.compareTo(calendar) < 0) {
            cek = "Date is : " + calendar.getTime();
        } else {
            calendar.add(Calendar.DAY_OF_MONTH, 7);
            cek = "Date is : " + calendar.getTime();
        }
     /*   manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000 * 60 * 60 * 24*7, pendingIntent);   */
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                manager.INTERVAL_DAY * 7, pendingIntent);
        return cek;

    }

    public void cancel(PendingIntent pi) {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pi);
        //Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
    }
}
