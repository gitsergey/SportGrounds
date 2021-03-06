/*
 * Created by Sergey Parfinovich on 27.06.17 12:19
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 27.06.17 11:49
 */

package com.example.sergey.sportgrounds.ui.detail;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sergey.sportgrounds.R;
import com.example.sergey.sportgrounds.model.Location;
import com.example.sergey.sportgrounds.model.LoginResponse;
import com.example.sergey.sportgrounds.rest.Utils;
import com.example.sergey.sportgrounds.ui.reservation.ReservationActivity;
import com.example.sergey.sportgrounds.ui.login.LoginActivity;
import com.squareup.picasso.Picasso;

import io.realm.Realm;

public class DetailActivity extends AppCompatActivity implements IDetailView {

    private IDetailPresenter presenter;

    private TextView mName;
    private TextView mDescription;
    private TextView mMark;
    private ImageView mImage;
    private RatingBar mRatingBar;

    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        configViews();
        presenter = new DetailPresenterImpl(this, Realm.getDefaultInstance());
        presenter.onCreate(this, savedInstanceState);
        loadData();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadData() {
        String locationLat = getIntent().getStringExtra("locationLat");

        final Location location = presenter.findLocationData(locationLat);

        if(location != null) {
            Log.d("DETAILACTIVITY NAME", location.getName() + " " + location.getDescription() + " ");

            String descr = location.getDescription().toString();

            mDescription.setText(descr.replace("\\n", "\n"));
            mMark.setText(String.format("%.1f", location.getRating()));
            mName.setText(location.getName());

            Picasso.with(DetailActivity.this).load(location.getImages().get(0).getResizedUrl())
                    .error(R.drawable.logo)
                    .into(mImage);
        }

        final LoginResponse loginResponse = presenter.findUserData();

        FloatingActionButton reserved = (FloatingActionButton) findViewById(R.id.reserved);
        reserved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loginResponse != null) {
                    Intent intent1 = new Intent(DetailActivity.this, ReservationActivity.class);
                    intent1.putExtra("locationName", location.getName());
                    intent1.putExtra("locationId", location.getId().toString());
                    intent1.putExtra("authToken", loginResponse.getAuthToken());
                    startActivity(intent1);
                } else {
                    Intent intent1 = new Intent(DetailActivity.this, LoginActivity.class);
                    startActivity(intent1);
                }
            }
        });

        mMark.setText(location.getRating().toString());
        mRatingBar.setRating(Float.valueOf(String.valueOf(location.getRating())));

        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, final float rating, boolean fromUser) {
                Snackbar snackbar = Snackbar.make(coordinatorLayout, "Отправить вашу оценку: " + rating, Snackbar.LENGTH_LONG)
                        .setAction("Отправить", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(loginResponse != null) {
                                    presenter.sendRatingRequest(rating, location.getId(), loginResponse.getAuthToken());
                                } else {
                                    Intent intent1 = new Intent(DetailActivity.this, LoginActivity.class);
                                    startActivity(intent1);
                                }
                            }
                        });
                snackbar.show();
            }
        });
    }

    private void configViews() {
        mImage = (ImageView) findViewById(R.id.backdrop);
        mDescription = (TextView) findViewById(R.id.tv_description);
        mMark = (TextView) findViewById(R.id.tv_mark);
        mRatingBar = (RatingBar) findViewById(R.id.ratingBar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_content);
        mName = (TextView) findViewById(R.id.tv_name);
    }

    @Override
    public boolean getNetworkAvailability() {
        return Utils.isNetworkAvailable(getApplicationContext());
    }

    @Override
    public void showError() {
        Toast.makeText(getApplicationContext(), "Ошибка подключения", Toast.LENGTH_LONG).show();
    }

}
