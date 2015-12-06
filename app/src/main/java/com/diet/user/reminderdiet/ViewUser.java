package com.diet.user.reminderdiet;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.diet.user.reminderdiet.lib.SQLHelper;
import com.diet.user.reminderdiet.lib.SessionManager;

import java.math.BigDecimal;

public class ViewUser extends AppCompatActivity {
    Button btnHome, btnEdit;
    TextView txtTampilNama, txtTampilKet, txtTampilUsia, txtTampilTinggiBadan, txtTampilBeratBadan, txtTampilJk, txtTampilBBideal, txtTampilKalori, txtTampilObesitas;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        btnHome = (Button) findViewById(R.id.btnHome);
        btnEdit = (Button) findViewById(R.id.btnKeedit);

    /*    session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        nam = user.get(SessionManager.KEY_NAME);*/

        bacaDb();

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent newIntent = new Intent(ViewUser.this, MainActivity.class);
                startActivityForResult(newIntent, 0);
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddUser.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent newIntent = new Intent(ViewUser.this, MainActivity.class);
        startActivityForResult(newIntent, 0);
        //super.onBackPressed();
    }

    void bacaDb() {
        String nama = "", usia = "", tingi = "", berat = "", jk = "", bbi = "", kkl = "";
        SQLHelper dbHelper = new SQLHelper(ViewUser.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor baca;
        baca = db.rawQuery("select * from userinfo limit 1", null);

        if (baca.moveToFirst()) {
            nama = baca.getString(baca.getColumnIndex("nama"));
            usia = baca.getString(baca.getColumnIndex("usia_user"));
            tingi = baca.getString(baca.getColumnIndex("tb_user"));
            berat = baca.getString(baca.getColumnIndex("bb_user"));
            jk = baca.getString(baca.getColumnIndex("jk_user"));
            bbi = baca.getString(baca.getColumnIndex("bbi_user"));
            kkl = baca.getString(baca.getColumnIndex("kkal_user"));
/*
            for (; !baca.isAfterLast(); baca.moveToNext()) {
                nama = baca.getString(baca.getColumnIndex("nama"));
            }*/
        }
        txtTampilNama = (TextView) findViewById(R.id.txtTampilNama);
        txtTampilNama.setText(nama);
        txtTampilUsia = (TextView) findViewById(R.id.txtTampilUsia);
        txtTampilUsia.setText(usia);
        txtTampilTinggiBadan = (TextView) findViewById(R.id.txtTampilTinggiBadan);
        txtTampilTinggiBadan.setText(tingi);
        txtTampilBeratBadan = (TextView) findViewById(R.id.txtTampilBeratBadan);
        txtTampilBeratBadan.setText(berat);
        txtTampilJk = (TextView) findViewById(R.id.txtTampilJk);
        txtTampilJk.setText(jk);
        txtTampilBBideal = (TextView) findViewById(R.id.txtTampilBBideal);
        txtTampilBBideal.setText(bbi);
        txtTampilKalori = (TextView) findViewById(R.id.txtTampilKalori);
        txtTampilKalori.setText(kkl);
        txtTampilObesitas = (TextView) findViewById(R.id.txtTampilObesitas);


        double tb = Double.parseDouble(tingi) / 100;
        double bagi = tb * tb;
        double BB = Double.parseDouble(berat);
        double imt = BB / bagi;
        String im = null;

        if (imt < 18.5) {
            im = "Kurang";
        } else if (imt >= 18.5 && imt < 22.9) {
            im = "Normal";
        } else if (imt >= 22.9 && imt <= 23.0) {
            im = "Kelebihan berat badan";
        } else if (imt > 23.0 && imt < 26.9) {
            im = "Praobesitas";
        } else if (imt >= 26.9) {
            im = "Obesitas";
        }

        int decimalPlace = 2;
        BigDecimal bd = new BigDecimal(imt);
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_UP);
        imt = bd.doubleValue();

        txtTampilObesitas.setText(imt + " Kg/m \n" + im);

        txtTampilKet = (TextView) findViewById(R.id.txtTampilKet);
        String kate = null;
        if (Integer.valueOf(kkl) < 1500) {
            kate = "Diet Rendah 1200";
        } else if (Integer.valueOf(kkl) >= 1500) {
            kate = "Diet Rendah 1500";
        }
        txtTampilKet.setText(kate);
    }
}
