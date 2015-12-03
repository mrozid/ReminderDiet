package com.diet.user.reminderdiet;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toolbar;

import com.diet.user.reminderdiet.lib.SQLHelper;

public class ViewMakanan extends AppCompatActivity {
    TableLayout tl;
    String menu, kat;
    TextView tv;
    SQLHelper dbHelper;
    SQLiteDatabase dbRead;
    Cursor makanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_makanan);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        dbHelper = new SQLHelper(ViewMakanan.this);
        dbRead = dbHelper.getReadableDatabase();
        tl = (TableLayout) findViewById(R.id.tbMenu);
        tv = (TextView) findViewById(R.id.viewTextMenu);
        Intent i = getIntent();
        menu = i.getStringExtra("menu");
        kat = i.getStringExtra("kategori");
        tv.setText(menu);
        isi_data();
    }


    void isi_data() {
        String[] waktu = {"p", "s1", "s", "s2", "m"};
        Integer count = 300;
        TableRow trWH = new TableRow(this);
        trWH.setId(count++);
        trWH.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        TextView labelHMakanan = new TextView(this);
        labelHMakanan.setId(count++);
        labelHMakanan.setText("Makanan ");
        labelHMakanan.setTextSize(19);
        labelHMakanan.setTextColor(Color.WHITE);
        trWH.addView(labelHMakanan);
        TextView labelHJUmlah = new TextView(this);
        labelHJUmlah.setId(count++);
        labelHJUmlah.setText("Jumlah ");
        labelHJUmlah.setTextSize(19);
        labelHJUmlah.setTextColor(Color.WHITE);
        trWH.addView(labelHJUmlah);
        TextView labelHKKL = new TextView(this);
        labelHKKL.setId(count++);
        labelHKKL.setText(" KKL ");
        labelHKKL.setTextSize(19);
        labelHKKL.setTextColor(Color.WHITE);
        trWH.addView(labelHKKL);

        tl.addView(trWH, new TableLayout.LayoutParams(
                Toolbar.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        for (int i = 0; i < 5; i++) {

            String isi = "";
            if (waktu[i].equals("p")) {
                isi = " Pagi ";
            } else if (waktu[i].equals("s1")) {
                isi = " Selingan ";
            } else if (waktu[i].equals("s")) {
                isi = " Siang ";
            } else if (waktu[i].equals("s2")) {
                isi = " Selingan ";
            } else {
                isi = " Malam ";
            }

            TableRow tr = new TableRow(this);
            tr.setBackgroundColor(Color.GRAY);
            tr.setId(count++);
            tr.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            TextView labelMenu = new TextView(this);
            labelMenu.setId(count++);
            labelMenu.setText(isi);
            labelMenu.setTextSize(19);
            labelMenu.setTextColor(Color.WHITE);
            tr.addView(labelMenu);
            tl.addView(tr, new TableLayout.LayoutParams(
                    Toolbar.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
            if (kat.equals("I")) {
                makanan = dbRead.rawQuery("SELECT * FROM makanan_kategori \n" +
                        "INNER JOIN makanan on makanan_kategori.id_makanan= makanan.id_makanan \n" +
                        "INNER JOIN kategori on kategori.id_kategori=makanan_kategori.id_kategori\n" +
                        "where makanan_kategori.g='1' and kategori.jenis_kategori='" + menu + "' and makanan_kategori.jenis='" + waktu[i] + "'", null);
            } else {
                makanan = dbRead.rawQuery("SELECT * FROM makanan_kategori \n" +
                        "INNER JOIN makanan on makanan_kategori.id_makanan= makanan.id_makanan \n" +
                        "INNER JOIN kategori on kategori.id_kategori=makanan_kategori.id_kategori\n" +
                        "where makanan_kategori.g='2' and kategori.jenis_kategori='" + menu + "' and makanan_kategori.jenis='" + waktu[i] + "'", null);
            }

            if (makanan.moveToFirst()) {
                String cek = "";
                do {
                    // cek = cek + makanan.getString(makanan.getColumnIndex("jenis_makanan")) + "-" + makanan.getString(makanan.getColumnIndex("jumlah_makanan")) + "-" + makanan.getString(makanan.getColumnIndex("kalori_makanan")) + "#";
                    TableRow trW = new TableRow(this);
                    trW.setId(count++);
                    trW.setLayoutParams(new TableRow.LayoutParams(
                            TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT));

                    TextView labelMakanan = new TextView(this);
                    labelMakanan.setId(count++);
                    labelMakanan.setText(makanan.getString(makanan.getColumnIndex("jenis_makanan")));
                    labelMakanan.setTextSize(19);
                    labelMakanan.setTextColor(Color.WHITE);
                    trW.addView(labelMakanan);
                    TextView labelJUmlah = new TextView(this);
                    labelJUmlah.setId(count++);
                    labelJUmlah.setText(makanan.getString(makanan.getColumnIndex("jumlah_makanan")));
                    labelJUmlah.setTextSize(19);
                    labelJUmlah.setTextColor(Color.WHITE);
                    trW.addView(labelJUmlah);
                    TextView labelKKL = new TextView(this);
                    labelKKL.setId(count++);
                    labelKKL.setText(makanan.getString(makanan.getColumnIndex("kalori_makanan")));
                    labelKKL.setTextSize(19);
                    labelKKL.setTextColor(Color.WHITE);
                    trW.addView(labelKKL);


                    tl.addView(trW, new TableLayout.LayoutParams(
                            Toolbar.LayoutParams.WRAP_CONTENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
                    count++;
                } while (makanan.moveToNext());
            }
            count++;
        }
    }
}
