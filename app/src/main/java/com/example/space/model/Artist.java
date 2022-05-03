package com.example.space.model;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Artist {

@SerializedName("idArtist")
@Expose
private String idArtist;
@SerializedName("name")
@Expose
private String name;
@SerializedName("linkImage")
@Expose
private String linkImage;

public String getIdArtist() {
return idArtist;
}

public void setIdArtist(String idArtist) {
this.idArtist = idArtist;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getLinkImage() {
return linkImage;
}

public void setLinkImage(String linkImage) {
this.linkImage = linkImage;
}

}