/*
 * Created by Sergey Parfinovich on 27.06.17 12:19
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 21.06.17 17:57
 */

package com.example.sergey.sportgrounds.ui.main;


import com.example.sergey.sportgrounds.model.Location;

import java.util.List;

public interface IMainView {
    void showProgress();
    void hideProgress();
    void createMarkerLocation(List<Location> location);
    void initNavHeader(String name, String email);
    void showNavBarLoginOrExit(boolean userLogin);
}
