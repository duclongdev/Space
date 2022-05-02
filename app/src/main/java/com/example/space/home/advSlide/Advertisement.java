package com.example.space.home.advSlide;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Advertisement {

    @SerializedName("idBanner")
    @Expose
    private String idBanner;
    @SerializedName("idSong")
    @Expose
    private String idSong;
    @SerializedName("linkImage")
    @Expose
    private String linkImage;

    public String getIdBanner() {
        return idBanner;
    }

    public void setIdBanner(String idBanner) {
        this.idBanner = idBanner;
    }

    public String getIdSong() {
        return idSong;
    }

    public void setIdSong(String idSong) {
        this.idSong = idSong;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }

}
