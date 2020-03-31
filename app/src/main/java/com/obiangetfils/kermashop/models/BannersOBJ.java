package com.obiangetfils.kermashop.models;

public class BannersOBJ {
    String bannerTitle;
    Integer bannerImage;

    public BannersOBJ(String bannerTitle, Integer bannerImage) {
        this.bannerTitle = bannerTitle;
        this.bannerImage = bannerImage;
    }

    public String getBannerTitle() {
        return bannerTitle;
    }

    public void setBannerTitle(String bannerTitle) {
        this.bannerTitle = bannerTitle;
    }

    public Integer getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(Integer bannerImage) {
        this.bannerImage = bannerImage;
    }
}
