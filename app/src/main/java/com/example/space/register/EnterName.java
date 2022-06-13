package com.example.space.register;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.space.Home;
import com.example.space.R;
import com.example.space.login.Sign_in;
import com.example.space.model.RegisterViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class EnterName extends Fragment {
    private ProgressDialog progressDialog;
    Button done_backToLogin;
    private RegisterViewModel model;
    EditText enterName;
    TextInputLayout enterNameLayout;
    NavController navController;
    boolean checkEmpty = true;
    boolean checkLength = true;
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(requireActivity()).get(RegisterViewModel.class);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_enter_name, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        String email=model.getEmail().getValue();
        String password=model.getPassWord().getValue();
        done_backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.getName().setValue(enterName.getText().toString());
                if(checkEnterName())
                createAccount(email,password);
            }
        });

        enterName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
               enterNameLayout.setError("");
            }
        });
        enterName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getAction()==KeyEvent.ACTION_DOWN&&(i == KeyEvent.KEYCODE_ENTER)){
                    updateUI();
                    return true;
                }
                return false;
            }
        });
        enterName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    hideKeyboard(view);
                }
            }
        });
    }
    private void init(View view)
    {
        done_backToLogin = view.findViewById(R.id.done_backToLogin);
        enterName = view.findViewById(R.id.enterName);
        enterNameLayout = view.findViewById(R.id.enterNameLayout);
        navController = Navigation.findNavController(view);
    }

    private boolean checkEnterName()
    {
        if(enterName.getText().length() <= 2)
        {
            enterNameLayout.setError("Please enter your real name");
            return false;
        }
        else if(!checkName(enterName.getText().toString())){
            enterNameLayout.setError("Please enter your real name");
            return false;
        }
        else
            return true;
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }

    private void reload() {
    }

    private void createAccount(String email, String password) {
        String name=model.getName().getValue();
        String age=model.getAge().getValue();
        progressDialog=new ProgressDialog(requireActivity(),R.style.MyAlertDialogStyle);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Waiting for loading...");
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User users=new User(name,age,email);
                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).setValue(users);
                            progressDialog.hide();
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            currentUser.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(requireActivity(),"User registered successfully. Please verify email !",Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                            updateUI();
                        } else {
                            progressDialog.hide();
                        }
                    }
                });
    }
    public static String removeAccent(String s) { String temp = Normalizer.normalize(s, Normalizer.Form.NFD); Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+"); temp = pattern.matcher(temp).replaceAll("");
        return temp.replaceAll("đ", "d"); }
    public static boolean checkName( String name ) {
        return removeAccent(name).matches( "^[a-zA-Z ]{2,}$" );
    }
    private void updateUI() {
        navController.navigate(R.id.action_enterName_to_sign_in);
    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}