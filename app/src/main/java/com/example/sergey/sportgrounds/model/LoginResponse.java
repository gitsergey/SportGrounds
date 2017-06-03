package com.example.sergey.sportgrounds.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class LoginResponse extends RealmObject{

    @PrimaryKey
    @SerializedName("auth_token")
    @Expose
    private String authToken;
    @SerializedName("user")
    @Expose
    private User user;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}

