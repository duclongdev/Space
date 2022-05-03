package com.example.space.model;

import java.util.List;

public class ListSearch {
    private int type;
    private List<Artist> artistList;
    private List<Song> songList;

    public ListSearch(int type, List<Artist> artistList, List<Song> songList) {
        this.type = type;
        this.artistList = artistList;
        this.songList = songList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Artist> getArtistList() {
        return artistList;
    }

    public void setArtistList(List<Artist> artistList) {
        this.artistList = artistList;
    }

    public List<Song> getSongList() {
        return songList;
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }
}
