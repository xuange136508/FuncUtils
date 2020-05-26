package com.my.loopiine.ui.main.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ============================================================
 * Domain范围，领域  bean 网络请求返回的数据(实现了序列化)
 * ============================================================
 **/
public class VideoListDomain implements Parcelable {

    private  String videoUrl;

    private String imageUrl;

    private String parentUrl;

    private String title;

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getParentUrl() {
        return parentUrl;
    }

    public void setParentUrl(String parentUrl) {
        this.parentUrl = parentUrl;
    }

    public VideoListDomain(String imageUrl, String title, String parentUrl,String videoUrl) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.parentUrl = parentUrl;
        this.videoUrl = videoUrl;
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
        dest.writeString(this.videoUrl);

    }

    protected VideoListDomain(Parcel in) {
        this.parentUrl = in.readString();
        this.imageUrl = in.readString();
        this.title = in.readString();
        this.videoUrl = in.readString();
    }

    public static final Creator<VideoListDomain> CREATOR = new Creator<VideoListDomain>() {
        @Override
        public VideoListDomain createFromParcel(Parcel source) {
            return new VideoListDomain(source);
        }

        @Override
        public VideoListDomain[] newArray(int size) {
            return new VideoListDomain[size];
        }
    };
}
