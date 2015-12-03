package com.diet.user.reminderdiet;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.diet.user.reminderdiet.lib.SQLHelper;

public class MainActivity extends AppCompatActivity {
    Button btnUser, btnTips, btnReminder, btnMakan;
    SQLHelper dbHelper;
    SQLiteDatabase db, dbIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnUser = (Button) findViewById(R.id.btnUser);
        btnTips = (Button) findViewById(R.id.btnTips);
        btnReminder = (Button) findViewById(R.id.btnReminder);
        btnMakan = (Button) findViewById(R.id.btnMakan);

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = "";
                dbHelper = new SQLHelper(MainActivity.this);
                db = dbHelper.getReadableDatabase();
                Cursor baca;
                baca = db.rawQuery("select * from userinfo", null);

                if (baca.moveToFirst()) {
                    nama = baca.getString(baca.getColumnIndex("nama"));

                    for (; !baca.isAfterLast(); baca.moveToNext()) {
                        nama = baca.getString(baca.getColumnIndex("nama"));
                    }
                }
                if (nama != "") {
                    Intent i = new Intent(getApplicationContext(), ViewUser.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(getApplicationContext(), AddUser.class);
                    startActivity(i);
                }

            }
        });
        btnReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AlarmMakan.class);
                startActivity(i);
            }
        });
        btnTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PilihTips.class);
                startActivity(i);
            }
        });

        btnMakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Makanan.class);
                startActivity(i);
            }
        });
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Anda Yakin Mau Keluar")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finishAffinity();
                    }
                })
                .setIcon(R.mipmap.ic_launcher)
                .setTitle("Konfirmasi Keluar")
                .setNegativeButton("No", null)
                .show();
        //super.onBackPressed();
    }
}
