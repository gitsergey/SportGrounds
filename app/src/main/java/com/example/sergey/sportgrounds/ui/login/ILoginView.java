/*
 * Created by Sergey Parfinovich on 27.06.17 12:19
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 26.06.17 22:51
 */

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
