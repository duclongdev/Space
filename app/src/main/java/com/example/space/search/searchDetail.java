package com.example.space.search;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import com.example.space.API.APIService;
import com.example.space.API.Dataservice;
import com.example.space.MainActivity;
import com.example.space.MusicPlayer.MusicPlayer1;
import com.example.space.R;
import com.example.space.SearchAdapter.SongAdapter;
import com.example.space.model.Song;
import com.example.space.myInterface.IClickSearch;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class searchDetail extends Fragment {
    RecyclerView list_song;
    SongAdapter songAdapter;
    SearchView searchView;
    List<Song> songs;
    TextView text,not_find;
    ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_search_detail, container, false);
        searchView=v.findViewById(R.id.search_bar);
        list_song=v.findViewById(R.id.l_s);
        text=v.findViewById(R.id.text1);
        not_find=v.findViewById(R.id.not_find);
        progressDialog=new ProgressDialog(requireActivity(),R.style.MyAlertDialogStyle);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Waiting for loading...");
        progressDialog.show();
        Dataservice dataservice= APIService.getService();
        Call<List<Song>> callRecommendSong=dataservice.getRecommendSong();
        callRecommendSong.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                MainActivity.mangsong = (ArrayList<Song>) response.body();
                songs=response.body();
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                list_song.setLayoutManager(linearLayoutManager);
                songAdapter=new SongAdapter(searchDetail.this, songs, new IClickSearch() {
                    @Override
                    public void onClickSearch(Song song,int index) {
//                        Bundle bundle=new Bundle();
//                        bundle.putInt("data",index);
                        Intent intent = new Intent(getContext(), MusicPlayer1.class);
                        intent.putExtra("data", index);
                        startActivity(intent);
//                        NavHostFragment.findNavController(searchDetail.this).navigate(R.id.action_searchDetail2_to_musicPlayer,bundle);
                    }
                });
                LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.layout_animation_loadlist);
                list_song.setLayoutAnimation(layoutAnimationController);
                list_song.setAdapter(songAdapter);
                progressDialog.hide();
            }
            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {

            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressDialog.show();
                Call<List<Song>> callSong=dataservice.getSong(query);
                callSong.enqueue(new Callback<List<Song>>() {
                    @Override
                    public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                        songs=response.body();
                        if(songs.size()>0)
                        {
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                            list_song.setLayoutManager(linearLayoutManager);
                            songAdapter=new SongAdapter(searchDetail.this, songs, new IClickSearch() {
                                @Override
                                public void onClickSearch(Song song,int index) {
                                    progressDialog.show();
                                    MainActivity.mangsong.clear();
                                    Call<List<Song>> callSong1=dataservice.getSongGenre(song.getIdGenre());
                                    callSong1.enqueue(new Callback<List<Song>>() {
                                        @RequiresApi(api = Build.VERSION_CODES.N)
                                        @Override
                                        public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                                            MainActivity.mangsong.addAll(response.body());
                                            MainActivity.mangsong.removeIf(n->(n.getTitleSong().equals(song.getTitleSong())));
                                            MainActivity.mangsong.add(song);
//                                            Bundle bundle=new Bundle();
//                                            bundle.putInt("data",MainActivity.mangsong.size()-1);
                                            progressDialog.hide();
                                            Intent intent=new Intent(requireActivity(), MusicPlayer1.class);
                                            intent.putExtra("data",MainActivity.mangsong.size() - 1);
                                            startActivity(intent);
//                                            NavHostFragment.findNavController(searchDetail.this).navigate(R.id.action_searchDetail2_to_musicPlayer,bundle);
                                        }

                                        @Override
                                        public void onFailure(Call<List<Song>> call, Throwable t) {

                                        }
                                    });
                                }
                            });
                            LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.layout_animation_loadlist);
                            list_song.setLayoutAnimation(layoutAnimationController);
                            list_song.setAdapter(songAdapter);
                            text.setVisibility(View.GONE);
                            not_find.setVisibility(View.GONE);
                            list_song.setVisibility(View.VISIBLE);
                            progressDialog.hide();
                        }
                        else{
                            not_find.setVisibility(View.VISIBLE);
                            text.setVisibility(View.GONE);
                            list_song.setVisibility(View.GONE);
                            progressDialog.hide();
                        }
                    }
                    @Override
                    public void onFailure(Call<List<Song>> call, Throwable t) {

                    }
                });
                return  false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return v;
    }
    private void getSongs() {
        Dataservice dataservice = APIService.getService();
        Call<List<Song>> call = dataservice.getRecommendSong();
        call.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                MainActivity.mangsong = (ArrayList<Song>) response.body();
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {

            }
        });
    }
}