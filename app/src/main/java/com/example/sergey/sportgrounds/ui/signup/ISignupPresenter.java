package com.example.sergey.sportgrounds.ui.signup;


import android.app.Activity;
import android.os.Bundle;

public interface ISignupPresenter {
    void onCreate(Activity activity, Bundle savedInstanceState);
    void onResume();
    void onDestroy();
    void signup(String name, String email, String password);
}
