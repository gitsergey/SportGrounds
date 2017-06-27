/*
 * Created by Sergey Parfinovich on 27.06.17 12:19
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 27.06.17 11:49
 */

package com.example.sergey.sportgrounds.ui.detail;


import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import com.example.sergey.sportgrounds.model.Location;
import com.example.sergey.sportgrounds.model.LoginResponse;

public interface IDetailPresenter {

    void onCreate(Activity activity, Bundle savedInstanceState);
    Location findLocationData(String latitude);
    void sendRatingRequest(Float rating, Integer locationId, String authToken);
    LoginResponse findUserData();
}
