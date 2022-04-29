package com.example.space.SearchAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.space.R;
import com.example.space.model.Genre;
import com.example.space.model.Song;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {
    private Context mContext;
    private List<Song> list_song;
    public SongAdapter(Context mContext, List<Song> list_genre) {
        this.mContext = mContext;
        this.list_song = list_song;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_song, parent, false);
        SongAdapter.ViewHolder viewHolder = new SongAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(mContext).load(list_song.get(position).getLinkImage()).centerCrop().into(holder.imageView);
        holder.t_s.setText(list_song.get(position).getTitleSong());
        holder.a_s.setText(list_song.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list_song.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView t_s,a_s;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.i_s);
            t_s=itemView.findViewById(R.id.t_s);
            a_s=itemView.findViewById(R.id.a_s);
        }
    }
}
