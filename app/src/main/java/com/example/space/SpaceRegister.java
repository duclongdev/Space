package com.example.space;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

public class SpaceRegister extends AppCompatActivity {

    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_register);
        FragmentManager fragmentManager = getSupportFragmentManager();
        NavHostFragment hi = (NavHostFragment)fragmentManager
                .findFragmentById(R.id.nav_host_fragment_register);
        navController = hi.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public boolean onSupportNavigateUp()
    {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}