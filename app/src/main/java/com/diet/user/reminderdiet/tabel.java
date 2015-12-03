package com.diet.user.reminderdiet;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.diet.user.reminderdiet.lib.SQLHelper;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class tabel extends AppCompatActivity {
    Button btn;
    SQLHelper dbHelper;
    SQLiteDatabase dbRead, dbIn;
    CheckBox cekSenin, cekSelasa, cekRabu, cekKamis, cekJumat, cekSabtu, cekMinggu;
    TextView cektet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabel);
        btn = (Button) findViewById(R.id.btnCkCk);
        dbHelper = new SQLHelper(tabel.this);
        dbRead = dbHelper.getReadableDatabase();
        dbIn = dbHelper.getWritableDatabase();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();
            }
        });


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

        LayoutInflater layoutInflater = LayoutInflater.from(tabel.this);
        View promptView = layoutInflater.inflate(R.layout.day_choser, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(tabel.this);
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

    // senin 2,selasa 3, rabu 4, kamis 5, jumat 6 sabtu 7 minggu 1
    private void forday(int i) {
        Calendar calNow = Calendar.getInstance();
        Calendar calSet = new GregorianCalendar();

        calSet.set(Calendar.DAY_OF_WEEK, i);
        calSet.set(Calendar.HOUR_OF_DAY, 1);
        calSet.set(Calendar.MINUTE, 2);
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);

        Toast.makeText(getApplicationContext(), "Date is : " + calSet.getTime(), Toast.LENGTH_LONG).show();
    }

}
