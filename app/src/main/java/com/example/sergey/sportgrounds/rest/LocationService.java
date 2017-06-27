/*
 * Created by Sergey Parfinovich on 27.06.17 12:19
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 28.05.17 19:08
 */

package com.example.sergey.sportgrounds.rest;

import com.example.sergey.sportgrounds.model.Location;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface LocationService {
    @GET("/api/v1/locations")
    Call<List<Location>> getAllLocations();
}