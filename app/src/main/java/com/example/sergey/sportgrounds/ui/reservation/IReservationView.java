package com.example.sergey.sportgrounds.ui.reservation;


public interface IReservationView {
    void showProgress();
    void hideProgress();
    void showMessage();
    void showError();
    boolean getNetworkAvailability();
    void finishActivity();
}
