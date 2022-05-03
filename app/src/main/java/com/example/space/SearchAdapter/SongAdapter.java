package com.example.space.SearchAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.space.R;
import com.example.space.model.Genre;
import com.example.space.model.Song;
import com.example.space.myInterface.IClickSearch;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {
    private Fragment mContext;
    private List<Song> list_song;
    private IClickSearch iClickSearch;
    public SongAdapter(Fragment mContext, List<Song> list_song, IClickSearch iClickSearch) {
        this.mContext = mContext;
        this.list_song = list_song;
        this.iClickSearch=iClickSearch;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_song, parent, false);
        SongAdapter.ViewHolder viewHolder = new SongAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (list_song == null)
            return;
        Song a=list_song.get(position);
        int index=position;
        Glide.with(mContext).load(list_song.get(position).getLinkImageS()).centerCrop().into(holder.imageView);
        holder.t_s.setText(list_song.get(position).getTitleSong());
        holder.a_s.setText(list_song.get(position).getName());
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickSearch.onClickSearch(a,index);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_song.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView t_s,a_s;
        private LinearLayout item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageS);
            t_s=itemView.findViewById(R.id.title);
            a_s=itemView.findViewById(R.id.artist);
            item=itemView.findViewById(R.id.item_song);
        }
    }
}
