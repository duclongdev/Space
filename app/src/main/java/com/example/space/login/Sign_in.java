package com.example.space.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.space.Home;
import com.example.space.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class Sign_in extends Fragment {
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private ImageButton btn_back;
    private TextView forget_pass;
    private EditText email;
    private EditText password;
    private Button btn_sign_in;
    private TextInputLayout email_layout;
    private TextInputLayout pass_layout;

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_sign_in, container, false);
        Init(v);
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
                NavHostFragment.findNavController(Sign_in.this).navigate(R.id.action_sign_in_to_method_Sign_in);
            }
        });
        // chuyển đến màn hình quên mật khẩu
        forget_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Sign_in.this).navigate(R.id.action_sign_in_to_forgot_pass);
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
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    hideKeyboard(view);
                }
            }
        });
        email.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getAction()==KeyEvent.ACTION_DOWN&&(i == KeyEvent.KEYCODE_ENTER)){
                    password.setFocusable(true);
                    return true;
                }
                return false;
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
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    hideKeyboard(view);
                }
            }
        });
        password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getAction()==KeyEvent.ACTION_DOWN&&(i == KeyEvent.KEYCODE_ENTER)){
                    if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                        email_layout.setError("Invalid email");
                        return true;
                    }
                    if(password.getText().toString().length() < 8){
                        pass_layout.setError("At least 8 characters");
                        return true;
                    }
                    SignIn(email.getText().toString().trim(),password.getText().toString().trim());
                    return true;
                }
                return false;
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
                        progressDialog.hide();
                        if (task.isSuccessful()) {
                            if(FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()){
                                Intent intent=new Intent(requireActivity(),Home.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(requireActivity(),"Please verify your email !",Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(requireActivity(),"Your email or password is wrong !",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    //cập nhật lại giao diện
    private void updateUI(FirebaseUser currentUser) {

    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}