package com.diet.user.reminderdiet;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewTips extends AppCompatActivity {
TextView makanan,kol,kal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tips);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        makanan = (TextView) findViewById(R.id.txtVMakanan);
        kal = (TextView) findViewById(R.id.txtDiskripsi);
        Intent i = getIntent();
        makanan.setText(i.getStringExtra("judul"));
        kal.setText(i.getStringExtra("diskripsi"));


    }
}
