package com.my.loopiine.ui.main.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ============================================================
 * Domain范围，领域  bean 网络请求返回的数据(实现了序列化)
 * ============================================================
 **/
public class AVListDomain implements Parcelable {
    private  String imageUrl;

    private String parentUrl;

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParentUrl() {
        return parentUrl;
    }

    public void setParentUrl(String parentUrl) {
        this.parentUrl = parentUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public AVListDomain(String imageUrl,String parentUrl, String title) {
        this.parentUrl = parentUrl;
        this.imageUrl = imageUrl;
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.parentUrl);
        dest.writeString(this.imageUrl);
        dest.writeString(this.title);
    }

    protected AVListDomain(Parcel in) {
        this.parentUrl = in.readString();
        this.imageUrl = in.readString();
        this.title = in.readString();
    }

    public static final Creator<AVListDomain> CREATOR = new Creator<AVListDomain>() {
        @Override
        public AVListDomain createFromParcel(Parcel source) {
            return new AVListDomain(source);
        }

        @Override
        public AVListDomain[] newArray(int size) {
            return new AVListDomain[size];
        }
    };
}
