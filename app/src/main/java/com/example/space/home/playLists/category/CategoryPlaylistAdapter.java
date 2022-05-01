package com.example.space.home.playLists.category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.space.R;
import com.example.space.home.playLists.playlist.PlayList;
import com.example.space.home.playLists.playlist.PlayListAdapter;
import com.example.space.myInterface.IClickPlayList;

import java.util.List;

public class CategoryPlaylistAdapter extends RecyclerView.Adapter<CategoryPlaylistAdapter.CategoryViewHolder> {

    public CategoryPlaylistAdapter( Fragment fragment, IClickPlayList iClickPlayList) {
        this.fragment = fragment;
        this.iClickPlayList = iClickPlayList;
    }

    private List<CategoryPlaylist> categoryPlaylistList;
    private Fragment fragment;
    private IClickPlayList iClickPlayList;


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_category_item, parent, false);
        return new CategoryViewHolder(view);
    }
    public void setData(List<CategoryPlaylist> categoryPlaylistList){
        this.categoryPlaylistList = categoryPlaylistList;
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryPlaylist categoryPlaylist = categoryPlaylistList.get(position);
        if (categoryPlaylist == null)
            return;
        holder.title.setText(categoryPlaylist.getName());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(fragment.getContext(), RecyclerView.HORIZONTAL, false);
        holder.category_item.setLayoutManager(linearLayoutManager);

        PlayListAdapter playListAdapter = new PlayListAdapter(fragment, new IClickPlayList() {
            @Override
            public void onClickPlayList(PlayList playList) {
                iClickPlayList.onClickPlayList(playList);
            }
        });
        playListAdapter.getData(categoryPlaylist.getListPlayLists());
        holder.category_item.setAdapter(playListAdapter);
    }

    @Override
    public int getItemCount() {
        if (categoryPlaylistList == null)
            return 0;
        return categoryPlaylistList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private RecyclerView category_item;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            category_item = itemView.findViewById(R.id.category_item);
        }
    }


}
