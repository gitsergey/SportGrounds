package com.example.sergey.sportgrounds.ui.main;


import android.app.Activity;
import android.os.Bundle;
import com.example.sergey.sportgrounds.model.Location;
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
