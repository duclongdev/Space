package com.example.space.home.playLists.playlist;

public class PlayList {

    private int id;
    private String url;

    public String getName() {
        return name;
    }

    private String name;

    public PlayList(int id, String url, String name) {
        this.id = id;
        this.url = url;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }
}
