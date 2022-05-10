package com.example.space.register;

import android.app.Activity;
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

import com.example.space.R;
import com.example.space.model.RegisterViewModel;
import com.google.android.material.textfield.TextInputLayout;

public class EnterPassword extends Fragment {


    Button next_enterBirthday;
    EditText enterPassword,enterConfirmPassword;
    TextInputLayout enterPasswordLayout,enterConfirmPasswordLayout;
    boolean check = true;
    private RegisterViewModel model;
    NavController navController;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(requireActivity()).get(RegisterViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_enter_password, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        next_enterBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPassword();
            }
        });

        enterPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(enterPassword.getText().toString().length() < 8){
                    enterPasswordLayout.setError("At least 8 characters");
                }
                else {
                    enterPasswordLayout.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        enterPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    hideKeyboard(view);
                }
            }
        });
        enterPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getAction()==KeyEvent.ACTION_DOWN&&(i == KeyEvent.KEYCODE_ENTER)){
                    enterConfirmPassword.setFocusable(true);
                    return true;
                }
                return false;
            }
        });
        enterConfirmPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getAction()==KeyEvent.ACTION_DOWN&&(i == KeyEvent.KEYCODE_ENTER)){
                    checkPassword();
                    return true;
                }
                return false;
            }
        });
        enterConfirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    hideKeyboard(view);
                }
            }
        });
        enterConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(enterConfirmPassword.getText().toString().length() < 8){
                    enterConfirmPasswordLayout.setError("At least 8 characters");
                }
                else {
                    enterConfirmPasswordLayout.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    private void init(View view)
    {
        next_enterBirthday = view.findViewById(R.id.next_enterBirthday);
        navController = Navigation.findNavController(view);
        enterPassword = view.findViewById(R.id.enterPassword);
        enterConfirmPassword=view.findViewById(R.id.enterConfirmPassword);
        enterPasswordLayout = view.findViewById(R.id.enterPasswordLayout);
        enterConfirmPasswordLayout=view.findViewById(R.id.enterConfirmPasswordLayout);
    }

    private void checkPassword()
    {
        if(!enterPassword.getText().toString().equals(enterConfirmPassword.getText().toString()))
        {
            enterConfirmPasswordLayout.setError("Your password and confirm password must match.");
            check = false;
        }else {
            model.getPassWord().setValue(enterPassword.getText().toString().trim());
            navController.navigate(R.id.action_enterPassword_to_enterAge);
        }
    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}