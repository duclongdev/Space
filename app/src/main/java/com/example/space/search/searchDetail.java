package com.example.space.search;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.space.API.APIService;
import com.example.space.API.Dataservice;
import com.example.space.R;
import com.example.space.SearchAdapter.GenreAdapter;
import com.example.space.SearchAdapter.ThemeAdapter;
import com.example.space.model.Genre;
import com.example.space.model.Theme;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class searchDetail extends Fragment {
    RecyclerView list_theme,list_genre;
    ThemeAdapter themeAdapter;
    GenreAdapter genreAdapter;
    ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_search_detail, container, false);
        progressDialog=new ProgressDialog(requireActivity(),R.style.MyAlertDialogStyle);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Waiting for loading...");
        progressDialog.show();
        list_theme=v.findViewById(R.id.l_theme);
        list_genre=v.findViewById(R.id.l_genre);
        Dataservice dataservice= APIService.getService();
        Call<List<Theme>> callTheme= dataservice.getTheme();
        callTheme.enqueue(new Callback<List<Theme>>() {
            @Override
            public void onResponse(Call<List<Theme>> call, Response<List<Theme>> response) {
                List<Theme> themes=(ArrayList<Theme>) response.body();
                themeAdapter=new ThemeAdapter(requireContext(),themes);
                list_theme.setAdapter(themeAdapter);
                list_theme.setLayoutManager(new GridLayoutManager(requireActivity(),2));
                list_theme.setHasFixedSize(true);
            }
            @Override
            public void onFailure(Call<List<Theme>> call, Throwable t) {

            }
        });
        Call<List<Genre>> callGenre= dataservice.getGenre();
        callGenre.enqueue(new Callback<List<Genre>>() {
            @Override
            public void onResponse(Call<List<Genre>> call, Response<List<Genre>> response) {
                List<Genre> genres=(ArrayList<Genre>) response.body();
                genreAdapter=new GenreAdapter(requireContext(),genres);
                list_genre.setAdapter(genreAdapter);
                list_genre.setLayoutManager(new GridLayoutManager(requireActivity(),2));
                list_genre.setHasFixedSize(true);
                progressDialog.hide();
            }
            @Override
            public void onFailure(Call<List<Genre>> call, Throwable t) {

            }
        });
        return v;
    }
}