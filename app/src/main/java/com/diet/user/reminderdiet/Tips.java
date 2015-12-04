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
    Cursor artips,olahraga;

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

      TableRow trolahraga = new TableRow(this);
        trolahraga.setId(count++);
        trolahraga.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        TableLayout tabelahraga = new TableLayout(this);

        tabelahraga.setBackgroundColor(Color.parseColor("#7474FF"));
        TableRow tbr = new TableRow(this);
        tbr.setMinimumWidth(width);
        tbr.setId(count++);
        tbr.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        TextView label1 = new TextView(this);
        label1.setId(count++);
        label1.setText("Aktifitas");
        label1.setTextSize(19);
        label1.setTextColor(Color.WHITE);
        tbr.addView(label1);
        TextView label50 = new TextView(this);
        label50.setId(count++);
        label50.setText("50");
        label50.setTextSize(19);
        label50.setTextColor(Color.WHITE);
        tbr.addView(label50);
        TextView label60 = new TextView(this);
        label60.setId(count++);
        label60.setText("60");
        label60.setTextSize(19);
        label60.setTextColor(Color.WHITE);
        tbr.addView(label60);
        TextView label70 = new TextView(this);
        label70.setId(count++);
        label70.setText("70");
        label70.setTextSize(19);
        label70.setTextColor(Color.WHITE);
        tbr.addView(label70);
        TextView label80 = new TextView(this);
        label80.setId(count++);
        label80.setText("80");
        label80.setTextSize(19);
        label80.setTextColor(Color.WHITE);
        tbr.addView(label80);
        TextView label90 = new TextView(this);
        label90.setId(count++);
        label90.setText("90");
        label90.setTextSize(19);
        label90.setTextColor(Color.WHITE);
        tbr.addView(label90);

        tabelahraga.addView(tbr, new TableLayout.LayoutParams(
                Toolbar.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT));

        olahraga = dbRead.rawQuery("SELECT * FROM olahraga", null);

        if (olahraga.moveToFirst()) {
            do {

                TableRow tbr1 = new TableRow(this);
                tbr1.setMinimumWidth(width);
                tbr1.setId(count++);
                tbr1.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));

                TextView label11 = new TextView(this);
                label11.setId(count++);
                label11.setText("Aktifitas");
                label11.setTextSize(19);
                label11.setTextColor(Color.WHITE);
                tbr1.addView(label11);
                TextView label501 = new TextView(this);
                label501.setId(count++);
                label501.setText("50");
                label501.setTextSize(19);
                label501.setTextColor(Color.WHITE);
                tbr1.addView(label501);
                TextView label601 = new TextView(this);
                label601.setId(count++);
                label601.setText("60");
                label601.setTextSize(19);
                label601.setTextColor(Color.WHITE);
                tbr1.addView(label601);
                TextView label701 = new TextView(this);
                label701.setId(count++);
                label701.setText("70");
                label701.setTextSize(19);
                label701.setTextColor(Color.WHITE);
                tbr1.addView(label701);
                TextView label801 = new TextView(this);
                label801.setId(count++);
                label801.setText("80");
                label801.setTextSize(19);
                label801.setTextColor(Color.WHITE);
                tbr1.addView(label801);
                TextView label901 = new TextView(this);
                label901.setId(count++);
                label901.setText("90");
                label901.setTextSize(19);
                label901.setTextColor(Color.WHITE);
                tbr1.addView(label901);

                tabelahraga.addView(tbr1, new TableLayout.LayoutParams(
                        Toolbar.LayoutParams.WRAP_CONTENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));
                count++;
            } while (olahraga.moveToNext());
        }



        trolahraga.addView(tabelahraga);

        tl.addView(trolahraga, new TableLayout.LayoutParams(
                Toolbar.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT));


    }
}

