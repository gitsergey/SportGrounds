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
