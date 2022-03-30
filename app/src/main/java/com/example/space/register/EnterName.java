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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EnterName extends Fragment {

    Button done_backToLogin;
    private RegisterViewModel model;
    EditText enterName;
    TextInputLayout enterNameLayout;
    NavController navController;
    boolean checkEmpty = true;
    boolean checkLength = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(requireActivity()).get(RegisterViewModel.class);
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
        done_backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEnterName();
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
    }

    private void init(View view)
    {
        done_backToLogin = view.findViewById(R.id.done_backToLogin);
        enterName = view.findViewById(R.id.enterName);
        enterNameLayout = view.findViewById(R.id.enterNameLayout);
        navController = Navigation.findNavController(view);
    }

    private void checkEnterName()
    {
        if(enterName.getText().length() <= 2)
        {
            enterNameLayout.setError("Please enter your real name");
        }
    }


}