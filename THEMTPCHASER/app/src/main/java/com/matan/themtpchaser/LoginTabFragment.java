package com.matan.themtpchaser;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    CheckBox remember;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //    if ((currentUser != null)) {
        //       startActivity(new Intent((Context) LoginTabFragment, HomeActivity.class));
        //  }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_login_tab_fragment, container, false);

        mAuth = FirebaseAuth.getInstance();

        EditText email = root.findViewById(R.id.login_email);
        EditText pass = root.findViewById(R.id.login_pass);
        Button login_button = root.findViewById(R.id.login_button);
        remember = root.findViewById(R.id.rememberMe);
        SharedPreferences preferences = getContext().getSharedPreferences("checkbox", Context.MODE_PRIVATE);
        String checkbox = preferences.getString("remember", "");
        if (checkbox.equals("true")) {
            Intent go = new Intent(getActivity(), HomeActivity.class);
            Toast.makeText(root.getContext(), "true", Toast.LENGTH_SHORT).show();
            startActivity(go);
        } else if (checkbox.equals("false")) {
            Toast.makeText(root.getContext(), "Please Sign In", Toast.LENGTH_SHORT).show();
        }
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }
        });


        email.setTranslationX(800);
        pass.setTranslationX(800);
        login_button.setTranslationX(800);

        email.setAlpha(0);
        pass.setAlpha(0);
        login_button.setAlpha(0);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        login_button.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();

        remember.setOnCheckedChangeListener((compoundButton, b) -> {

            if (compoundButton.isChecked()) {
                SharedPreferences preferences1 = getContext().getSharedPreferences("checkBox", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences1.edit();
                editor.putString("remember", "true");
                editor.apply();
                Toast.makeText(root.getContext(), "Checked", Toast.LENGTH_SHORT).show();

            } else if (!compoundButton.isChecked()) {
                    SharedPreferences preferences1 = getContext().getSharedPreferences("checkBox", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences1.edit();
                    editor.putString("remember", "false");
                    editor.apply();
                    Toast.makeText(root.getContext(), "Unchecked", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}