/*
 * Created by Sergey Parfinovich on 27.06.17 12:19
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 27.06.17 11:34
 */

package com.example.sergey.sportgrounds.ui.signup;


import android.app.Activity;
import android.os.Bundle;

public interface ISignupPresenter {
    void onCreate(Activity activity, Bundle savedInstanceState);
    void onResume();
    void onDestroy();
    void signup(String name, String email, String password);
}
