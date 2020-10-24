package com.obiangetfils.kermashop.models;

import java.util.List;

public class CartOBJ {

    private ProductOBJ productOBJ;
    private String date;
    private String time;


    public CartOBJ(ProductOBJ productOBJ, String date, String time) {
        this.productOBJ = productOBJ;
        this.date = date;
        this.time = time;

    }

    public ProductOBJ getProductOBJ() {
        return productOBJ;
    }

    public void setProductOBJ(ProductOBJ productOBJ) {
        this.productOBJ = productOBJ;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
