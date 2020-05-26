package com.my.loopiine.utils.userdemo.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.my.loopiine.R;
/**
 * 简单使用工具
 *
 * 有关图片质量RGB-565 转 RGB-8888
 * http://blog.csdn.net/theone10211024/article/details/45557859
 */

public class GlideDemo {

    public static void loadImage(Context ctx,String url,ImageView iv){
        Glide.with(ctx).load(url).asBitmap().animate(R.anim.image_load)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_loading)
                .error(R.mipmap.ic_fail)
                .into(iv);

        //居中适应imageview平铺 .centerCrop()
        //注意： 圆角/圆形 和centerCrop()不能同时使用

        //1 圆角
        RequestManager glideRequest = Glide.with(ctx);
        glideRequest.load(url)
                .transform(new CornersTransform(ctx))
                .into(iv);

        //2 圆形
        RequestManager glideRequest2 = Glide.with(ctx);
        glideRequest2.load(url)
                .transform(new CircleTransform(ctx))
                .into(iv);
    }



}
