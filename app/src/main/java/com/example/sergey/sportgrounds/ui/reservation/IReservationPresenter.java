/*
 * Created by Sergey Parfinovich on 27.06.17 12:19
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 27.06.17 11:49
 */

package com.example.sergey.sportgrounds.ui.reservation;


import android.app.Activity;
import android.os.Bundle;

public interface IReservationPresenter {
    void onCreate(Activity activity, Bundle savedInstanceState);
    void sendRequest(String comment, String dateAndTime, Integer locationId, String userToken);
}
