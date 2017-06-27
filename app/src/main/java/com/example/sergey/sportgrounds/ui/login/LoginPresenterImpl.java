package com.example.sergey.sportgrounds.ui.login;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.sergey.sportgrounds.model.LoginRequest;
import com.example.sergey.sportgrounds.model.LoginResponse;
import com.example.sergey.sportgrounds.model.realm.RealmService;
import com.example.sergey.sportgrounds.rest.RestManager;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginPresenterImpl implements ILoginPresenter {

    private static final String TAG = "LoginActivity";

    private ILoginView mView;
    private Realm mRealm;
    private RestManager mManager;
    private RealmService realmService;

    public LoginPresenterImpl(Realm realm, ILoginView view) {
        this.mRealm = realm;
        this.mView = view;
    }

    @Override
    public void onCreate(Activity activity, Bundle savedInstanceState) {
        mManager = new RestManager();
        realmService = new RealmService(mRealm);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    public void login(String email, String password) {
        Log.d(TAG, "Login");

        if (!mView.validate()) {
            mView.onLoginFailed();
            return;
        }

        mView.loginProgressStart();

        final LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);

        if(mView.getNetworkAvailability()) {
            realmService.deleteUser();
            Call<LoginResponse> loginResponseCall = mManager.getLoginService().getLoginAccess(loginRequest);

            loginResponseCall.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    LoginResponse loginResponse = response.body();
                    if(loginResponse != null) {
                        Log.d(TAG, "onResponse: " + loginResponse.getUser().getName() + " / " + loginResponse.getUser().getEmail());
                        mView.loginProgressStop();
                        realmService.addUser(loginResponse);
                        mView.onLoginSuccess();
                    } else {
                        mView.loginProgressStop();
                        mView.onLoginFailed();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Log.d(TAG, "onFailure: Call Error");
                    mView.loginProgressStop();
                    mView.onLoginFailed();
                }
            });

        } else {
            mView.loginProgressStop();
            mView.networkError();
            mView.onLoginFailed();
        }
    }
}
