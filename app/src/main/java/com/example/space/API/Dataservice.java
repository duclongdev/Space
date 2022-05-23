package com.example.space.API;

import com.example.space.home.advSlide.Advertisement;
import com.example.space.model.Artist;
import com.example.space.model.Genre;
import com.example.space.model.Song;
import com.example.space.model.Theme;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Dataservice {
    @GET("searchsong.php")
    Call<List<Song>> getSong(@Query("titleSong") String title);
    @GET("song.php")
    Call<List<Song>> getRecommendSong();
    @GET("theme.php")
    Call<List<Theme>> getTheme();
    @GET("banner.php")
    Call<List<Advertisement>> getBanner();
    @GET("artist.php")
    Call<List<Artist>> getArtist();
    @GET("genre.php")
    Call<List<Genre>> getGenre();
    @GET("songGenre.php")
    Call<List<Song>>getSongGenre(@Query("genre") String genre);
    @GET("songArtist.php")
    Call<List<Song>>getSongArtist(@Query("artist") String artist);
    @GET("songTheme.php")
    Call<List<Song>>getSongTheme(@Query("theme") String theme);
    @GET("songBanner.php")
    Call<List<Song>>getSongBanner(@Query("banner") String banner);
    @GET("userFavorite.php")
    Call<List<Song>>getSongFavorite(@Query("idUser") String idUser);
    @GET("checkFavorite.php")
    Call<String>checkFavorite(@Query("idUser") String idUser,@Query("idSong") String idSong);
    @FormUrlEncoded
    @POST("addFavorite.php")
    Call<String>addFavorite(@Field("idFavorite") String idFavorite,@Field("idUser") String idUser, @Field("idSong") String idSong);
    @FormUrlEncoded
    @POST("removeFavorite.php")
    Call<String>removeFavorite(@Field("idUser") String idUser,@Field("idSong") String idSong);
}
