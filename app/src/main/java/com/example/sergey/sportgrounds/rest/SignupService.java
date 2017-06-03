package com.example.sergey.sportgrounds.rest;


import com.example.sergey.sportgrounds.model.LoginResponse;
import com.example.sergey.sportgrounds.model.SignupRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignupService {
    @POST("/api/v1/registrations")
    Call<LoginResponse> getSignupAccess(@Body SignupRequest signupRequest);
}
