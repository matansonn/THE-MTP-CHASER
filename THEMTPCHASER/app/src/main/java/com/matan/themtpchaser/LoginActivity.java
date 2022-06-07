package com.matan.themtpchaser;


import static java.lang.Thread.sleep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    private FirebaseAuth mAuth;
    private TextView tvAni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        tvAni = findViewById(R.id.tvAni);

        Typeface typeface = ResourcesCompat.getFont(this, R.font.dela_one);
        tvAni.setTypeface(typeface);
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.myanim);
        tvAni.setAnimation(anim);
        new Thread(new Runnable() {
            @Override
            public void run() {
//                try {
//                    sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        }).start();


        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.addTab(tabLayout.newTab().setText("Signup"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final LoginAdapter adapter = new LoginAdapter(
                getSupportFragmentManager(),
                this,
                tabLayout.getTabCount()
        );
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setTranslationY(300);
        tabLayout.setAlpha(0);
        tabLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();

    }
    public boolean check(String s){
        if (!(s.length()>=3) && (s.length()<=20)){
            Toast.makeText(LoginActivity.this, "Your passward isn't in the right range of characters" , Toast.LENGTH_SHORT).show();
            return false;}
        return true;
    }

    public void signUp(View view){
        EditText emailEt = findViewById(R.id.su_email);
        EditText passEt = findViewById(R.id.su_pass);
        mAuth.createUserWithEmailAndPassword(emailEt.getText().toString() , passEt.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(LoginActivity.this ,HomeActivity.class));
                }
                else{
                    Toast.makeText(LoginActivity.this , "Sign Up Failed :(" , Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public  void login(View view){
        EditText emailEt = findViewById(R.id.login_email);
        EditText passEt = findViewById(R.id.login_pass);
        mAuth.signInWithEmailAndPassword(emailEt.getText().toString() , passEt.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(LoginActivity.this ,HomeActivity.class));
                }
                else{
                    Toast.makeText(LoginActivity.this , "Login Failed :(" , Toast.LENGTH_LONG).show();
                }
            }
       });

    }
}
