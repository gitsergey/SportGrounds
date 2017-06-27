package com.example.sergey.sportgrounds.ui.login;


public interface ILoginView {
    void loginProgressStart();
    void loginProgressStop();
    boolean validate();
    void onLoginSuccess();
    void onLoginFailed();
    boolean getNetworkAvailability();
    void networkError();
}
