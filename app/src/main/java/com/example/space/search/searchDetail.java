package com.example.space.search;

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
import com.example.space.SearchAdapter.ThemeAdapter;
import com.example.space.model.Theme;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class searchDetail extends Fragment {
    RecyclerView list_theme;
    ThemeAdapter themeAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_search_detail, container, false);
        list_theme=v.findViewById(R.id.l_theme);
        Dataservice dataservice= APIService.getService();
        Call<List<Theme>> call= dataservice.getTheme();
        call.enqueue(new Callback<List<Theme>>() {
            @Override
            public void onResponse(Call<List<Theme>> call, Response<List<Theme>> response) {
                List<Theme> mang=(ArrayList<Theme>) response.body();
                themeAdapter=new ThemeAdapter(requireContext(),mang);
                list_theme.setAdapter(themeAdapter);
                list_theme.setLayoutManager(new GridLayoutManager(requireActivity(),2));
                list_theme.setHasFixedSize(true);
            }
            @Override
            public void onFailure(Call<List<Theme>> call, Throwable t) {

            }
        });
        return v;
    }
}