package com.example.sergey.sportgrounds.ui.detail;


import android.app.Activity;
import android.os.Bundle;

import com.example.sergey.sportgrounds.model.Location;
import com.example.sergey.sportgrounds.model.LoginResponse;

public interface IDetailPresenter {

    Location findLocationData(String latitude);
    LoginResponse findUserData();

}
