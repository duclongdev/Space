package com.example.space.model;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Theme {

@SerializedName("idTheme")
@Expose
private String idTheme;
@SerializedName("name")
@Expose
private String name;
@SerializedName("linkTheme")
@Expose
private String linkTheme;

public String getIdTheme() {
return idTheme;
}

public void setIdTheme(String idTheme) {
this.idTheme = idTheme;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getLinkTheme() {
return linkTheme;
}

public void setLinkTheme(String linkTheme) {
this.linkTheme = linkTheme;
}

}