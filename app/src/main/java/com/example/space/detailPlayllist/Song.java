package com.example.space.detailPlayllist;

public class Song {
    private int id;
    private String url;
    private String name;
    private String author;

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public Song(int id, String url, String name, String author) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.author = author;
    }


}
