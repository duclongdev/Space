package com.example.space;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.space.API.APIService;
import com.example.space.API.Dataservice;
import com.example.space.API.Song;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchScreen extends Fragment {
    private RecyclerView list_search;
    private ArrayList<Song> listSong;
    private SearchSongAdapter searchSongAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_search_screen, container, false);
        Init(v);
        return v;
    }
    private void Init(View v){
        list_search=v.findViewById(R.id.list_search);
        Dataservice dataservice= APIService.getService();
        Call<List<Song>> call=dataservice.getSong();
        call.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                listSong=(ArrayList<Song>) response.body();
                searchSongAdapter=new SearchSongAdapter(requireActivity(),listSong);
                list_search.setAdapter(searchSongAdapter);
                list_search.setLayoutManager(new LinearLayoutManager(requireActivity()));
            }
            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {

            }
        });

    }
}