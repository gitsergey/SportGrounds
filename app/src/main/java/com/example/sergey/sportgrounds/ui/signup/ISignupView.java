package com.example.sergey.sportgrounds.ui.signup;


public interface ISignupView {
    boolean getNetworkAvailability();
    void networkError();
    boolean validate();
    void onSignupFailed();
    void onSignupSuccess();
    void signupProgressStart();
    void signupProgressStop();
}
