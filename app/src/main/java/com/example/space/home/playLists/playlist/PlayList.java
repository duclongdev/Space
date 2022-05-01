package com.example.space.home.playLists.playlist;

public class PlayList {

    private int id;
    private String url;
    private int type;

    public String getName() {
        return name;
    }

    private String name;

    public int getType() {
        return type;
    }

    public PlayList(int id, String url, String name, int type) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }
}
