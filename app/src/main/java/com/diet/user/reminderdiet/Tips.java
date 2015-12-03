package com.diet.user.reminderdiet;

import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toolbar;

import com.diet.user.reminderdiet.lib.SQLHelper;


public class Tips extends AppCompatActivity {

    SQLHelper dbHelper;
    SQLiteDatabase dbRead;
    TableLayout tl;
    Cursor artips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        dbHelper = new SQLHelper(Tips.this);
        dbRead = dbHelper.getReadableDatabase();
        tl = (TableLayout) findViewById(R.id.tbViewTips);


        isi_data();
    }


    void isi_data() {
        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();

        Integer count = 150;
        TableRow trWH = new TableRow(this);
        trWH.setId(count++);
        trWH.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        TextView labelHMakanan = new TextView(this);
        labelHMakanan.setId(count++);
        labelHMakanan.setText("Pola Makan ");
        labelHMakanan.setTextSize(19);
        labelHMakanan.setTextColor(Color.WHITE);
        trWH.addView(labelHMakanan);

        tl.addView(trWH, new TableLayout.LayoutParams(
                Toolbar.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        artips = dbRead.rawQuery("select * from tips where judul ='Pola makan '", null);
        Integer no = 1;
        if (artips.moveToFirst()) {
            do {
                TableRow trW = new TableRow(this);
                trW.setId(count++);
                trW.setBackgroundColor(Color.parseColor("#7474FF"));
                trW.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));

                TextView labelMakanan = new TextView(this);
                labelMakanan.setPadding(20, 0, 0, 5);
                labelMakanan.setId(count++);
                labelMakanan.setText(no++ + ". " + artips.getString(artips.getColumnIndex("diskripsi")));
                labelMakanan.setTextSize(19);
                labelMakanan.setMaxWidth(width - 25);
                labelMakanan.setTextColor(Color.WHITE);
                trW.addView(labelMakanan);


                tl.addView(trW, new TableLayout.LayoutParams(
                        Toolbar.LayoutParams.WRAP_CONTENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));
                count++;
            } while (artips.moveToNext());
        }

        TableRow trWH2 = new TableRow(this);
        trWH2.setId(count++);
        trWH2.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        TextView labelHMakanan2 = new TextView(this);
        labelHMakanan2.setId(count++);
        labelHMakanan2.setText("Aktifitas Fisik ");
        labelHMakanan2.setTextSize(19);
        labelHMakanan2.setTextColor(Color.WHITE);
        trWH2.addView(labelHMakanan2);

        tl.addView(trWH2, new TableLayout.LayoutParams(
                Toolbar.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        artips = dbRead.rawQuery("select * from tips where judul ='Aktifitas Fisik '", null);
        Integer no2 = 1;
        if (artips.moveToFirst()) {
            do {

                TableRow trW2 = new TableRow(this);
                trW2.setId(count++);
                trW2.setBackgroundColor(Color.parseColor("#7474FF"));
                trW2.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));

                TextView labelMakanan2 = new TextView(this);
                labelMakanan2.setPadding(20, 0, 0, 5);
                labelMakanan2.setId(count++);
                labelMakanan2.setText(no2++ + ". " + artips.getString(artips.getColumnIndex("diskripsi")));
                labelMakanan2.setTextSize(19);
                labelMakanan2.setMaxWidth(width - 25);
                labelMakanan2.setTextColor(Color.WHITE);
                trW2.addView(labelMakanan2);


                tl.addView(trW2, new TableLayout.LayoutParams(
                        Toolbar.LayoutParams.WRAP_CONTENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));
                count++;
            } while (artips.moveToNext());
        }

     /* TableRow trolahraga = new TableRow(this);
        trWH2.setId(count++);
        trWH2.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        TableLayout tabelahraga = new TableLayout(this);

        */



        /*
        trWH2.addView(tabelahraga);

        tl.addView(trWH2, new TableLayout.LayoutParams(
                Toolbar.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT));*/


    }
}

