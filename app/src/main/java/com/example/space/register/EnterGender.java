package com.example.space.register;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.space.R;
import com.example.space.model.RegisterViewModel;


public class EnterGender extends Fragment {

    private Button next_enterName;
    private RadioGroup gender;
    private RadioButton male;
    private RadioButton female;
    private RadioButton other;
    NavController navController;
    private RegisterViewModel model;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(requireActivity()).get(RegisterViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_enter_gender, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        next_enterName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(male.isChecked()) model.getGender().setValue("male");
                else if(female.isChecked()) model.getGender().setValue("female");
                else if(other.isChecked()) model.getGender().setValue("other");
                navController.navigate(R.id.action_enterGender_to_enterName);
            }
        });
    }

    private void init(View view)
    {
        next_enterName = view.findViewById(R.id.next_enterName);
        male = view.findViewById(R.id.male);
        female = view.findViewById(R.id.female);
        other = view.findViewById(R.id.other);
        navController = Navigation.findNavController(view);
    }

}