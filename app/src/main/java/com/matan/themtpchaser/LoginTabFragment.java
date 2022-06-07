package com.matan.themtpchaser;

import android.content.Context;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginTabFragment extends Fragment {
    private FirebaseAuth mAuth;
    private Object LoginTabFragment;
    boolean valid;
    Button login_button;
    EditText email, pass;
    @Override
    public void onStart() {
        //הפעלת חיבור הFireBase
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    //    if ((currentUser != null)) {
     //       startActivity(new Intent((Context) LoginTabFragment, HomeActivity.class));
      //  }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //פעולה ראשית בה יופעו פעולות שייפורטו בהמשך
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_login_tab_fragment, container, false);

        mAuth = FirebaseAuth.getInstance();

        EditText email = root.findViewById(R.id.login_email);
        EditText pass = root.findViewById(R.id.login_pass);
        Button login_button = root.findViewById(R.id.login_button);


        email.setTranslationX(800);
        pass.setTranslationX(800);
        login_button.setTranslationX(800);

        email.setAlpha(0);
        pass.setAlpha(0);
        login_button.setAlpha(0);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        login_button.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //בדיקת אימות פרטים של המשתמש מול ה - FireBase
                checkField(email);
                checkField(pass);
                if(valid){
                    mAuth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            //אם מוצלח מעבר לדף הבית
                            
                            Toast.makeText(getContext(), "logged in successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getContext() ,HomeActivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("Tag", e.toString());
                            Toast.makeText(root.getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    });

                }
            }
        });

        return root;
    }
    //בדיקת אימות נתונים
    public boolean checkField(EditText textField){
        if(textField.getText().toString().isEmpty()){
            textField.setError("Error");
            valid = false;
        } else {
            valid = true;
        }
        return valid;
    }
}
