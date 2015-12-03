package com.diet.user.reminderdiet.lib;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

import com.diet.user.reminderdiet.JamMakan;
import com.diet.user.reminderdiet.R;
import com.diet.user.reminderdiet.ReminderMakan;

/**
 * Created by m rosyid on 10/15/2015.
 */
public class AlarmReceiverMakan extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Waktunya Makan", Toast.LENGTH_LONG).show();
        MediaPlayer player = MediaPlayer.create(context, R.raw.tone);
        player.start();
        intent = new Intent();
        intent.setClass(context, ReminderMakan.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
