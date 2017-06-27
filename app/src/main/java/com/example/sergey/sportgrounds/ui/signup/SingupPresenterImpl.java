package com.example.sergey.sportgrounds.ui.signup;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.sergey.sportgrounds.model.LoginResponse;
import com.example.sergey.sportgrounds.model.SignupRequest;
import com.example.sergey.sportgrounds.model.realm.RealmService;
import com.example.sergey.sportgrounds.rest.RestManager;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingupPresenterImpl implements ISignupPresenter {
    private static final String TAG = "SignupActivity";

    private ISignupView mView;
    private Realm mRealm;

    private RestManager mManager;
    private RealmService realmService;

    public SingupPresenterImpl(ISignupView view, Realm realm) {
        this.mView = view;
        this.mRealm = realm;
    }

    @Override
    public void onCreate(Activity activity, Bundle savedInstanceState) {
        mManager = new RestManager();
        realmService = new RealmService(mRealm);
    }

    @Override
    public void signup(String name, String email, String password) {
        Log.d(TAG, "Signup");

        if (!mView.validate()) {
            mView.onSignupFailed();
            return;
        }

        mView.signupProgressStart();

        final SignupRequest signupRequest = new SignupRequest();
        signupRequest.setName(name);
        signupRequest.setEmail(email);
        signupRequest.setPassword(password);

        if(mView.getNetworkAvailability()) {
            realmService.deleteUser();
            Call<LoginResponse> loginResponseCall = mManager.getSignupService().getSignupAccess(signupRequest);

            loginResponseCall.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    LoginResponse loginResponse = response.body();
                    Log.d(TAG, "onResponse: " + loginResponse.getUser().getName() + " / " + loginResponse.getUser().getEmail());
                    mView.signupProgressStop();
                    realmService.addUser(loginResponse);
                    mView.onSignupSuccess();
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Log.d(TAG, "onFailure: Call Error");
                    mView.signupProgressStop();
                    mView.onSignupFailed();
                }
            });

        } else {
            mView.signupProgressStop();
            mView.networkError();
            mView.onSignupFailed();
        }
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }
}
