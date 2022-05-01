package com.example.space.home.advSlide;

public class Advertisement {
    public Advertisement(int id, String url) {
        this.id = id;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    private int id;
    private String url;
}
