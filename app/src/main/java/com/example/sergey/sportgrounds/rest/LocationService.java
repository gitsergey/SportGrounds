package com.example.sergey.sportgrounds.rest;

import com.example.sergey.sportgrounds.model.Location;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface LocationService {
    @GET("/api/v1/locations")
    Call<List<Location>> getAllLocations();
}