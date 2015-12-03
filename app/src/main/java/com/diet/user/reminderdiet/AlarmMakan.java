package com.diet.user.reminderdiet;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.diet.user.reminderdiet.lib.AlarmReceiverMakan;
import com.diet.user.reminderdiet.lib.SQLHelper;

import java.util.Calendar;

public class AlarmMakan extends AppCompatActivity {
    private TextView txtPagi, txtSelingan, txtSiang, txtOlahraga, txtMalam;
    public Button btnAlarmMakan, btnSetHari;
    SQLHelper dbHelper;
    SQLiteDatabase dbRead, dbIn;
    private int hour;
    private int minute;
    private PendingIntent pendingIntent;
    CheckBox cekSenin, cekSelasa, cekRabu, cekKamis, cekJumat, cekSabtu, cekMinggu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_makan);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        txtPagi = (TextView) findViewById(R.id.txtPagi);
        txtSelingan = (TextView) findViewById(R.id.txtSelingan);
        txtSiang = (TextView) findViewById(R.id.txtSiang);
        txtOlahraga = (TextView) findViewById(R.id.txtOlahraga);
        txtMalam = (TextView) findViewById(R.id.txtMalam);
        btnSetHari = (Button) findViewById(R.id.btnSetHari);
        btnAlarmMakan = (Button) findViewById(R.id.btnAlarmMakan);
        dbHelper = new SQLHelper(AlarmMakan.this);
        dbIn = dbHelper.getWritableDatabase();
        dbRead = dbHelper.getReadableDatabase();
        addButtonClickListener();
        setValue();
    }

    public void addButtonClickListener() {
        btnSetHari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();
            }
        });
        btnAlarmMakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pagi = txtPagi.getText().toString();
                String selingan = txtSelingan.getText().toString();
                String siang = txtSiang.getText().toString();
                String olahraga = txtOlahraga.getText().toString();
                String malam = txtMalam.getText().toString();

                String[] dt = {pagi, selingan, siang, olahraga, malam};
                Integer x = 10;
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

                Intent i = new Intent(getApplicationContext(), AlarmPersiapan.class);
                startActivity(i);
            }
        });
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

    void check(String day) {
        String sql = "update day_alarm SET " + day + " = 1";
        dbIn.execSQL(sql);
    }

    void unCheck(String day) {
        String sql = "update day_alarm SET " + day + " = 0";
        dbIn.execSQL(sql);
    }

    void isiChck() {
        Cursor baca;
        baca = dbRead.rawQuery("SELECT * from day_alarm", null);
        if (baca.moveToFirst()) {

            if (baca.getString(baca.getColumnIndex("senin")).equals("1")) {
                cekSenin.setChecked(true);
            } else {
                cekSenin.setChecked(false);
            }
            if (baca.getString(baca.getColumnIndex("selasa")).equals("1")) {
                cekSelasa.setChecked(true);
            } else {
                cekSelasa.setChecked(false);
            }
            if (baca.getString(baca.getColumnIndex("rabu")).equals("1")) {
                cekRabu.setChecked(true);
            } else {
                cekRabu.setChecked(false);
            }
            if (baca.getString(baca.getColumnIndex("kamis")).equals("1")) {
                cekKamis.setChecked(true);
            } else {
                cekKamis.setChecked(false);
            }
            if (baca.getString(baca.getColumnIndex("jumat")).equals("1")) {
                cekJumat.setChecked(true);
            } else {
                cekJumat.setChecked(false);
            }
            if (baca.getString(baca.getColumnIndex("sabtu")).equals("1")) {
                cekSabtu.setChecked(true);
            } else {
                cekSabtu.setChecked(false);
            }
            if (baca.getString(baca.getColumnIndex("minggu")).equals("1")) {
                cekMinggu.setChecked(true);
            } else {
                cekMinggu.setChecked(false);
            }


        }
    }

    protected void showInputDialog() {

        LayoutInflater layoutInflater = LayoutInflater.from(AlarmMakan.this);
        View promptView = layoutInflater.inflate(R.layout.day_choser, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AlarmMakan.this);
        alertDialogBuilder.setView(promptView);

        cekSenin = (CheckBox) promptView.findViewById(R.id.checkSenin);
        cekSelasa = (CheckBox) promptView.findViewById(R.id.checkSelasa);
        cekRabu = (CheckBox) promptView.findViewById(R.id.checkRabu);
        cekKamis = (CheckBox) promptView.findViewById(R.id.checkKamis);
        cekJumat = (CheckBox) promptView.findViewById(R.id.checkJumat);
        cekSabtu = (CheckBox) promptView.findViewById(R.id.checkSabtu);
        cekMinggu = (CheckBox) promptView.findViewById(R.id.checkMinggu);

        isiChck();

        cekSenin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    check("senin");
                } else {
                    unCheck("senin");
                }

            }
        });
        cekSelasa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    check("selasa");
                } else {
                    unCheck("selasa");
                }

            }
        });
        cekRabu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    check("rabu");
                } else {
                    unCheck("rabu");
                }

            }
        });
        cekKamis.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    check("kamis");
                } else {
                    unCheck("kamis");
                }

            }
        });
        cekJumat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    check("jumat");
                } else {
                    unCheck("jumat");
                }

            }
        });
        cekSabtu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    check("sabtu");
                } else {
                    unCheck("sabtu");
                }

            }
        });
        cekMinggu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    check("minggu");
                } else {
                    unCheck("minggu");
                }

            }
        });

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
               /* .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });*/

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    String cekWaktu(int hourOfDay, int minute, int count, int day) {

        Intent alarmIntent = new Intent(getBaseContext(), AlarmReceiverMakan.class);
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
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                manager.INTERVAL_DAY * 7, pendingIntent);
        return cek;
    }

   /* public void startAlarm(int hourOfDay, int minute, int count) {

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


        //manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000 * 60 * 60 * 24, pendingIntent);
    }*/

    public void cancel(PendingIntent pi) {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pi);
        //Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
    }

}
