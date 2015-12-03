package com.diet.user.reminderdiet;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.diet.user.reminderdiet.lib.SQLHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Kandungan extends AppCompatActivity {

    ListView listview;
    SQLHelper dbHelper;
    SQLiteDatabase dbRead;
    ArrayList<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kandungan);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        listview = (ListView) findViewById(R.id.listKandungan);

        addList();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                Intent i = new Intent(getApplicationContext(), ViewKandungan.class);
                i.putExtra("makanan", list.get(position));
                startActivity(i);
            }

        });
    }

    public void addList() {
        dbHelper = new SQLHelper(Kandungan.this);
        dbRead = dbHelper.getReadableDatabase();
        Cursor baca;
        baca = dbRead.rawQuery("SELECT * FROM  kandungan_makanan ORDER BY makanan ASC", null);


        if (baca.moveToFirst()) {
            do {
                list.add(baca.getString(baca.getColumnIndex("makanan")));
            } while (baca.moveToNext());
        }
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);


    }

}

class StableArrayAdapter extends ArrayAdapter<String> {

    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

    public StableArrayAdapter(Context context, int textViewResourceId,
                              List<String> objects) {
        super(context, textViewResourceId, objects);
        for (int i = 0; i < objects.size(); ++i) {
            mIdMap.put(objects.get(i), i);
        }
    }

    @Override
    public long getItemId(int position) {
        String item = getItem(position);
        return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}


