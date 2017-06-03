package com.example.sergey.sportgrounds.ui.signup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sergey.sportgrounds.R;
import com.example.sergey.sportgrounds.model.LoginResponse;
import com.example.sergey.sportgrounds.model.SignupRequest;
import com.example.sergey.sportgrounds.model.realm.RealmService;
import com.example.sergey.sportgrounds.rest.RestManager;
import com.example.sergey.sportgrounds.rest.Utils;
import com.example.sergey.sportgrounds.ui.login.LoginActivity;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    private RestManager mManager;
    private RealmService realmService;

    EditText _nameText;
    EditText _emailText;
    EditText _passwordText;
    EditText _reEnterPasswordText;
    Button _signupButton;
    TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        _loginLink = (TextView) findViewById(R.id.link_login);
        _nameText = (EditText) findViewById(R.id.input_name);
        _emailText = (EditText) findViewById(R.id.input_email);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _reEnterPasswordText = (EditText) findViewById(R.id.input_reEnterPassword);
        _signupButton = (Button) findViewById(R.id.btn_signup);

        mManager = new RestManager();
        realmService = new RealmService(Realm.getDefaultInstance());

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // signup logic
        final SignupRequest signupRequest = new SignupRequest();
        signupRequest.setName(name);
        signupRequest.setEmail(email);
        signupRequest.setPassword(password);

        if(getNetworkAvailability()) {
            Call<LoginResponse> loginResponseCall = mManager.getSignupService().getSignupAccess(signupRequest);

            loginResponseCall.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    LoginResponse loginResponse = response.body();
                    Log.d(TAG, "onResponse: " + loginResponse.getUser().getName() + " / " + loginResponse.getUser().getEmail());
                    progressDialog.dismiss();
                    realmService.addUser(loginResponse);
                    onSignupSuccess();
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Log.d(TAG, "onFailure: Call Error");
                    progressDialog.dismiss();
                    onSignupFailed();
                }
            });

        } else {
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_LONG);
            onSignupFailed();
        }
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }


        if (password.isEmpty() || password.length() < 4 || password.length() > 16) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 16 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }

    public boolean getNetworkAvailability() {
        return Utils.isNetworkAvailable(getApplicationContext());
    }
}