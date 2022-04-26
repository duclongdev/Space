package com.example.space;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Home extends AppCompatActivity {
    final Fragment homeScreen = new HomeScreen();
    final Fragment searchScreen = new SearchScreen();
    final Fragment favoriteScreen = new FavoriteScreen();
    final Fragment userScreen = new UserScreen();
    final FragmentManager fm = getSupportFragmentManager();
    private BottomNavigationView bottom_bar;
    Fragment active = homeScreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
//        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.homeScreen, R.id.searchScreen, R.id.favoriteScreen,R.id.userScreen)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);
        fm.beginTransaction().add(R.id.nav_host_fragment_activity_main, userScreen, "4").hide(userScreen).commit();
        fm.beginTransaction().add(R.id.nav_host_fragment_activity_main, favoriteScreen, "3").hide(favoriteScreen).commit();
        fm.beginTransaction().add(R.id.nav_host_fragment_activity_main, searchScreen, "2").hide(searchScreen).commit();
        fm.beginTransaction().add(R.id.nav_host_fragment_activity_main,homeScreen, "1").commit();
        bottom_bar=findViewById(R.id.bottom_nav);
        bottom_bar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        fm.beginTransaction().hide(active).show(homeScreen).commit();
                        active = homeScreen;
                        return true;

                    case R.id.search:
                        fm.beginTransaction().hide(active).show(searchScreen).commit();
                        active = searchScreen;
                        return true;

                    case R.id.favorite:
                        fm.beginTransaction().hide(active).show(favoriteScreen).commit();
                        active = favoriteScreen;
                        return true;
                    case R.id.user:
                        fm.beginTransaction().hide(active).show(userScreen).commit();
                        active = userScreen;
                        return true;
                }
                return false;
            }
        });
    }
}