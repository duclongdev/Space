package com.example.space.detailPlayllist.bottomSheet;

public class SongOption {
    private int id;
    private int src;
    private String title;


    public SongOption(int id, int src, String title) {
        this.id = id;
        this.src = src;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public int getSrc() {
        return src;
    }

    public String getTitle() {
        return title;
    }
}
