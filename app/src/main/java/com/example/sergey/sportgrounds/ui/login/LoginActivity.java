package com.example.sergey.sportgrounds.ui.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sergey.sportgrounds.R;
import com.example.sergey.sportgrounds.rest.Utils;
import com.example.sergey.sportgrounds.ui.signup.SignupActivity;

import io.realm.Realm;


public class LoginActivity extends AppCompatActivity implements ILoginView {
    private static final int REQUEST_SIGNUP = 0;

    private ILoginPresenter presenter;

    private ProgressDialog progressDialog;

    EditText _passwordText;
    EditText _emailText;
    TextView _signupLink;
    Button _loginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _signupLink = (TextView) findViewById(R.id.link_signup);
        _emailText = (EditText) findViewById(R.id.input_email);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _loginButton = (Button) findViewById(R.id.btn_login);

        presenter = new LoginPresenterImpl(Realm.getDefaultInstance(), this);
        presenter.onCreate(this, savedInstanceState);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                _loginButton.setEnabled(false);
                presenter.login(_emailText.getText().toString(), _passwordText.getText().toString());
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    @Override
    public void loginProgressStart() {
        progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();
    }

    @Override
    public void loginProgressStop() {
        progressDialog.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    @Override
    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }

    @Override
    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 16) {
            _passwordText.setError("between 4 and 16 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    @Override
    public boolean getNetworkAvailability() {
        return Utils.isNetworkAvailable(getApplicationContext());
    }

    @Override
    public void networkError() {
        Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_LONG);
    }

}