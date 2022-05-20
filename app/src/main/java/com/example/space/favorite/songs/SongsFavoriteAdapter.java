package com.example.space.favorite.songs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.space.R;
import com.example.space.SearchAdapter.SongAdapter;
import com.example.space.detailPlayllist.SongListAdapter;
import com.example.space.model.Song;
import com.example.space.myInterface.IClickOnMoreOptionOfSongItem;
import com.example.space.myInterface.IClickSong;

import java.util.List;

public class SongsFavoriteAdapter extends RecyclerView.Adapter<SongsFavoriteAdapter.SongFavoriteViewHolder>{

    private List<Song> songListFvr;
    private Fragment fragment;
    private IClickSong iClickSong;
    public SongsFavoriteAdapter(Fragment fragment, IClickSong iClickSong) {
        this.fragment = fragment;
        this.iClickSong = iClickSong;
    }

    public void setData(List<com.example.space.model.Song> songList){
        this.songListFvr = songList;
    }
    @NonNull
    @Override
    public SongFavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite_song, parent, false);
        return new SongsFavoriteAdapter.SongFavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongFavoriteViewHolder holder, int position) {
        Song song = songListFvr.get(position);
        if(song == null) return;
        Glide.with(fragment).load(song.getLinkImageS()).centerCrop().into(holder.songImg);
        holder.songName.setText(song.getTitleSong());
        holder.artistName.setText(song.getName());
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
        if(songListFvr != null)
            return songListFvr.size();
        return 0;
    }

    public class SongFavoriteViewHolder extends RecyclerView.ViewHolder {
        ImageView songImg;
        TextView songName;
        TextView artistName;
        ImageButton loveBtn;
        LinearLayout clickSong;
        public SongFavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            songImg = itemView.findViewById(R.id.song_img_fvr);
            songName = itemView.findViewById(R.id.song_name_fvr);
            artistName = itemView.findViewById(R.id.song_artist_fvr);
            loveBtn = itemView.findViewById(R.id.love_fvr);
            clickSong = itemView.findViewById(R.id.click_song_fvr);
        }
    }
}
