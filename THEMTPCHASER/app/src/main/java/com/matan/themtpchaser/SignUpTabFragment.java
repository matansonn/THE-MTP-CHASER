package com.matan.themtpchaser;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpTabFragment extends Fragment {
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_sign_up_tab_fragment,container, false);

       mAuth = FirebaseAuth.getInstance();

        EditText email = root.findViewById(R.id.su_email);
        EditText emailcon = root.findViewById(R.id.su_emailcon);
        EditText pass = root.findViewById(R.id.su_pass);
        EditText passcon = root.findViewById(R.id.su_passcon);
        Button signup_button = root.findViewById(R.id.signup_button);

        email.setTranslationX(800);
        emailcon.setTranslationX(800);
        pass.setTranslationX(800);
        passcon.setTranslationX(800);
        signup_button.setTranslationX(800);

        email.setAlpha(0);
        emailcon.setAlpha(0);
        pass.setAlpha(0);
        passcon.setAlpha(0);
        signup_button.setAlpha(0);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        emailcon.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        passcon.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        signup_button.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //בדיקת תקינות קלט

                mAuth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(root.getContext()," Sign Up Success", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Tag", e.toString());
                        Toast.makeText(root.getContext(), "Failed To Create An Account", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return root;


    }
    public boolean check(String s, ViewGroup root){
        if (!(s.length()>=3) && (s.length()<=20)){
            Toast.makeText(root.getContext(), "Your passward isn't in the right range of characters" , Toast.LENGTH_SHORT).show();
            return false;}
        return true;
    }



}
