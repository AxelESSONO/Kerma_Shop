package com.obiangetfils.kermashop.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ImagesProducts implements Parcelable {
    String imageUri;

    public ImagesProducts(String imageUri) {
        this.imageUri = imageUri;
    }

    protected ImagesProducts(Parcel in) {
        imageUri = in.readString();
    }

    public static final Creator<ImagesProducts> CREATOR = new Creator<ImagesProducts>() {
        @Override
        public ImagesProducts createFromParcel(Parcel in) {
            return new ImagesProducts(in);
        }

        @Override
        public ImagesProducts[] newArray(int size) {
            return new ImagesProducts[size];
        }
    };

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageUri);
    }
}
