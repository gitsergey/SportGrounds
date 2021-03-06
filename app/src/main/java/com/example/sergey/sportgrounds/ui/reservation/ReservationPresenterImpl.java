/*
 * Created by Sergey Parfinovich on 27.06.17 12:19
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 06.06.17 17:59
 */

package com.example.sergey.sportgrounds.ui.reservation;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.sergey.sportgrounds.model.ReservationRequest;
import com.example.sergey.sportgrounds.model.ReservationResponse;
import com.example.sergey.sportgrounds.rest.RestManager;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationPresenterImpl implements IReservationPresenter {

    private IReservationView mView;
    private RestManager restManager;

    @Override
    public void onCreate(Activity activity, Bundle savedInstanceState) {
        restManager = new RestManager();
    }

    public ReservationPresenterImpl(IReservationView mView) {
        this.mView = mView;
    }

    @Override
    public void sendRequest(String comment, String dateAndTime, Integer locationId, String userToken) {

        Map<String, String> map = new HashMap<>();

        map.put("Content-Type", "application/json");
        map.put("Authorization", userToken);

        final ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.setComment(comment);
        reservationRequest.setLocationId(locationId);
        reservationRequest.setReservedAt(dateAndTime);

        if(mView.getNetworkAvailability()) {
            mView.showProgress();
            Call<ReservationResponse> reservationResponseCall = restManager.getReservationService().getResrvation(map, reservationRequest);

            reservationResponseCall.enqueue(new Callback<ReservationResponse>() {
                @Override
                public void onResponse(Call<ReservationResponse> call, Response<ReservationResponse> response) {
                    ReservationResponse reservationResponse = response.body();
                    if(reservationResponse != null) {
                        Log.d("ReservtionActivity", "onResponse: " + reservationRequest.getReservedAt());
                        mView.hideProgress();
                        onRequestSuccess();
                    } else {
                        mView.hideProgress();
                    }
                }

                @Override
                public void onFailure(Call<ReservationResponse> call, Throwable t) {
                    Log.d("ReservtionActivity", "onFailure: Call Error");
                    mView.showError();
                    mView.hideProgress();
                }
            });

        } else {
            mView.showError();
        }
    }

    public void onRequestSuccess() {
        mView.finishActivity();
    }

}
