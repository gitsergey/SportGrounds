/*
 * Created by Sergey Parfinovich on 27.06.17 12:19
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 06.06.17 17:59
 */

package com.example.sergey.sportgrounds.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RatingRequest {

    @SerializedName("value")
    @Expose
    private Float value;
    @SerializedName("location_id")
    @Expose
    private Integer locationId;

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }
}
