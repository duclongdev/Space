package com.example.space;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import android.os.Bundle;
import android.util.Log;

import com.example.space.API.APIService;
import com.example.space.API.Dataservice;
import com.example.space.model.Song;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private ArrayList<Song> mangsong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        NavHostFragment hi = (NavHostFragment)fragmentManager.findFragmentById(R.id.nav_host_fragment_register);
        navController = hi.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Dataservice dataservice= APIService.getService();
        Call<List<Song>> call=dataservice.getSong();
        call.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                mangsong=(ArrayList<Song>) response.body();
                for (int i=0;i<mangsong.size();i++){
                    Log.d("ddd",mangsong.get(i).getTitleSong());
                }
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {

            }
        });
    }
    public boolean onSupportNavigateUp()
    {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}