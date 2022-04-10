package com.example.space.register;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.space.R;
import com.example.space.model.RegisterViewModel;
import com.google.android.material.textfield.TextInputLayout;


public class EnterEmail extends Fragment {

    Button next_enterPassword;
    EditText enterEmail;
    TextInputLayout enterEmailLayout;
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
        return inflater.inflate(R.layout.fragment_enter_email, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        next_enterPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEmail();
                enterEmail.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        hideError();
                    }
                });
            }
        });
    }

    private void init(View view)
    {
        next_enterPassword = view.findViewById(R.id.next_enterPassword);
        enterEmail = view.findViewById(R.id.enterEmail);
        enterEmailLayout = view.findViewById(R.id.enterEmailLayout);
        navController = Navigation.findNavController(view);
    }

    private void checkEmail()
    {
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(enterEmail.getText().toString()).matches()) {
            enterEmailLayout.setError("Invalid email");
            check = false;
        }
        else{
            model.getEmail().setValue(enterEmail.getText().toString().trim());
            navController.navigate(R.id.action_enterEmail_to_enterPassword);
        }

    }

    private void hideError(){
        if(check==false) {
            enterEmailLayout.setError("");
            check=true;
        }
    }


}