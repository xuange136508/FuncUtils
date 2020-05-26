package com.my.loopiine.ui.main.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ============================================================
 * Domain范围，领域  bean 网络请求返回的数据(实现了序列化)
 * ============================================================
 **/
public class NoteListDomain implements Parcelable {

    private  String text;

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public NoteListDomain(String text, String title, String parentUrl) {
        this.text = text;
        this.title = title;
        this.parentUrl = parentUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.parentUrl);
        dest.writeString(this.text);
        dest.writeString(this.title);
    }

    protected NoteListDomain(Parcel in) {
        this.parentUrl = in.readString();
        this.text = in.readString();
        this.title = in.readString();
    }

    public static final Creator<NoteListDomain> CREATOR = new Creator<NoteListDomain>() {
        @Override
        public NoteListDomain createFromParcel(Parcel source) {
            return new NoteListDomain(source);
        }

        @Override
        public NoteListDomain[] newArray(int size) {
            return new NoteListDomain[size];
        }
    };
}
