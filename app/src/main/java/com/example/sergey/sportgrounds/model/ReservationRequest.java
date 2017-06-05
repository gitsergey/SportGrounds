package com.example.sergey.sportgrounds.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReservationRequest {

    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("location_id")
    @Expose
    private Integer locationId;
    @SerializedName("reserved_at")
    @Expose
    private String reservedAt;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getReservedAt() {
        return reservedAt;
    }

    public void setReservedAt(String reservedAt) {
        this.reservedAt = reservedAt;
    }

}
