package com.example.sergey.sportgrounds.ui.reservation;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import java.util.Calendar;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.sergey.sportgrounds.R;
import com.example.sergey.sportgrounds.model.LoginResponse;
import com.example.sergey.sportgrounds.rest.Utils;
import com.example.sergey.sportgrounds.ui.login.LoginActivity;

import io.realm.Realm;

import static android.R.attr.onClick;

public class ReservationActivity extends AppCompatActivity implements IReservationView, View.OnClickListener {

    private IReservationPresenter presenter;

    private TextView tvName;
    private EditText etDate;
    private EditText etTime;
    private EditText etComment;
    private Button btnSend;

    private int mYear, mMonth, mDay, mHour, mMinute;

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

        etDate.setOnClickListener(this);
        etTime.setOnClickListener(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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

    @Override
    public void onClick(View v) {
        if(v == etDate) {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            etDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }

        if(v == etTime) {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            etTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }
}
