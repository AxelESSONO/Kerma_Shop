package com.obiangetfils.kermashop.models;

public class UserOBJ {

    String user_firstname, user_lastName, user_username, user_password, user_phone, user_type, user_id, user_image;

    public UserOBJ() {
    }

    public UserOBJ(String user_firstname, String user_lastName, String user_username, String user_password, String user_phone, String user_type, String user_id, String user_image) {
        this.user_firstname = user_firstname;
        this.user_lastName = user_lastName;
        this.user_username = user_username;
        this.user_password = user_password;
        this.user_phone = user_phone;

        this.user_type = user_type;
        this.user_id = user_id;
        this.user_image = user_image;
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

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }
}
