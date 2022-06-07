package com.matan.themtpchaser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //יצירת Menu על מנת לצאת מהאפליקציה, להתחבר ממשתמש אחר, להפסיק את מוזיקת הרקע ולהתחילה מחדש
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //בחירת Item
        int itemId = item.getItemId();
        if (itemId == R.id.item1) {
            AlertDialog.Builder abd = new AlertDialog.Builder(this);
            abd.setTitle("Exit");
            abd.setIcon(R.drawable.stop);
            abd.setMessage("Are you sure you want to exit the app?");
            abd.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //אם נבחרה יציאה מהאפליקציה – > כיבוי מוזיקת הרקע + יציאה מהאפליקציה.
                    Intent music = new Intent(HomeActivity.this, BackgroundMusicService.class);
                    stopService(music);
                    finishAffinity();
                }
            });
            abd.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //במקרה של ביטול יציאה, חזור לדף הבית.
                }
            });
            abd.create().show();
            return true;
        } else if (itemId == R.id.item2) {
            //אם 2 נבחר תחזור לדף כניסה.
            startActivity(new Intent(this, MainActivity.class));
            return true;
        }
        if(itemId == R.id.item3){
            //אם 3 נבחר עבור לקף ההוראות.
            startActivity(new Intent(this, InstructionsActivity.class));
            return true;
        }
        if( itemId == R.id.stopmusic) {
            //אם 3 נבחר עצור את מוזיקת הרקע.
            Intent music = new Intent(HomeActivity.this, BackgroundMusicService.class);
            stopService(music);
        }
        if( itemId == R.id.startmusic){
            //אם 4 נבחר התחל את מוזיקת הרקע.
          Intent music = new Intent(HomeActivity.this, BackgroundMusicService.class);
          startService(music);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //פעולה ראשית, הפעולה תפעיל פעולות שיפורטו בהמשך
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //התחל שאלון
                Intent intent = new Intent(HomeActivity.this, QuizActivity.class);
                startActivity(intent);
            }
        });


    }


}