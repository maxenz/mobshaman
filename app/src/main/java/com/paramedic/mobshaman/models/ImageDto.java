package com.paramedic.mobshaman.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by maxo on 27/10/16.
 */
public class ImageDto {

    @SerializedName("content")
    private String content;

    @SerializedName("name")
    private String name;

    public ImageDto(String content, String name) {
        this.content = content;
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
