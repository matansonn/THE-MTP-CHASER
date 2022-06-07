package com.matan.themtpchaser;

import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView tvb;
    BatteryReceiver batteryReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //הפעלת הService ושעון 5 שניות
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvb = findViewById(R.id.tvb);
        batteryReceiver = new BatteryReceiver(tvb);
        registerReceiver(batteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        Intent music = new Intent(MainActivity.this, BackgroundMusicService.class);
        startService(music);
        new CountDownTimer(5000, 1000) {

            @Override
            public void onTick(long l) {
                //הפעלת הודעת Toast שתספור 5 שניות ותציג אותם לפני מעבר לדף ההתחברות
                Toast.makeText(getApplicationContext(), "Loading...("+ (l/1000)+ ")",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
                //מעבר בסיום שעון ה5 שניות לדף ההתחברות
                Intent go = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(go);

            }
        }.start();
    }

    @Override
    protected void onStop() {
       // שחרור משאב בדיקה - זכרון ותהליך
        super.onStop();
        unregisterReceiver(batteryReceiver);
    }
}