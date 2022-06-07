package com.matan.themtpchaser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    private TextView score;
    private Button done, share;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //הפעולה הראשית, בפעולה זאת יופעלו פעולות אשר יפורטו בהמשך.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        score = findViewById(R.id.sa_score);
        done = findViewById(R.id.sa_done);
        share = findViewById(R.id.sa_share);

        String score_str = getIntent().getStringExtra("SCORE");
        score.setText(score_str);



        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //בלחיצה על הכפתור DONE האפליקציה תחזור חזרה לדף הבית
                Intent intent = new Intent(ScoreActivity.this, HomeActivity.class);
                ScoreActivity.this.startActivity(intent);
                ScoreActivity.this.finish();

            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //בלחיצה על כפתור שיתוף האפליקציה תשתף את תוצאתך
                //הIntent פונה לActivity שבמערכת
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                String message = "I have solved an MTP CHASER trivia\n"+ score_str;
                share.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(share);
            }
        });



    }
}