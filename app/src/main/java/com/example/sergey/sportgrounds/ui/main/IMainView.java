package com.example.sergey.sportgrounds.ui.main;


import com.example.sergey.sportgrounds.model.Location;

import java.util.List;

public interface IMainView {
    void showProgress();
    void hideProgress();
    void createMarkerLocation(List<Location> location);
    void initNavHeader(String name, String email);
}
