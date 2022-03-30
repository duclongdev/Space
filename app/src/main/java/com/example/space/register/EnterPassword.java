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

public class EnterPassword extends Fragment {


    Button next_enterBirthday;
    EditText enterPassword;
    TextInputLayout enterPasswordLayout;
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

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(check==false && enterPassword.getText().toString().length() >= 8)
                    enterPasswordLayout.setError("");
            }
        });
    }


    private void init(View view)
    {
        next_enterBirthday = view.findViewById(R.id.next_enterBirthday);
        navController = Navigation.findNavController(view);
        enterPassword = view.findViewById(R.id.enterPassword);
        enterPasswordLayout = view.findViewById(R.id.enterPasswordLayout);
    }

    private void checkPassword()
    {
        if(enterPassword.getText().toString().length() < 8)
        {
            enterPasswordLayout.setError("At least 8 characters");
            check = false;
        }else {
            model.getPassWord().setValue(enterPassword.getText().toString());
            navController.navigate(R.id.action_enterPassword_to_enterAge);
        }
    }


}