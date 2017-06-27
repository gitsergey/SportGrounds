/*
 * Created by Sergey Parfinovich on 27.06.17 12:19
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 15.06.17 20:55
 */

package com.example.sergey.sportgrounds.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sergey.sportgrounds.R;
import com.example.sergey.sportgrounds.model.Location;
import com.example.sergey.sportgrounds.model.realm.RealmService;
import com.example.sergey.sportgrounds.ui.main.IMainView;
import com.example.sergey.sportgrounds.ui.main.MainActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;

import io.realm.Realm;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private View view;
    private final Realm realm;
    private final Context contex;
    private LayoutInflater mInflater;

    public CustomInfoWindowAdapter(Realm realm, Context context) {
        this.realm = realm;
        this.contex = context;
        mInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE);
        view = mInflater.inflate(R.layout.custom_info_window, null);

    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(final Marker marker) {

        String locationLat = String.valueOf(marker.getPosition().latitude);
        Location location = realm.where(Location.class).equalTo("latitude", locationLat).findFirst();

        view = mInflater.inflate(R.layout.custom_info_window, null);

        if(location != null) {

            ImageView infoWindowImage = (ImageView) view.findViewById(R.id.infoWindowImageView);
            Log.d("URL: ", String.valueOf(location.getImages().get(0).getOriginUrl()));

            if (infoWindowImage.getDrawable() == null) {
                Picasso.with(contex).load(location.getImages().get(0).getThumbUrl()).resize(50, 50)
                        .error(R.drawable.logo)
                        .centerCrop().into(infoWindowImage, new InfoWindowRefresher(marker));
            } else {
                Picasso.with(contex).load(location.getImages().get(0).getThumbUrl()).resize(50, 50)
                        .error(R.drawable.logo)
                        .centerCrop().into(infoWindowImage);
            }

            TextView titleTV = (TextView) view.findViewById(R.id.title);
            titleTV.setText(location.getName());
            TextView snippetTV = (TextView) view.findViewById(R.id.snippet);
            snippetTV.setText("Оценка: " + location.getRating());
        }
        return view;
    }

}

class InfoWindowRefresher implements com.squareup.picasso.Callback {
    private Marker markerToRefresh;

    public InfoWindowRefresher(Marker markerToRefresh) {
        this.markerToRefresh = markerToRefresh;
    }

    @Override
    public void onSuccess() {
        if (markerToRefresh != null && markerToRefresh
                .isInfoWindowShown()) {
            markerToRefresh.hideInfoWindow();
            markerToRefresh.showInfoWindow();
        }
    }

    @Override
    public void onError() {}
}
