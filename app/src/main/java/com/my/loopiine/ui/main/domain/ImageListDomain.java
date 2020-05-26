package com.my.loopiine.ui.main.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ============================================================
 * Domain范围，领域  bean 网络请求返回的数据(实现了序列化)
 * ============================================================
 **/
public class ImageListDomain implements Parcelable {
    private String linkUrl;
    private  String imageUrl;
    private String imgaeTitle;

    @Override
    public String toString() {
        return "ImageListDomain{" +
                "linkUrl='" + linkUrl + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", imgaeTitle='" + imgaeTitle + '\'' +
                '}';
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImgaeTitle() {
        return imgaeTitle;
    }

    public void setImgaeTitle(String imgaeTitle) {
        this.imgaeTitle = imgaeTitle;
    }

    public ImageListDomain(String linkUrl, String imageUrl, String imgaeTitle) {
        this.linkUrl = linkUrl;
        this.imageUrl = imageUrl;
        this.imgaeTitle = imgaeTitle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.linkUrl);
        dest.writeString(this.imageUrl);
        dest.writeString(this.imgaeTitle);
    }

    protected ImageListDomain(Parcel in) {
        this.linkUrl = in.readString();
        this.imageUrl = in.readString();
        this.imgaeTitle = in.readString();
    }

    public static final Creator<ImageListDomain> CREATOR = new Creator<ImageListDomain>() {
        @Override
        public ImageListDomain createFromParcel(Parcel source) {
            return new ImageListDomain(source);
        }

        @Override
        public ImageListDomain[] newArray(int size) {
            return new ImageListDomain[size];
        }
    };
}
