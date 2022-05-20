package com.example.space.favorite;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.space.R;
import com.example.space.databinding.FragmentFavoriteScreenBinding;
import com.example.space.favorite.songs.SongsFavoriteAdapter;
import com.example.space.model.Song;
import com.example.space.myInterface.IClickSong;


public class FavoriteScreen extends Fragment {


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
        songsFavoriteAdapter = new SongsFavoriteAdapter(FavoriteScreen.this, new IClickSong() {
            @Override
            public void onCLickSong(Song song) {

            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
        binding.songsFvr.setLayoutManager(linearLayoutManager);
        binding.songsFvr.setAdapter(songsFavoriteAdapter);
        songsFavoriteAdapter.setData(setData());
    }

    void setData(){
    }
}