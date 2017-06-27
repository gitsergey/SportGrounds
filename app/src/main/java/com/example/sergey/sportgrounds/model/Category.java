/*
 * Created by Sergey Parfinovich on 27.06.17 12:19
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 28.05.17 0:19
 */

package com.example.sergey.sportgrounds.model;


import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Category  extends RealmObject {

    @PrimaryKey
    private Integer id;
    private String name;
    @SerializedName("icon_name")
    private String iconName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }


}