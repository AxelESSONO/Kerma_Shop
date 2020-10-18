package com.obiangetfils.kermashop.models;

import java.util.List;

public class ImageProductBis {

    List<ImagesProducts> imagesProducts;
    boolean isProductClicked;

    public ImageProductBis(List<ImagesProducts> imagesProducts, boolean isProductClicked) {
        this.imagesProducts = imagesProducts;
        this.isProductClicked = isProductClicked;
    }

    public List<ImagesProducts> getImagesProducts() {
        return imagesProducts;
    }

    public void setImagesProducts(List<ImagesProducts> imagesProducts) {
        this.imagesProducts = imagesProducts;
    }

    public boolean isProductClicked() {
        return isProductClicked;
    }

    public void setProductClicked(boolean productClicked) {
        isProductClicked = productClicked;
    }
}
