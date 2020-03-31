package com.obiangetfils.kermashop.models;

public class UserOBJ {

    String user_firstname, user_lastName, user_username, user_password, user_phone;

    public UserOBJ() {
    }

    public UserOBJ(String user_firstname, String user_lastName, String user_username, String user_password, String user_phone) {
        this.user_firstname = user_firstname;
        this.user_lastName = user_lastName;
        this.user_username = user_username;
        this.user_password = user_password;
        this.user_phone = user_phone;
    }


    public String getUser_firstname() {
        return user_firstname;
    }

    public void setUser_firstname(String user_firstname) {
        this.user_firstname = user_firstname;
    }

    public String getUser_lastName() {
        return user_lastName;
    }

    public void setUser_lastName(String user_lastName) {
        this.user_lastName = user_lastName;
    }

    public String getUser_username() {
        return user_username;
    }

    public void setUser_username(String user_username) {
        this.user_username = user_username;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }
}
