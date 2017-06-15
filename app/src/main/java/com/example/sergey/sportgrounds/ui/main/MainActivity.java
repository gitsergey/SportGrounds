package com.example.sergey.sportgrounds.ui.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sergey.sportgrounds.R;
import com.example.sergey.sportgrounds.adapter.CustomInfoWindowAdapter;
import com.example.sergey.sportgrounds.model.Location;
import com.example.sergey.sportgrounds.model.LoginResponse;
import com.example.sergey.sportgrounds.rest.Utils;
import com.example.sergey.sportgrounds.ui.detail.DetailActivity;
import com.example.sergey.sportgrounds.ui.login.LoginActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity
        implements IMainView, NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

//    public static Context applicationContext;
    GoogleMap mMap;
    private ProgressDialog mDialog;
    private IMainPresenter presenter;
    private GoogleMap.InfoWindowAdapter customInfoWindowAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync((OnMapReadyCallback) this);

        mMap = mapFragment.getMap();

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

        customInfoWindowAdapter = new CustomInfoWindowAdapter(Realm.getDefaultInstance(), this);
        mMap.setInfoWindowAdapter(customInfoWindowAdapter);


        presenter = new MainPresenterImpl(Realm.getDefaultInstance(), this);
        presenter.onCreate(this, savedInstanceState);

    }

    @Override
    public void initNavHeader(String name, String email) {
        TextView tvUsername = (TextView) findViewById(R.id.user_name);
        TextView tvUserEmail = (TextView) findViewById(R.id.user_email);
        tvUsername.setText(name);
        tvUserEmail.setText(email);
    }

    @Override protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //Navigation and ToolBar

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.all_category) {
            mMap.clear();
            createMarkerLocation(presenter.getAllLocations());
        } else if (id == R.id.football) {
            mMap.clear();
            createMarkerLocation(presenter.getCategoryLocations(1));
        } else if (id == R.id.basketball) {
            mMap.clear();
            createMarkerLocation(presenter.getCategoryLocations(2));
        } else if (id == R.id.volleyball) {
            mMap.clear();
            createMarkerLocation(presenter.getCategoryLocations(3));
        } else if (id == R.id.hockey) {
            mMap.clear();
            createMarkerLocation(presenter.getCategoryLocations(4));
        } else if (id == R.id.tennis) {
            mMap.clear();
            createMarkerLocation(presenter.getCategoryLocations(5));
        } else if (id == R.id.table_tennis) {
            mMap.clear();
            createMarkerLocation(presenter.getCategoryLocations(6));
        } else if (id == R.id.roller_skates) {
            mMap.clear();
            createMarkerLocation(presenter.getCategoryLocations(7));
        } else if (id == R.id.bike) {
            mMap.clear();
            createMarkerLocation(presenter.getCategoryLocations(8));
        } else if (id == R.id.skateboard) {
            mMap.clear();
            createMarkerLocation(presenter.getCategoryLocations(9));
        } else if (id == R.id.workout) {
            mMap.clear();
            createMarkerLocation(presenter.getCategoryLocations(10));
        } else if (id == R.id.ice_rink) {
            mMap.clear();
            createMarkerLocation(presenter.getCategoryLocations(11));
        } else if (id == R.id.diving) {
            mMap.clear();
            createMarkerLocation(presenter.getCategoryLocations(12));
        } else if (id == R.id.bmx) {
            mMap.clear();
            createMarkerLocation(presenter.getCategoryLocations(13));
        } else if (id == R.id.rent) {
            mMap.clear();
            createMarkerLocation(presenter.getCategoryLocations(14));
        } else if (id == R.id.login) {
            Intent intent1 = new Intent(this, LoginActivity.class);
            startActivity(intent1);
        } else if (id == R.id.exit) {
            presenter.deleteUser();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Map function

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if(!getNetworkAvailability()) {
            createMarkerLocation(presenter.getAllLocations());
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(55.78874, 49.12214), 10));
    }

    public boolean getNetworkAvailability() {
        return Utils.isNetworkAvailable(getApplicationContext());
    }

    //MainView implement metodes

    @Override
    public void showProgress() {
        mDialog = new ProgressDialog(MainActivity.this);
        mDialog.setMessage("Loading Locations Data...");
        mDialog.setCancelable(true);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setIndeterminate(true);
        mDialog.show();
    }

    @Override
    public void hideProgress() {
        mDialog.dismiss();
    }

    @Override
    public void createMarkerLocation(List<Location> location) {
        if(location.size() !=  0) {
            for (int i = 0; i < location.size(); i++) {
                Marker map = mMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(location.get(i).getLatitude()), Double.valueOf(location.get(i).getLongitude())))
                        .title(location.get(i).getName())
                        .snippet(location.get(i).getDescription()));

                if(location.get(i).getCategory().getId() != null) {
                    switch (location.get(i).getCategory().getId()) {
                        case 1:
                            map.setIcon(vectorToBitmap(R.drawable.football, Color.parseColor("#e67e22")));
                            break;
                        case 2:
                            map.setIcon(vectorToBitmap(R.drawable.basketball, Color.parseColor("#e74c3c")));
                            break;
                        case 3:
                            map.setIcon(vectorToBitmap(R.drawable.volleyball, Color.parseColor("#f1c40f")));
                            break;
                        case 4:
                            map.setIcon(vectorToBitmap(R.drawable.hockey, Color.parseColor("#2980b9")));
                            break;
                        case 5:
                            map.setIcon(vectorToBitmap(R.drawable.tennis, Color.parseColor("#d35400")));
                            break;
                        case 6:
                            map.setIcon(vectorToBitmap(R.drawable.table_tennis, Color.parseColor("#f39c12")));
                            break;
                        case 7:
                            map.setIcon(vectorToBitmap(R.drawable.roller_skates, Color.parseColor("#16a085")));
                            break;
                        case 8:
                            map.setIcon(vectorToBitmap(R.drawable.bike, Color.parseColor("#27ae60")));
                            break;
                        case 9:
                            map.setIcon(vectorToBitmap(R.drawable.skateboard, Color.parseColor("#16a085")));
                            break;
                        case 10:
                            map.setIcon(vectorToBitmap(R.drawable.workout, Color.parseColor("#2c3e50")));
                            break;
                        case 11:
                            map.setIcon(vectorToBitmap(R.drawable.ice_rink, Color.parseColor("#3498db")));
                            break;
                        case 12:
                            map.setIcon(vectorToBitmap(R.drawable.diving, Color.parseColor("#34495e")));
                            break;
                        case 13:
                            map.setIcon(vectorToBitmap(R.drawable.bmx, Color.parseColor("#7f8c8d")));
                            break;
                        case 14:
                            map.setIcon(vectorToBitmap(R.drawable.rent, Color.parseColor("#c0392b")));
                            break;
                        default:
                            break;
                    }
                }

                Log.d("Create Marker", location.get(i).getName());
            }
            mMap.setOnInfoWindowClickListener(this);
        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_LONG).show();
        }

    }

    private BitmapDescriptor vectorToBitmap(@DrawableRes int id, @ColorInt int color) {
        Drawable vectorDrawable = ResourcesCompat.getDrawable(getResources(), id, null);
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        DrawableCompat.setTint(vectorDrawable, color);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("locationLat", String.valueOf(marker.getPosition().latitude));
        intent.putExtra("locationLong", String.valueOf(marker.getPosition().longitude));
        startActivity(intent);
        Toast.makeText(this, String.valueOf(marker.getPosition().latitude) + " || " + String.valueOf(marker.getPosition().longitude), Toast.LENGTH_LONG).show();
    }
}
