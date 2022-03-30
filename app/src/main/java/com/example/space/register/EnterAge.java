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
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.space.R;
import com.example.space.model.RegisterViewModel;

import java.util.Calendar;


public class EnterAge extends Fragment {

    Button next_enterGender;
    DatePicker chooseBirthday;
    long age = 0;
    TextView yourAge;
    RegisterViewModel model;
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
        return inflater.inflate(R.layout.fragment_enter_age, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        next_enterGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(setAge())
                    navController.navigate(R.id.action_enterAge_to_enterGender);
            }
        });

    }

    public void init(View view)
    {
        next_enterGender = view.findViewById(R.id.next_enterGender);
        navController = Navigation.findNavController(view);
        chooseBirthday = view.findViewById(R.id.chooseBirthday);
        yourAge = view.findViewById(R.id.yourAge);
        chooseBirthday.setMaxDate(Calendar.getInstance().getTimeInMillis());
    }

    boolean setAge()
    {
        Calendar instance = Calendar.getInstance();
        int year = chooseBirthday.getYear();
        int Year = instance.get(Calendar.YEAR);
        int Age = Year - year;
        if(Age < 12)
        {
            yourAge.setText("Looks like you made the wrong choice");
            yourAge.setTextColor(this.getResources().getColor(R.color.red));
            return false;
        }
        int month = chooseBirthday.getMonth() ;
        int day = chooseBirthday.getDayOfMonth();
        month+=1;
        String date = String.format("%d / %d / %d", day, month, year);
        model.getAge().setValue(date);
        return true;
    }
}