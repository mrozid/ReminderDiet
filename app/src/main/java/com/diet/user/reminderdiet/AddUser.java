package com.diet.user.reminderdiet;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.diet.user.reminderdiet.lib.SQLHelper;
import com.diet.user.reminderdiet.lib.SessionManager;

public class AddUser extends AppCompatActivity {
    Button btnSimpan;
    EditText inputNama, inputUsia, inputTinggiBadan, inputBeratBadan;
    RadioGroup radioSex;
    private RadioButton radioSexButton;
    String nama, usia, tinggibadan, beratbadan, jk;
    Boolean cekJk = false, cekNama = false, cekUsia = false, cekTinggi = false, cekberat = false, aksi;
    SQLHelper dbHelper;
    SQLiteDatabase dbIn, dbRead;
    int kkl, bbi;
    SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnSimpan = (Button) findViewById(R.id.btnSimpan);
        radioSex = (RadioGroup) findViewById(R.id.radioSex);
        inputNama = (EditText) findViewById(R.id.inputNama);
        inputUsia = (EditText) findViewById(R.id.inputUsia);
        inputTinggiBadan = (EditText) findViewById(R.id.inputTinggiBadan);
        inputBeratBadan = (EditText) findViewById(R.id.inputBeratBadan);
        session = new SessionManager(getApplicationContext());
        aksi = isi();

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedId = radioSex.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    cekJk = false;
                } else {
                    radioSexButton = (RadioButton) findViewById(selectedId);
                    jk = (String) radioSexButton.getText();
                    cekJk = true;
                }


                cekNama = harusDiisi(inputNama);
                cekUsia = harusDiisi(inputUsia);
                cekTinggi = harusDiisi(inputTinggiBadan);
                cekberat = harusDiisi(inputBeratBadan);


                if (!cekNama || !cekJk || !cekTinggi || !cekUsia || !cekberat) {
                    if (!cekJk) {
                        int redColorValue = Color.RED;
                        radioSex.setBackgroundColor(redColorValue);
                        Toast.makeText(getApplicationContext(), "Data Belum lengkap dan Jenis Kelamin Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                    } else {
                        int whiteColorValue = Color.WHITE;
                        radioSex.setBackgroundColor(whiteColorValue);
                        Toast.makeText(getApplicationContext(), "Data Belum lengkap", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    nama = inputNama.getText().toString();
                    usia = inputUsia.getText().toString();
                    tinggibadan = inputTinggiBadan.getText().toString();
                    beratbadan = inputBeratBadan.getText().toString();

                    if (jk == "Perempuan") {
                        kkl = (int) (65.5 + (9.6 * Integer.parseInt(beratbadan)) + (1.7 * Integer.parseInt(tinggibadan)) - (4.7 * Integer.parseInt(usia)));
                    } else {
                        kkl = (int) (66 + (13.7 * Integer.parseInt(beratbadan)) + (5 * Integer.parseInt(tinggibadan) - (6.8 * Integer.parseInt(usia))));
                    }
                    bbi = (int) ((Integer.parseInt(tinggibadan) - 100) * 0.9);
                    dbHelper = new SQLHelper(AddUser.this);
                    dbIn = dbHelper.getWritableDatabase();

                    if (aksi) {
                        String query = "update userinfo set nama='" + nama + "',tb_user='" + tinggibadan + "',usia_user='" + usia + "',bb_user='" + beratbadan + "',bbi_user='" + bbi + "',kkal_user='" + kkl + "',jk_user='" + jk + "'";
                        dbIn.execSQL(query);
                        Toast.makeText(getApplicationContext(), "Berhasil merubah data", Toast.LENGTH_LONG).show();
                    } else {
                        String query = "INSERT INTO userinfo VALUES (NULL,'" + nama + "','" + tinggibadan + "','" + usia + "','" + beratbadan + "','" + bbi + "','" + kkl + "','" + jk + "')";
                        dbIn.execSQL(query);
                        Toast.makeText(getApplicationContext(), "Berhasil memasukan data", Toast.LENGTH_LONG).show();

                    }
                    String Stringkate ="";
                    if(kkl<1500){
                        Stringkate = "I";
                    }else if(kkl>=1500){
                        Stringkate = "II";
                    }
                    session.createLoginSession(nama,Stringkate);
                    finish();
                    Intent i = new Intent(getApplicationContext(), ViewUser.class);
                    startActivity(i);


                }
            }
        });
    }

    public boolean harusDiisi(EditText editText) {

        String text = editText.getText().toString().trim();
        editText.setError(null);
        // length 0 means there is no text
        if (text.length() == 0) {
            editText.setError(Html
                    .fromHtml("<font color='red'>Input tidak boleh kosong</font>"));
            return false;
        } else {
            return true;
        }

    }

    boolean isi() {
        String nama = "", usia = "", tingi = "", berat = "", jk = "", bbi = "", kkl = "";
        dbHelper = new SQLHelper(AddUser.this);
        dbRead = dbHelper.getReadableDatabase();
        Cursor baca;
        baca = dbRead.rawQuery("select * from userinfo limit 1", null);

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
        if (nama == "") {
            return false;
        } else {
            btnSimpan.setText("Edit");
            inputNama.setText(nama);
            inputBeratBadan.setText(berat);
            inputTinggiBadan.setText(tingi);
            inputUsia.setText(usia);
            if (jk == "Perempuan") {
                radioSex.check(R.id.radioPrempuan);
            } else {
                radioSex.check(R.id.radioLaki);
            }
            return true;
        }
    }
}
