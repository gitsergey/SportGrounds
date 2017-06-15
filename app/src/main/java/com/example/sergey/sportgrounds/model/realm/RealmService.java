package com.example.sergey.sportgrounds.model.realm;

import android.util.Log;

import com.example.sergey.sportgrounds.model.Location;
import com.example.sergey.sportgrounds.model.LoginResponse;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


public class RealmService {

    private final Realm mRealm;

    public RealmService(final Realm realm) {
        mRealm = realm;
    }

    public void closeRealm() {
        mRealm.close();
    }

    public ArrayList<Location> getAllLocations() {
        RealmResults<Location> results = mRealm.where(Location.class).findAll();
        ArrayList<Location> locations = new ArrayList<>();
        for (Location l : results) {
            locations.add(l);
            Log.d("Find Realm ALL DATA", l.getName());
        }
        return locations;
    }

    public ArrayList<Location> getCategoryLocations(Integer id) {
        RealmResults<Location> results = mRealm.where(Location.class).equalTo("categoryId", id).findAll();
        ArrayList<Location> categoryId = new ArrayList<>();
        if(results.size() != 0) {
            for (Location l : results) {
                categoryId.add(l);
            }
        }
        return categoryId;
    }


    public void addLocationAsync(final List<Location> LocationsList) {
        final Location locationRealm = new Location();

        for (int i = 0; i < LocationsList.size(); i++) {
            final Location location = LocationsList.get(i);

            mRealm.beginTransaction();
            locationRealm.setId(location.getId());
            locationRealm.setName(location.getName());
            locationRealm.setDescription(location.getDescription());
            locationRealm.setLatitude(location.getLatitude());
            locationRealm.setLongitude(location.getLongitude());
            locationRealm.setRating(location.getRating());
            locationRealm.setCategoryId(location.getCategoryId());
            locationRealm.setImages(location.getImages());
            locationRealm.setCategory(location.getCategory());
            mRealm.copyToRealmOrUpdate(locationRealm);
//            Log.d("Add Database", location.getId() + " " + location.getName() + " CategoryId: " + location.getCategory().getId() + " /Image URL: " + location.getImages().get(0).getOriginUrl());
            mRealm.commitTransaction();
        }
    }

    public void addUser(LoginResponse loginResponse) {
        final LoginResponse loginResponseRealm = new LoginResponse();
        if(loginResponse != null) {
            mRealm.beginTransaction();
            loginResponseRealm.setAuthToken(loginResponse.getAuthToken());
            loginResponseRealm.setUser(loginResponse.getUser());
            Log.d("Add Database", "addUser: " + loginResponse.getUser().getEmail());
            mRealm.copyToRealmOrUpdate(loginResponseRealm);
            mRealm.commitTransaction();
        } else {
            Log.d("Add Database", "addUser: Failed");
        }
    }

    public void deleteUser() {
        mRealm.beginTransaction();
        mRealm.delete(LoginResponse.class);
        mRealm.commitTransaction();
    }

    public LoginResponse getUserData() {
        LoginResponse loginResponse = mRealm.where(LoginResponse.class).findFirst();
        return loginResponse;
    }
}
