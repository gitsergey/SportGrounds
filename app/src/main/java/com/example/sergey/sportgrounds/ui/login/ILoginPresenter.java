/*
 * Created by Sergey Parfinovich on 27.06.17 12:19
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 26.06.17 22:51
 */

package com.example.sergey.sportgrounds.ui.login;


import android.app.Activity;
import android.os.Bundle;

public interface ILoginPresenter {
    void onCreate(Activity activity, Bundle savedInstanceState);
    void onResume();
    void onDestroy();
    void login(String email, String password);
}
