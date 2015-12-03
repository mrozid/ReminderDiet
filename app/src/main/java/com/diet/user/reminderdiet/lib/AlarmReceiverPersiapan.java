package com.diet.user.reminderdiet.lib;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

import com.diet.user.reminderdiet.Makanan;
import com.diet.user.reminderdiet.MenuMakanan;
import com.diet.user.reminderdiet.R;

import java.util.HashMap;

/**
 * Created by m rosyid on 10/15/2015.
 */
public class AlarmReceiverPersiapan extends BroadcastReceiver {
    SessionManager ln;

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Silahkan Persiapkan Makanan Anda", Toast.LENGTH_LONG).show();
        MediaPlayer player = MediaPlayer.create(context, R.raw.tone);
        player.start();
        intent = new Intent();
        ln = new SessionManager(context.getApplicationContext());
        HashMap<String, String> user = ln.getUserDetails();
        if (ln.isLoggedIn()) {
            String name = user.get(SessionManager.KEY_KKL);
            intent.putExtra("kategori", name);
            intent.setClass(context, MenuMakanan.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else {
            intent.setClass(context, Makanan.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }

    }
}
