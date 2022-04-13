package com.example.space.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.space.Forgotpass;
import com.example.space.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot_pass extends Fragment {
    private Button btn_forgot;
    private EditText email;
    private TextInputLayout email_layout;
    private FirebaseAuth auth;
    private ImageButton btn_back;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_forgot_pass, container, false);

        return v;
    }
    private void Init(View v){
        btn_forgot=v.findViewById(R.id.btn_forgot);
        email=v.findViewById(R.id.forgot_email);
        email_layout=v.findViewById(R.id.forgot_layout);
        btn_back=v.findViewById(R.id.btn_back_sign_in);
        auth=FirebaseAuth.getInstance();
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    email_layout.setError(null);
                }
                else {
                    email_layout.setError("Invalid email");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        btn_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    return;
                }
                auth.sendPasswordResetEmail(email.getText().toString().trim())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(requireActivity(), "thành công", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}