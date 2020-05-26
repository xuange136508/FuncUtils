package com.my.loopiine.FunVideo;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/7.
 */

public class FunVideoList implements Serializable {

    private String imgUrl;
    private String title;
    private String playUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }
}
