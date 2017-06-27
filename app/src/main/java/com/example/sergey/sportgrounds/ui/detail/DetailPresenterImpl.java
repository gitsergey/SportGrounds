package com.example.sergey.sportgrounds.ui.detail;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.sergey.sportgrounds.model.Location;
import com.example.sergey.sportgrounds.model.LoginResponse;
import com.example.sergey.sportgrounds.model.RatingRequest;
import com.example.sergey.sportgrounds.model.RatingResponse;
import com.example.sergey.sportgrounds.rest.RestManager;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPresenterImpl implements IDetailPresenter {

    private IDetailView mView;
    private Realm mRealm;
    private RestManager mManager;

    public DetailPresenterImpl(IDetailView mView, Realm mRealm) {
        this.mView = mView;
        this.mRealm = mRealm;
    }

    @Override
    public void onCreate(Activity activity, Bundle savedInstanceState) {
        mManager = new RestManager();
    }

    @Override
    public Location findLocationData(String latitude) {
        final Location location = mRealm.where(Location.class).equalTo("latitude", latitude).findFirst();
        return location;
    }

    @Override
    public void sendRatingRequest(final Float rating, Integer locationId, String authToken) {
        Map<String, String> map = new HashMap<>();

        map.put("Content-Type", "application/json");
        map.put("Authorization", authToken);

        final RatingRequest ratingRequest = new RatingRequest();
        ratingRequest.setValue(rating);
        ratingRequest.setLocationId(locationId);

        if(mView.getNetworkAvailability()) {
            Call<RatingResponse> ratingResponseCall = mManager.getRatingService().getRating(map, ratingRequest);

            ratingResponseCall.enqueue(new Callback<RatingResponse>() {
                @Override
                public void onResponse(Call<RatingResponse> call, Response<RatingResponse> response) {
                    RatingResponse ratingResponse = response.body();
                    if(ratingResponse != null) {
                        Log.d("DetailActivity", "onResponse:" + ratingResponse.getValue());
                    } else {
                        mView.showError();
                    }
                }

                @Override
                public void onFailure(Call<RatingResponse> call, Throwable t) {
                    Log.d("ReservtionActivity", "onFailure: Call Error");
                    mView.showError();
                }
            });

        } else {
            mView.showError();
        }
    }

    @Override
    public LoginResponse findUserData() {
        final LoginResponse loginResponse = mRealm.where(LoginResponse.class).findFirst();
        return loginResponse;
    }
}
