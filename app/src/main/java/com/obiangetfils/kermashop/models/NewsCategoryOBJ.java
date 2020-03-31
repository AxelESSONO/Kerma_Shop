package com.obiangetfils.kermashop.models;

public class NewsCategoryOBJ {

    private int id;
    private String title;
    private int imageID;
    private String time;
    private int description;

    public NewsCategoryOBJ(int id, String title, int imageID, String time, int description) {
        this.id = id;
        this.title = title;
        this.imageID = imageID;
        this.time = time;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getDescription() {
        return description;
    }

    public void setDescription(int description) {
        this.description = description;
    }
}
