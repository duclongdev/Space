package com.example.space.API;

import com.example.space.model.Song;
import com.example.space.model.Theme;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Dataservice {
    @GET("song.php")
    Call<List<Song>> getSong();
    @GET("theme.php")
    Call<List<Theme>> getTheme();
}
