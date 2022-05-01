package com.example.space.home.playLists.playlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.space.R;
import com.example.space.myInterface.IClickPlayList;

import java.util.List;

public class PlayListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static int TYPE_PLAYLIST_RECENT = 0;
    private static int TYPE_PLAYLIST_NORMAL = 1;
    private static int TYPE_PLAYLIST_AUTHOR = 2;

    public PlayListAdapter(Fragment fragment, IClickPlayList iClickPlayList) {
        this.fragment = fragment;
        this.iClickPlayList = iClickPlayList;
    }

    private List<PlayList> playLists;
    private Fragment fragment;
    private IClickPlayList iClickPlayList;

    @Override
    public int getItemViewType(int position) {
        int type = playLists.get(position).getType();
        switch (type) {
            case 0:
                return TYPE_PLAYLIST_RECENT;
            case 1:
                return TYPE_PLAYLIST_NORMAL;
            case 2:
                return TYPE_PLAYLIST_AUTHOR;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        if (playLists == null) return 0;
        return playLists.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_PLAYLIST_NORMAL) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.play_list_item, parent, false);
            return new PlayListViewHolder(view);
        } else if (viewType == TYPE_PLAYLIST_RECENT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_item, parent, false);
            return new RecentViewHolder(view);
        } else if(viewType == TYPE_PLAYLIST_AUTHOR) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.author_item, parent, false);
            return new SongsAuthorViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PlayList playList = playLists.get(position);
        if (playList == null)
            return;
        if (holder.getItemViewType() == TYPE_PLAYLIST_NORMAL) {
            PlayListViewHolder playListViewHolder = (PlayListViewHolder) holder;
            playListViewHolder.playListName.setText(playList.getName());
            Glide.with(fragment).load(playList.getUrl()).centerCrop().into(playListViewHolder.playListImg);
            playListViewHolder.clickPlayList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iClickPlayList.onClickPlayList(playList);
                }
            });
        } else if (holder.getItemViewType() == TYPE_PLAYLIST_RECENT) {
            RecentViewHolder recentViewHolder = (RecentViewHolder) holder;
            recentViewHolder.recentPlaylistName.setText(playList.getName());
            Glide.with(fragment).load(playList.getUrl()).centerCrop().into(recentViewHolder.recentPlaylistImg);
            recentViewHolder.clickRecentSongList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iClickPlayList.onClickPlayList(playList);
                }
            });
        } else if (holder.getItemViewType() == TYPE_PLAYLIST_AUTHOR) {
            SongsAuthorViewHolder songsAuthorViewHolder = (SongsAuthorViewHolder) holder;
            songsAuthorViewHolder.authorName.setText(playList.getName());
            Glide.with(fragment).load(playList.getUrl()).centerCrop().into(songsAuthorViewHolder.authorImg);
            songsAuthorViewHolder.clickListSongOfAuthor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iClickPlayList.onClickPlayList(playList);
                }
            });
        }
    }

    public void getData(List<PlayList> playLists) {
        this.playLists = playLists;
        notifyDataSetChanged();
    }

    public class PlayListViewHolder extends RecyclerView.ViewHolder {

        private ImageView playListImg;
        private TextView playListName;
        private LinearLayout clickPlayList;

        public PlayListViewHolder(@NonNull View itemView) {
            super(itemView);
            playListImg = itemView.findViewById(R.id.playList_img);
            playListName = itemView.findViewById(R.id.playList_name);
            clickPlayList = itemView.findViewById(R.id.clickPlayList);
        }
    }

    public class SongsAuthorViewHolder extends RecyclerView.ViewHolder {

        private ImageView authorImg;
        private TextView authorName;
        private LinearLayout clickListSongOfAuthor;

        public SongsAuthorViewHolder(@NonNull View itemView) {
            super(itemView);
            authorImg = itemView.findViewById(R.id.author_img);
            authorName = itemView.findViewById(R.id.author_name);
            clickListSongOfAuthor = itemView.findViewById(R.id.click_song_list_of_author);
        }
    }

    public class RecentViewHolder extends RecyclerView.ViewHolder {

        private ImageView recentPlaylistImg;
        private TextView recentPlaylistName;
        private LinearLayout clickRecentSongList;

        public RecentViewHolder(@NonNull View itemView) {
            super(itemView);
            recentPlaylistImg = itemView.findViewById(R.id.recent_song_list_img);
            recentPlaylistName = itemView.findViewById(R.id.recent_song_list_name);
            clickRecentSongList = itemView.findViewById(R.id.click_recent_song_list);
        }
    }
}
