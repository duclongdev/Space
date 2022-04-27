package com.example.space.home.playLists.playlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.space.R;
import com.example.space.myInterface.IClickPlayList;

import java.util.List;

public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.PlayListViewHolder> {

    public PlayListAdapter( Fragment fragment, IClickPlayList iClickPlayList) {
        this.fragment = fragment;
        this.iClickPlayList = iClickPlayList;
    }

    private List<PlayList> playLists;
    private Fragment fragment;
    private IClickPlayList iClickPlayList;

    @NonNull
    @Override
    public PlayListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.play_list_item, parent, false);
        return new PlayListViewHolder(view);
    }

    public void getData(List<PlayList> playLists) {
        this.playLists = playLists;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull PlayListViewHolder holder, int position) {
        PlayList playList = playLists.get(position);
        if (playList == null)
            return;
        holder.playListName.setText(playList.getName());
        Glide.with(fragment).load(playList.getUrl()).centerCrop().into(holder.playListImg);
        holder.clickPlayList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickPlayList.onClickPlayList(playList.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        if(playLists == null)
            return  0;
        return playLists.size();
    }

    public class PlayListViewHolder extends RecyclerView.ViewHolder {

        private ImageView playListImg;
        private TextView playListName;
        private CardView clickPlayList;

        public PlayListViewHolder(@NonNull View itemView) {
            super(itemView);
            playListImg = itemView.findViewById(R.id.playList_img);
            playListName = itemView.findViewById(R.id.playList_name);
            clickPlayList = itemView.findViewById(R.id.clickPlayList);
        }
    }
}
