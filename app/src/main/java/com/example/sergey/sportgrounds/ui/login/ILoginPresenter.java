package com.example.sergey.sportgrounds.ui.login;


import android.app.Activity;
import android.os.Bundle;

public interface ILoginPresenter {
    void onCreate(Activity activity, Bundle savedInstanceState);
    void onResume();
    void onDestroy();
    void login(String email, String password);
}
