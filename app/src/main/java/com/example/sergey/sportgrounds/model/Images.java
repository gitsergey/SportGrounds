package com.example.sergey.sportgrounds.model;


import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Images extends RealmObject {

    @SerializedName("origin_url")
    private String originUrl;
    @SerializedName("resized_url")
    private String resizedUrl;
    @SerializedName("thumb_url")
    private String thumbUrl;

    public String getOriginUrl() {
        return originUrl;
    }

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
    }

    public String getResizedUrl() {
        return resizedUrl;
    }

    public void setResizedUrl(String resizedUrl) {
        this.resizedUrl = resizedUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }


}
