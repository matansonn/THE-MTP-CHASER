package com.matan.themtpchaser;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new CountDownTimer(5000, 1000) {

            @Override
            public void onTick(long l) {
                Toast.makeText(getApplicationContext(), "Loading...("+ (l/1000)+ ")",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
                Intent go = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(go);

            }
        }.start();
    }
}