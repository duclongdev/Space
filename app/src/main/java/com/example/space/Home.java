package com.example.space;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.space.detailPlayllist.PlaylistScreen;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class Home extends AppCompatActivity {
    //final Fragment homeScreen = new HomeScreen();
    final Fragment homeScreen = new PlaylistScreen();
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
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}