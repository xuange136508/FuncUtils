package com.my.loopiine.utils.userdemo.animation.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

import com.my.loopiine.R;

/**
 * 界面跳转动画demo
 */

public class ActivityAnimationDemo {

    public void demo(Context ctx , Class democlass, View view, Activity activity){

        Intent intent = new Intent(ctx, democlass);
        //如果大于5.0版本采用共享动画跳转
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(activity,
                            view.findViewById(R.id.add), "photos");
            ctx.startActivity(intent, options.toBundle());
        } else {
            //让新的Activity从一个小的范围扩大到全屏
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeScaleUpAnimation(view, view.getWidth() / 2,
                            view.getHeight() / 2, 0, 0);
            ActivityCompat.startActivity(activity, intent, options.toBundle());
        }

    }

    /**
     * "photos"为共享的属性名
     *  <ImageView
             android:id="@+id/add"
             android:layout_width="72dp"
             android:layout_height="72dp"
             android:layout_margin="4dp"
             android:scaleType="centerCrop"
             android:transitionName="photos"  //---->
             tools:src="@drawable/ic_header"/>
     *
     *新页面的控件要具有相同属性
     * <ImageView
         android:id="@+id/iv_news_detail_photo"
         android:layout_width="match_parent"
         android:layout_height="match_parent"
         android:fitsSystemWindows="true"
         android:scaleType="centerCrop"
         android:transitionName="photos"  //--->
         app:layout_collapseMode="parallax"
         app:layout_collapseParallaxMultiplier="0.7"
         tools:src="@drawable/ic_header"/>
     *
     *
     * ***/



}
