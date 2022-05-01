package com.example.space.detailPlayllist.bottomSheet;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.space.R;
import com.example.space.myInterface.IClickItemOnSongOption;


import java.util.List;

public class SongMoreOptionAdapter extends RecyclerView.Adapter<SongMoreOptionAdapter.MoreOptionViewHolder>{

    private List<SongOption> songOptionList;
    private IClickItemOnSongOption iClickItemOnSongOption;

    public SongMoreOptionAdapter(List<SongOption> songOptionList, IClickItemOnSongOption iClickItemOnSongOption) {
        this.songOptionList = songOptionList;
        this.iClickItemOnSongOption = iClickItemOnSongOption;
    }

    @NonNull
    @Override
    public MoreOptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_option_item, parent, false);
        return new MoreOptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoreOptionViewHolder holder, int position) {
        SongOption songOption = songOptionList.get(position);
        if(songOption == null)
            return;
        holder.songOptionTitle.setText(songOption.getTitle());
        holder.songOptionImg.setImageResource(songOption.getSrc());
        holder.moreOptionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemOnSongOption.onClickItemOption(songOption.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        if(songOptionList == null) return 0;
        return songOptionList.size();
    }

    public class MoreOptionViewHolder extends RecyclerView.ViewHolder {
        ImageView songOptionImg;
        TextView songOptionTitle;
        LinearLayout moreOptionLayout;
        public MoreOptionViewHolder(@NonNull View itemView) {
            super(itemView);
            songOptionImg = itemView.findViewById(R.id.song_option_img);
            songOptionTitle = itemView.findViewById(R.id.song_option_title);
            moreOptionLayout = itemView.findViewById(R.id.choose_option_song);
        }
    }
}
