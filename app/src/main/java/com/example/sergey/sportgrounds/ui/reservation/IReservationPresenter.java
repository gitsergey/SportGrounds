package com.example.sergey.sportgrounds.ui.reservation;


import android.app.Activity;
import android.os.Bundle;

public interface IReservationPresenter {
    void onCreate(Activity activity, Bundle savedInstanceState);
    void sendRequest(String comment, String dateAndTime, Integer locationId, String userToken);
}
