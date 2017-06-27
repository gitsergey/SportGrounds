/*
 * Created by Sergey Parfinovich on 27.06.17 12:19
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 27.06.17 11:34
 */

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
