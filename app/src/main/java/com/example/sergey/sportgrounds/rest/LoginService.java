package com.example.sergey.sportgrounds.rest;


import com.example.sergey.sportgrounds.model.LoginRequest;
import com.example.sergey.sportgrounds.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {
    @POST("/api/v1/authenticate")
    Call<LoginResponse> getLoginAccess(@Body LoginRequest loginRequest);
}
