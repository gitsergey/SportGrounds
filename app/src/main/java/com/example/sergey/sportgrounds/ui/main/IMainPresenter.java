package com.example.sergey.sportgrounds.ui.main;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.sergey.sportgrounds.model.Location;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

public interface IMainPresenter {
    void onCreate(Activity activity, Bundle savedInstanceState);
    void onResume();
    void onDestroy();
    ArrayList<Location> getAllLocations();
    ArrayList<Location> getCategoryLocations(Integer id);
    void deleteUser();
    void findUserData();

}
