package com.diet.user.reminderdiet;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.diet.user.reminderdiet.lib.ExpandableAdapter;
import com.diet.user.reminderdiet.lib.SQLHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MenuMakanan extends AppCompatActivity {
    final List<HashMap<String, String>> list =
            new ArrayList<HashMap<String, String>>();
    SQLHelper dbHelper;
    SQLiteDatabase dbRead;
    ListView listview;
    ExpandableListView els;
    List<String> listDataHeader;
    List<String> listIdDataHeader;
    HashMap<String, List<String>> listDataChild;
    ExpandableAdapter listAdapter;
    String kal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_makanan);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Intent i = getIntent();
        kal = i.getStringExtra("kategori");
        lDt(kal);

        els = (ExpandableListView) findViewById(R.id.expdls);
        //addList();
        // prepareListData();

        listAdapter = new ExpandableAdapter(this, listDataHeader, listDataChild);

        els.setAdapter(listAdapter);
        els.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
            /*    Toast.makeText(getApplicationContext(),
                        "Menampilkan " + listDataHeader.get(groupPosition),
                        Toast.LENGTH_SHORT).show();*/
                Intent i = new Intent(getApplicationContext(), ViewMakanan.class);
                i.putExtra("kategori", kal);
                i.putExtra("menu", listDataHeader.get(groupPosition));

                startActivity(i);
                return true;
            }
        });

/*        // Listview Group expanded listener
        els.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        els.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });*/
        // Listview on child click listener
        els.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub

                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });

    }


    private void lDt(String kal) {

        listIdDataHeader = new ArrayList<String>();
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        dbHelper = new SQLHelper(MenuMakanan.this);
        dbRead = dbHelper.getReadableDatabase();
        Cursor menu = null, makanan;
        if (kal.equals("I")) {
            menu = dbRead.rawQuery("select kategori.id_kategori,jenis_kategori,sum(kalori_makanan) as kalori from kategori INNER JOIN makanan_kategori ON kategori.id_kategori=makanan_kategori.id_kategori\n" +
                    "where makanan_kategori.g='1' GROUP BY jenis_kategori", null);
        } else {
            menu = dbRead.rawQuery("select kategori.id_kategori,jenis_kategori,sum(kalori_makanan) as kalori from kategori INNER JOIN makanan_kategori ON kategori.id_kategori=makanan_kategori.id_kategori\n" +
                    "where makanan_kategori.g='2' GROUP BY jenis_kategori", null);
        }
        String[] waktu = {"p", "s1", "s", "s2", "m"};
        if (menu.moveToFirst()) {
            do {
                String header = menu.getString(menu.getColumnIndex("jenis_kategori"));
                listDataHeader.add(header);
                listIdDataHeader.add(menu.getString(menu.getColumnIndex("id_kategori")));
                List<String> dt = new ArrayList<String>();
                for (int i = 0; i < 5; i++) {
                    String isi = "";
                    if (waktu[i].equals("p")) {
                        isi = isi + "Pagi";
                    } else if (waktu[i].equals("s1")) {
                        isi = isi + "Selingan";
                    } else if (waktu[i].equals("s")) {
                        isi = isi + "Siang";
                    } else if (waktu[i].equals("s2")) {
                        isi = isi + "Selingan";
                    } else {
                        isi = isi + "Malam";
                    }

                    /*makanan = dbRead.rawQuery("SELECT * FROM makanan_kategori INNER JOIN makanan on makanan_kategori.id_makanan= makanan.id_makanan where makanan_kategori.id_kategori=" + menu.getString(menu.getColumnIndex("id_kategori")) + " and makanan_kategori.jenis='" + waktu[i] + "'", null);
                    if (makanan.moveToFirst()) {
                        String cek="";
                        do {
                            cek = cek +  makanan.getString(makanan.getColumnIndex("jenis_makanan")) + "-" + makanan.getString(makanan.getColumnIndex("jumlah_makanan")) + "-" + makanan.getString(makanan.getColumnIndex("kalori_makanan"))+"#";

                        } while (makanan.moveToNext());
                        isi=isi+"!"+cek;
                    }

                    dt.add(isi);*/
                }
                listDataChild.put(header, dt);

            } while (menu.moveToNext());
        } else {
            Toast.makeText(
                    getApplicationContext(), "Data Masih Kosong !", Toast.LENGTH_SHORT)
                    .show();
        }
    }
}


