package com.matan.themtpchaser;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

public class BatteryReceiver extends BroadcastReceiver {
    TextView tvb;
    BatteryReceiver(TextView tvb){
    this.tvb = tvb;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        //החזרת אחוז הסוללה
    int precentage = intent.getIntExtra("level", 0);
    if (precentage != 0){
        tvb.setText(precentage +"%");
        }
    }
}
