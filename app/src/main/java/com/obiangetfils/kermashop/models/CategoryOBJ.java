package com.obiangetfils.kermashop.models;

public class CategoryOBJ {

    int image;
    int icon;
    String name;
    int count;

    public CategoryOBJ(int image, int icon, String name, int count) {
        this.image = image;
        this.icon = icon;
        this.name = name;
        this.count = count;
    }

    public int getImage() {
        return image;
    }

    public int getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

}

