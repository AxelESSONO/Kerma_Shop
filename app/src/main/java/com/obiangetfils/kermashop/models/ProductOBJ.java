package com.obiangetfils.kermashop.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ProductOBJ implements Parcelable {

    List<ImagesProducts> imagesProductsList;
    String category, currentPrice, description, oldPrice, pid, pname, quantity;
    Boolean tagNew, tagOnSale;

    public ProductOBJ(List<ImagesProducts> imagesProductsList,
                      String category,
                      String currentPrice,
                      String description,
                      String oldPrice,
                      String pid,
                      String pname,
                      String quantity,
                      Boolean tagNew,
                      Boolean tagOnSale) {
        this.imagesProductsList = imagesProductsList;
        this.category = category;
        this.currentPrice = currentPrice;
        this.description = description;
        this.oldPrice = oldPrice;
        this.pid = pid;
        this.pname = pname;
        this.quantity = quantity;
        this.tagNew = tagNew;
        this.tagOnSale = tagOnSale;
    }

    protected ProductOBJ(Parcel in) {
        category = in.readString();
        currentPrice = in.readString();
        description = in.readString();
        oldPrice = in.readString();
        pid = in.readString();
        pname = in.readString();
        quantity = in.readString();
        byte tmpTagNew = in.readByte();
        tagNew = tmpTagNew == 0 ? null : tmpTagNew == 1;
        byte tmpTagOnSale = in.readByte();
        tagOnSale = tmpTagOnSale == 0 ? null : tmpTagOnSale == 1;
    }

    public static final Creator<ProductOBJ> CREATOR = new Creator<ProductOBJ>() {
        @Override
        public ProductOBJ createFromParcel(Parcel in) {
            return new ProductOBJ(in);
        }

        @Override
        public ProductOBJ[] newArray(int size) {
            return new ProductOBJ[size];
        }
    };

    public List<ImagesProducts> getImagesProductsList() {
        return imagesProductsList;
    }

    public void setImagesProductsList(List<ImagesProducts> imagesProductsList) {
        this.imagesProductsList = imagesProductsList;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Boolean getTagNew() {
        return tagNew;
    }

    public void setTagNew(Boolean tagNew) {
        this.tagNew = tagNew;
    }

    public Boolean getTagOnSale() {
        return tagOnSale;
    }

    public void setTagOnSale(Boolean tagOnSale) {
        this.tagOnSale = tagOnSale;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(category);
        dest.writeString(currentPrice);
        dest.writeString(description);
        dest.writeString(oldPrice);
        dest.writeString(pid);
        dest.writeString(pname);
        dest.writeString(quantity);
        dest.writeByte((byte) (tagNew == null ? 0 : tagNew ? 1 : 2));
        dest.writeByte((byte) (tagOnSale == null ? 0 : tagOnSale ? 1 : 2));
    }
}


