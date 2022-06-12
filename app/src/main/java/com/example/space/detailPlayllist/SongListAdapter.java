package com.example.space.detailPlayllist;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.space.R;
import com.example.space.model.Song;
import com.example.space.myInterface.IClickItemOnSongOption;
import com.example.space.myInterface.IClickOnMoreOptionOfSongItem;
import com.example.space.myInterface.IClickSong;

import java.util.List;

public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.SongViewHolder> {

    private List<com.example.space.model.Song> songList;
    private Fragment fragment;
    private IClickSong iClickSong;

    public SongListAdapter(Fragment fragment, IClickSong iClickSong) {
        this.fragment = fragment;
        this.iClickSong = iClickSong;
    }

    public void setData(List<com.example.space.model.Song> songList){
        this.songList = songList;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_item, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Song song = songList.get(position);
        if(song == null) return;
        Glide.with(fragment).load(song.getLinkImageS()).centerCrop().into(holder.imgSong);
        holder.songName.setText(song.getTitleSong());
        holder.songAuthor.setText(song.getName());
        holder.itemView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.load_list));
        holder.clickSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickSong.onCLickSong(song);
            }
        });
    }
    @Override
    public int getItemCount() {
       if(songList == null) return 0;
       return songList.size();
    }

    public class SongViewHolder extends RecyclerView.ViewHolder {
        ImageView imgSong;
        TextView songName;
        TextView songAuthor;
        LinearLayout clickSong;
        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSong = itemView.findViewById(R.id.song_img);
            songName = itemView.findViewById(R.id.song_name);
            songAuthor = itemView.findViewById(R.id.song_author);
            clickSong = itemView.findViewById(R.id.click_song);
        }
    }
}
