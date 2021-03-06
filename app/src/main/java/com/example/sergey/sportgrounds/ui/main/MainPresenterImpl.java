/*
 * Created by Sergey Parfinovich on 27.06.17 12:19
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 27.06.17 11:49
 */

package com.example.sergey.sportgrounds.ui.main;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.sergey.sportgrounds.model.Location;
import com.example.sergey.sportgrounds.model.LoginResponse;
import com.example.sergey.sportgrounds.model.realm.RealmService;
import com.example.sergey.sportgrounds.rest.RestManager;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenterImpl implements IMainPresenter {

    private Realm mRealm;
    private IMainView mView;
    private RestManager mManager;

    private RealmService realmService;

    public MainPresenterImpl(Realm realm, IMainView mView) {
        this.mRealm = realm;
        this.mView = mView;
    }


    @Override
    public void onCreate(Activity activity, Bundle savedInstanceState) {
        realmService = new RealmService(mRealm);
        mManager = new RestManager();
        loadDataLocations();
        mView.showProgress();
    }

    @Override
    public void onResume() {
        if( mView != null) {
            findUserData();
        }
    }

    @Override
    public void deleteUser() {
        realmService.deleteUser();
    }

    @Override
    public void onDestroy() {
        realmService.closeRealm();
        mView = null;
    }

    @Override
    public ArrayList<Location> getAllLocations() {
        return realmService.getAllLocations();
    }

    @Override
    public ArrayList<Location> getCategoryLocations(Integer id) {
        return realmService.getCategoryLocations(id);
    }

    private void loadDataLocations() {
        Call<List<Location>> listCall = mManager.getLocatoinService().getAllLocations();
        listCall.enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {

                if(response.isSuccessful()) {
                    final List<Location> LocationsList = response.body();

                    realmService.addLocationAsync(LocationsList);
                    mView.createMarkerLocation(LocationsList);
                    Log.d("Locations data", LocationsList.get(1).getName());

                } else {
                    int sc = response.code();
                    switch (sc) {
                        case 400:
                            Log.e("Error 400", "Bad Request");
                            break;
                        case 404:
                            Log.e("Error 404", "Not Found");
                            break;
                        default:
                            Log.e("Error", "Generic Error");
                    }
                }
                mView.hideProgress();
            }

            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                Log.d("LoadingLocations", "onFailure: MainPresenterImpl");
                mView.hideProgress();
            }

        });
    }

    @Override
    public void findUserData() {
        LoginResponse loginResponse = realmService.getUserData();
        if(loginResponse != null) {
            mView.initNavHeader(loginResponse.getUser().getName(), loginResponse.getUser().getEmail());
            mView.showNavBarLoginOrExit(true);
        } else {
            mView.initNavHeader("Youre name", "youreemail@sportown.com");
            mView.showNavBarLoginOrExit(false);
        }
    }

}