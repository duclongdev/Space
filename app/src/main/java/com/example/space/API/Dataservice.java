package com.example.space.API;

import com.example.space.model.Genre;
import com.example.space.model.Song;
import com.example.space.model.Theme;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Dataservice {
    @GET("searchsong.php")
    Call<List<Song>> getSong(@Query("titleSong") String title);
    @GET("song.php")
    Call<List<Song>> getRecommendSong();
    @GET("theme.php")
    Call<List<Theme>> getTheme();
    @GET("genre.php")
    Call<List<Genre>> getGenre();
}
