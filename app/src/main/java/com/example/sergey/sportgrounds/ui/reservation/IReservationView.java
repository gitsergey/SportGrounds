/*
 * Created by Sergey Parfinovich on 27.06.17 12:19
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 05.06.17 16:33
 */

package com.example.sergey.sportgrounds.ui.reservation;


public interface IReservationView {
    void showProgress();
    void hideProgress();
    void showMessage();
    void showError();
    boolean getNetworkAvailability();
    void finishActivity();
}
