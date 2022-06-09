package com.example.space.favorite;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.space.API.APIService;
import com.example.space.API.Dataservice;
import com.example.space.MainActivity;
import com.example.space.MusicPlayer.MusicPlayer1;
import com.example.space.R;
import com.example.space.databinding.FragmentFavoriteScreenBinding;
import com.example.space.favorite.songs.SongsFavoriteAdapter;
import com.example.space.home.HomeScreen;
import com.example.space.model.Song;
import com.example.space.myInterface.IClickFavoriteSong;
import com.example.space.myInterface.IClickSong;
import com.google.firebase.auth.FirebaseAuth;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FavoriteScreen extends Fragment {

    private String UId;
    private FragmentFavoriteScreenBinding binding;
    private SongsFavoriteAdapter songsFavoriteAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavoriteScreenBinding.inflate(inflater, container, false);
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UId = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        Glide.with(requireActivity()).load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).into(binding.avaFavorite);
        songsFavoriteAdapter = new SongsFavoriteAdapter(FavoriteScreen.this, new IClickSong() {
            @Override
            public void onCLickSong(Song song) {
                Dataservice dataservice = APIService.getService();
                MainActivity.mangsong.clear();
                Call<List<Song>> callSong = dataservice.getSongGenre(song.getIdGenre());
                callSong.enqueue(new Callback<List<Song>>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                        List<Song> songs = response.body();
                        MainActivity.mangsong.addAll(response.body());
                        MainActivity.mangsong.removeIf(n->(n.getTitleSong().equals(song.getTitleSong())));
                        MainActivity.mangsong.add(song);
//                        Bundle bundle = new Bundle();
//                        bundle.putInt("data", MainActivity.mangsong.size() - 1);
                        Intent intent=new Intent(requireActivity(), MusicPlayer1.class);
                        intent.putExtra("data",MainActivity.mangsong.size() - 1);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<List<Song>> call, Throwable t) {

                    }
                });
            }
        }, new IClickFavoriteSong() {
            @Override
            public void onCLickFavoriteSong(Song song) {
                Dataservice dataservice=APIService.getService();
                Call<String> call=dataservice.removeFavorite(FirebaseAuth.getInstance().getCurrentUser().getUid(),song.getIdSong());
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.body().equals("thanhcong")){

                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
        binding.songsFvr.setLayoutManager(linearLayoutManager);
        binding.songsFvr.setAdapter(songsFavoriteAdapter);
        setData();
    }

    void setData(){
        Dataservice dataservice = APIService.getService();
        Call<List<Song>> call = dataservice.getSongFavorite(UId);
        call.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                List<Song> songs = response.body();
                songsFavoriteAdapter.setData(songs);
                songsFavoriteAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {

            }
        });
    }
}