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
import com.example.sergey.sportgrounds.ui.ReservationActivity;
import com.squareup.picasso.Picasso;

import io.realm.Realm;

public class DetailActivity extends AppCompatActivity {

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
        Intent intent = new Intent();


        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String locationLat = getIntent().getStringExtra("locationLat");
        String locationLong = getIntent().getStringExtra("locationLong");

        Realm realm = Realm.getDefaultInstance();

        Log.d("WTF!", locationLat);

        try {
            Location location = realm.where(Location.class).equalTo("latitude", locationLat).findFirst();//.equalTo("longitude", locationLong).findFirst();
            configViews();

            Log.d("DETAILACTIVITY NAME", location.getName() + " " + location.getDescription() + " " );

            mDescription.setText(location.getDescription());
            mMark.setText(String.format("%.1f", location.getRating()));
            mContacts.setText("Email: email@examle.com\n" + "Tel: +79347234321");
            Picasso.with(DetailActivity.this).load(location.getImages().get(0).getResizedUrl())
                    .error(R.drawable.logo)
                    .into(mImage);

            CollapsingToolbarLayout collapsingToolbar =
                    (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
            collapsingToolbar.setTitle(location.getName());

            FloatingActionButton reserved = (FloatingActionButton) findViewById(R.id.reserved);
            reserved.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent1 = new Intent(DetailActivity.this, ReservationActivity.class);
                    startActivity(intent1);
                }
            });


        } finally {
            realm.close();
        }
    }

    private void configViews() {
        mImage = (ImageView) findViewById(R.id.backdrop);
        mDescription = (TextView) findViewById(R.id.tv_description);
        mMark = (TextView) findViewById(R.id.tv_mark);
        mContacts = (TextView) findViewById(R.id.tv_contacts);
    }
}
