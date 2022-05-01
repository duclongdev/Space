package com.example.space.home.playLists.category;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.space.R;
import com.example.space.home.playLists.playlist.PlayList;

import java.util.List;

public class CategoryPlaylist {
    public CategoryPlaylist(int id, String name, List<PlayList> listPlayLists) {
        this.id = id;
        this.name = name;
        this.listPlayLists = listPlayLists;
    }

    private int id;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<PlayList> getListPlayLists() {
        return listPlayLists;
    }
    private String name;
    private List<PlayList> listPlayLists;



}
