package com.diet.user.reminderdiet;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Makanan extends AppCompatActivity {
    Button btnI, btnII;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makanan);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        btnI=(Button) findViewById(R.id.btnI);
        btnII=(Button) findViewById(R.id.btnII);
        i = new Intent(getApplicationContext(), MenuMakanan.class);
        btnI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("kategori","I");
                startActivity(i);
            }
        });

        btnII.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i.putExtra("kategori","II");
                startActivity(i);
            }
        });
    }
}
