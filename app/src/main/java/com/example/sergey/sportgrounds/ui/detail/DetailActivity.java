package com.example.sergey.sportgrounds.ui.detail;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sergey.sportgrounds.R;
import com.example.sergey.sportgrounds.model.Location;
import com.example.sergey.sportgrounds.model.LoginResponse;
import com.example.sergey.sportgrounds.ui.reservation.ReservationActivity;
import com.example.sergey.sportgrounds.ui.login.LoginActivity;
import com.squareup.picasso.Picasso;

import io.realm.Realm;

public class DetailActivity extends AppCompatActivity implements IDetailView {

    private IDetailPresenter presenter;

    TextView mCategory;
    TextView mName;
    TextView mDescription;
    TextView mMark;
    TextView mContacts;
    ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        configViews();
        presenter = new DetailPresenterImpl(this, Realm.getDefaultInstance());
        loadData();

    }

    private void loadData() {
        String locationLat = getIntent().getStringExtra("locationLat");

        final Location location = presenter.findLocationData(locationLat);

        if(location != null) {
            Log.d("DETAILACTIVITY NAME", location.getName() + " " + location.getDescription() + " ");

            mDescription.setText(location.getDescription());
            mMark.setText(String.format("%.1f", location.getRating()));
            mContacts.setText("Вы можете забронировать площадку");
            Picasso.with(DetailActivity.this).load(location.getImages().get(0).getResizedUrl())
                    .error(R.drawable.logo)
                    .into(mImage);

            CollapsingToolbarLayout collapsingToolbar =
                    (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
            collapsingToolbar.setTitle(location.getName());
        }

        FloatingActionButton reserved = (FloatingActionButton) findViewById(R.id.reserved);
        reserved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginResponse loginResponse = presenter.findUserData();
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
    }

    private void configViews() {
        mImage = (ImageView) findViewById(R.id.backdrop);
        mDescription = (TextView) findViewById(R.id.tv_description);
        mMark = (TextView) findViewById(R.id.tv_mark);
        mContacts = (TextView) findViewById(R.id.tv_contacts);
    }
}
