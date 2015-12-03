package com.diet.user.reminderdiet;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.diet.user.reminderdiet.lib.SQLHelper;

public class ViewKandungan extends AppCompatActivity {
    SQLHelper dbHelper;
    SQLiteDatabase dbRead;
    TextView txtMakanan, txtJumlah, txtEnergi, txtAir, txtProtein, txtLemak, txtSerat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_kandungan);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        txtMakanan = (TextView) findViewById(R.id.txtMakanan);
        txtJumlah = (TextView) findViewById(R.id.txtJumlah);
        txtEnergi = (TextView) findViewById(R.id.txtEnergi);
        txtAir = (TextView) findViewById(R.id.txtAir);
        txtProtein = (TextView) findViewById(R.id.txtProtein);
        txtLemak = (TextView) findViewById(R.id.txtLemak);
        txtSerat = (TextView) findViewById(R.id.txtSerat);
        Intent i = getIntent();
        String makanan = i.getStringExtra("makanan");
        txtMakanan.setText(makanan);

        dbHelper = new SQLHelper(ViewKandungan.this);
        dbRead = dbHelper.getReadableDatabase();

        Cursor baca;
        baca = dbRead.rawQuery("SELECT * FROM  kandungan_makanan WHERE makanan='" + makanan + "'", null);
        if (baca.moveToFirst()) {
            txtMakanan.setText(baca.getString(baca.getColumnIndex("makanan")));
            txtJumlah.setText(baca.getString(baca.getColumnIndex("jumlah")));
            txtEnergi.setText(baca.getString(baca.getColumnIndex("energi")));
            txtAir.setText(baca.getString(baca.getColumnIndex("air")));
            txtProtein.setText(baca.getString(baca.getColumnIndex("protein")));
            txtLemak.setText(baca.getString(baca.getColumnIndex("lemak")));
            txtSerat.setText(baca.getString(baca.getColumnIndex("serat")));
        }
    }
}
