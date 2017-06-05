package com.example.sergey.sportgrounds.rest;


import com.example.sergey.sportgrounds.model.ReservationRequest;
import com.example.sergey.sportgrounds.model.ReservationResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface ReservationService {
    @POST("/api/v1/requests")
    Call<ReservationResponse> getResrvation(
            @HeaderMap Map<String, String> headers,
            @Body ReservationRequest reservationRequest);
}
