package com.obiangetfils.kermashop.models;

public class NewsObj {

    private int Id;
    private int image;
    private String title;
    private String posts;

    public NewsObj(int id, int image, String title, String posts) {
        Id = id;
        this.image = image;
        this.title = title;
        this.posts = posts;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosts() {
        return posts;
    }

    public void setProducts(String posts) {
        this.posts = posts;
    }
}
