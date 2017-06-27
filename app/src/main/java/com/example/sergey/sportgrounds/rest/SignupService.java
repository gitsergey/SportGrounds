/*
 * Created by Sergey Parfinovich on 27.06.17 12:19
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 02.06.17 17:15
 */

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
