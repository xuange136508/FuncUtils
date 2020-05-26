package com.my.loopiine.ui.typeImageList.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ============================================================
 * 项目名称：Girls
 * 包名称：com.flyou.girls.ui.typeImageList.domain
 * 文件名：TypeImageDomain
 * 类描述：
 * 创建人：flyou
 * 邮箱：fangjaylong@gmail.com
 * 创建时间：2016/4/20 14:36
 * 修改备注：
 * 版本：@version  V1.0
 * ============================================================
 **/
public class TypeImageDomain2 implements Parcelable {
    private  String imageUrl;




    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public TypeImageDomain2(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imageUrl);
    }

    protected TypeImageDomain2(Parcel in) {
        this.imageUrl = in.readString();
    }

    public static final Creator<TypeImageDomain2> CREATOR = new Creator<TypeImageDomain2>() {
        @Override
        public TypeImageDomain2 createFromParcel(Parcel source) {
            return new TypeImageDomain2(source);
        }

        @Override
        public TypeImageDomain2[] newArray(int size) {
            return new TypeImageDomain2[size];
        }
    };
}
