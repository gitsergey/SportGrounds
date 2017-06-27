/*
 * Created by Sergey Parfinovich on 27.06.17 12:19
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 06.06.17 17:42
 */

package com.example.sergey.sportgrounds.rest;

import com.example.sergey.sportgrounds.model.RatingRequest;
import com.example.sergey.sportgrounds.model.RatingResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface RatingService {
    @POST("/api/v1/marks")
    Call<RatingResponse> getRating(
            @HeaderMap Map<String, String> headers,
            @Body RatingRequest ratingRequest);
}
