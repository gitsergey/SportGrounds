package com.example.sergey.sportgrounds.ui.detail;


import android.app.Activity;
import android.os.Bundle;

import com.example.sergey.sportgrounds.model.Location;
import com.example.sergey.sportgrounds.model.LoginResponse;

import io.realm.Realm;

public class DetailPresenterImpl implements IDetailPresenter {

    private IDetailView mView;
    private Realm mRealm;

    public DetailPresenterImpl(IDetailView mView, Realm mRealm) {
        this.mView = mView;
        this.mRealm = mRealm;
    }

    @Override
    public Location findLocationData(String latitude) {
        final Location location = mRealm.where(Location.class).equalTo("latitude", latitude).findFirst();
        return location;
    }

    @Override
    public LoginResponse findUserData() {
        final LoginResponse loginResponse = mRealm.where(LoginResponse.class).findFirst();
        return loginResponse;
    }
}
