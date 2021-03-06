/*
 * Created by Sergey Parfinovich on 27.06.17 12:19
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 06.06.17 17:42
 */

package com.example.sergey.sportgrounds.rest;


import com.example.sergey.sportgrounds.model.Location;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestManager {

    private LocationService mLocationService;
    private LoginService mLoginService;
    private SignupService mSignupService;
    private ReservationService mReservationService;
    private RatingService mRatingService;

    public static final String BASE_URL = "https://sportown.herokuapp.com";
    final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build();

    Gson gson = new GsonBuilder().registerTypeAdapter(Location.class, new LocationDeserializer()).create();


    public LocationService getLocatoinService() {
        if (mLocationService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(buildGsonConverter())
                    .build();
            mLocationService = retrofit.create(LocationService.class);
        }
        return mLocationService;
    }

    public LoginService getLoginService() {
        if(mLoginService == null) {
            Retrofit retrofit2 = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mLoginService = retrofit2.create(LoginService.class);
        }
        return mLoginService;
    }

    public SignupService getSignupService() {
        if(mSignupService == null) {
            Retrofit retrofit3 = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mSignupService = retrofit3.create(SignupService.class);
        }
        return mSignupService;
    }

    public ReservationService getReservationService() {
        if(mReservationService == null) {
            Retrofit retrofit4 = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mReservationService = retrofit4.create(ReservationService.class);
        }
        return mReservationService;
    }

    public RatingService getRatingService() {
        if(mRatingService == null) {
            Retrofit retrofit5 = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mRatingService = retrofit5.create(RatingService.class);
        }
        return mRatingService;
    }


    private static GsonConverterFactory buildGsonConverter() {
        GsonBuilder gsonBuilder = new GsonBuilder();

        // Add custom deserializers
        gsonBuilder.registerTypeAdapter(Location.class, new LocationDeserializer());
        Gson myGson = gsonBuilder.create();

        return GsonConverterFactory.create(myGson);
    }
}
