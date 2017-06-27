/*
 * Created by Sergey Parfinovich on 27.06.17 12:19
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 31.05.17 16:21
 */

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
