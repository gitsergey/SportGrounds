/*
 * Created by Sergey Parfinovich on 27.06.17 12:19
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 02.06.17 17:15
 */

package com.example.sergey.sportgrounds.model;


public class SignupRequest {

    private String name;
    private String email;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
