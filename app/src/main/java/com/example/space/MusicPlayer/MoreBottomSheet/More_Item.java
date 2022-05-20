package com.example.space.MusicPlayer.MoreBottomSheet;

public class More_Item {
    String title;
    int image;


    public More_Item(String name, int image) {
        this.title = name;
        this.image = image;
    }

    public More_Item(String name) {
        this.title = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
