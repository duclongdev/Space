package com.example.space.MusicPlayer.MoreBottomSheet;

public class More_Item {
    String name;
    int image;


    public More_Item(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public More_Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
