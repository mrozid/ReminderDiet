package com.diet.user.reminderdiet.lib;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.diet.user.reminderdiet.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by m rosyid on 10/7/2015.
 */
public class MyListAdapter extends BaseAdapter {
    private Activity activity;

    LayoutInflater inflater = null;
    List<HashMap<String, String>> mIdMap;
    ViewHolder holder;

    public MyListAdapter() {
        // TODO Auto-generated constructor stub
    }

    public MyListAdapter(Activity a, List<HashMap<String, String>> dt) {
        activity = a;
        mIdMap = dt;
        inflater = (LayoutInflater) a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {

        return mIdMap.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null) {
            vi = inflater.inflate(R.layout.list_row, null);
            holder = new ViewHolder();
            holder.judul = (TextView) vi.findViewById(R.id.judul); // title
            holder.isi = (TextView) vi.findViewById(R.id.diskripsi); // artist name

            holder.judul.setText(mIdMap.get(position).get("judul"));
            holder.isi.setText(mIdMap.get(position).get("diskripsi"));

            vi.setTag(holder);

        } else {
            holder = (ViewHolder) vi.getTag();
        }
        return vi;
    }
}

class ViewHolder {

    TextView judul;
    TextView isi;
}