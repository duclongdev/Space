package com.example.space;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class Forgotpass extends AppCompatActivity {
    private Button btn_forgot;
    private EditText email;
    private TextInputLayout email_layout;
    private FirebaseAuth auth;
    private ImageButton btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpass);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        btn_forgot=findViewById(R.id.btn_forgot);
        email=findViewById(R.id.forgot_email);
        email_layout=findViewById(R.id.forgot_layout);
        btn_back=findViewById(R.id.btn_back_sign_in);
        auth=FirebaseAuth.getInstance();
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
                                    Toast.makeText(Forgotpass.this, "thành công", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}