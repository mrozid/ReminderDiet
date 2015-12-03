package com.diet.user.reminderdiet;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PilihTips extends AppCompatActivity {
    Button btnTips,btnKan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_tips);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnTips = (Button) findViewById(R.id.btnTips);
        btnKan = (Button) findViewById(R.id.btnKan);

        btnTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i = new Intent(getApplicationContext(), Tips.class);
                i.putExtra("kategori","t");
                startActivity(i);
            }
        });
        btnKan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Kandungan.class);
                startActivity(i);
            }
        });
    }
}
