package com.example.sergey.sportgrounds.ui.main;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.sergey.sportgrounds.model.Location;
import com.example.sergey.sportgrounds.model.LoginResponse;
import com.example.sergey.sportgrounds.model.User;
import com.example.sergey.sportgrounds.model.realm.RealmService;
import com.example.sergey.sportgrounds.rest.RestManager;
import com.example.sergey.sportgrounds.rest.Utils;
import com.google.android.gms.maps.model.Marker;

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
//        realmService = new RealmService(mRealm);
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

    public void findUserData() {
        LoginResponse loginResponse = realmService.getUserData();
        if(loginResponse != null) {
            mView.initNavHeader(loginResponse.getUser().getName(), loginResponse.getUser().getEmail());
        } else {
            mView.initNavHeader("Youre name", "youreemail@sportown.com");
        }
    }

}