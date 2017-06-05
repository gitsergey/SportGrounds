package com.example.sergey.sportgrounds.ui.reservation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sergey.sportgrounds.R;
import com.example.sergey.sportgrounds.model.LoginResponse;
import com.example.sergey.sportgrounds.rest.Utils;
import com.example.sergey.sportgrounds.ui.login.LoginActivity;

import io.realm.Realm;

public class ReservationActivity extends AppCompatActivity implements IReservationView {

    private IReservationPresenter presenter;

    private TextView tvName;
    private EditText etDate;
    private EditText etTime;
    private EditText etComment;
    private Button btnSend;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        configView();

        String locationName = getIntent().getStringExtra("locationName");
        String locationIdstr = getIntent().getStringExtra("locationId");
        final String authToken = getIntent().getStringExtra("authToken");
        final Integer locationId = Integer.valueOf(locationIdstr);

        presenter = new ReservationPresenterImpl(this);
        presenter.onCreate(this, savedInstanceState);

        tvName.setText(locationName);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest(locationId, authToken);
            }
        });
    }

    private void sendRequest(Integer locationId, String userToken) {
        String date = etDate.getText().toString();
        String time = etTime.getText().toString();
        String comment = etComment.getText().toString();
        String dateAndTime = (date + " " + time);
        presenter.sendRequest(comment, dateAndTime, locationId, userToken);
    }

    private void configView() {
        tvName = (TextView) findViewById(R.id.location_name);
        etDate = (EditText) findViewById(R.id.input_date);
        etTime = (EditText) findViewById(R.id.input_time);
        etComment = (EditText) findViewById(R.id.input_comment);
        btnSend = (Button) findViewById(R.id.btn_send);
    }

    @Override
    public void showProgress() {
        progressDialog = new ProgressDialog(ReservationActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Send...");
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void showMessage() {
        Toast.makeText(getApplicationContext(), "Запрос отправлен", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError() {
        Toast.makeText(getApplicationContext(), "Ошибка подключения", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean getNetworkAvailability() {
        return Utils.isNetworkAvailable(getApplicationContext());
    }

    @Override
    public void finishActivity() {
        btnSend.setEnabled(true);
        finish();
    }
}
