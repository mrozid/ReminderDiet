package com.diet.user.reminderdiet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.diet.user.reminderdiet.lib.SQLHelper;

public class Splash extends AppCompatActivity {
    SQLHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        inisialisasiDb();
        final boolean active = true;
        final int splashTime = 2000;
        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (active && (waited < splashTime)) {
                        sleep(100);
                        if (active) {
                            waited += 100;
                        }
                    }
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    finish();
                    Intent newIntent = new Intent(Splash.this, MainActivity.class);
                    startActivityForResult(newIntent, 0);
                }
            }
        };
        splashTread.start();
    }


    void inisialisasiDb() {
        dbHelper = new SQLHelper(Splash.this);
        try {
            dbHelper.createDataBase();
        } catch (Exception ioe) {
            Toast.makeText(getApplicationContext(), "Gagal inisialisasi database silahkan cek provider anda", Toast.LENGTH_LONG).show();
        }
    }

}
