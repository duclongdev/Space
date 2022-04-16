package com.example.space;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.space.API.Song;

import java.util.ArrayList;
import java.util.List;

public class SearchSongAdapter extends RecyclerView.Adapter<SearchSongAdapter.ViewHolder> {
    private ArrayList<Song> ListSong;
    private Activity activity;
    public SearchSongAdapter(Activity activity,ArrayList<Song> listSong){
        this.activity=activity;
        this.ListSong=listSong;
    }
    @NonNull
    @Override
    public SearchSongAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View itemView = inflater.inflate(R.layout.item_search,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchSongAdapter.ViewHolder holder, int position) {
        Song song=ListSong.get(position);
        Glide.with(activity).load(song.getLinkImage()).centerCrop().into(holder.image);
        holder.title.setText(song.getTitleSong());
        holder.artist.setText(song.getIdArtist());
    }

    @Override
    public int getItemCount() {
        return ListSong.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView title,artist;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.img_song);
            title=itemView.findViewById(R.id.title_song);
            artist=itemView.findViewById(R.id.artist_song);
        }
    }
}
