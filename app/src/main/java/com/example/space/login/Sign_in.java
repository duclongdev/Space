package com.example.space.login;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.example.space.Home;
import com.example.space.LoginScreen;
import com.example.space.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Sign_in extends Fragment {
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private ImageButton btn_back;
    private Button forget_pass;
    private EditText email;
    private EditText password;
    private Button btn_sign_in;
    private TextInputLayout email_layout;
    private TextInputLayout pass_layout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_sign_in, container, false);
        return v;
    }
    private void Init(View v){
        mAuth = FirebaseAuth.getInstance();
        email=v.findViewById(R.id.enterEmail);
        password=v.findViewById(R.id.enterPassword);
        btn_sign_in=v.findViewById(R.id.btn_sign_in);
        email_layout=v.findViewById(R.id.inputEmailLayout);
        pass_layout=v.findViewById(R.id.inputPasswordLayout);
        forget_pass=v.findViewById(R.id.forget_pass);
        btn_back=v.findViewById(R.id.btn_back_main_sign_in);
        // trở về màn hình trước đó
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        // chuyển đến màn hình quên mật khẩu
        forget_pass.setOnClickListener(new View.OnClickListener() {
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
                email_layout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                email_layout.setError(null);
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    email_layout.setError("Invalid email");
                    return;
                }
                if(password.getText().toString().length() < 8){
                    pass_layout.setError("At least 8 characters");
                    return;
                }
                SignIn(email.getText().toString().trim(),password.getText().toString().trim());
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){

        }
    }

    private void SignIn(String email,String password){
        progressDialog=new ProgressDialog(requireActivity(),R.style.MyAlertDialogStyle);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Waiting for loading...");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.hide();
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            updateUI(currentUser);
                        } else {
                            progressDialog.hide();
                        }
                    }
                });
    }
    //cập nhật lại giao diện
    private void updateUI(FirebaseUser currentUser) {

    }
}