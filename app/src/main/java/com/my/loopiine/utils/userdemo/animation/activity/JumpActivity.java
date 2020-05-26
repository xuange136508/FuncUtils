package com.my.loopiine.utils.userdemo.animation.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.my.loopiine.R;
import com.my.loopiine.ui.MainActivity;

/**
 * Activity跳转动画 (背景灰色弹窗)
 */

public class JumpActivity extends  Activity{


    private void demo(Activity ctx){
        Intent xIntent = new Intent(this,MainActivity.class);
        startActivity(xIntent);
        overridePendingTransition(R.anim.fade, R.anim.hold);
    }

    //而MainActivity加入
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade, R.anim.hold);
    }


    // Activity半透明效果设置
    //1 布局 最外层标签加入
    /* android:background="@android:color/transparent" **/
    //2 设置清单文件
    /*
          <activity
             android:name="com.haiku.waterbuyer.activity.GuigeActivity"
             android:theme="@style/translucent">
         </activity>
     */
    //3 styles.xml
    /*
        * <!-- t透明度 -->
        <style name="translucent">
            <item name="android:windowBackground">@color/translucent_background2</item>
            <item name="android:windowIsTranslucent">true</item>
            <item name="android:windowNoTitle">true</item>
        </style>
    * */
    //4 color.xml
    /*
        * <!-- 透明度 -->
        <color name="translucent_background">#eb000000</color>
        <color name="translucent_background2">#99000000</color>
        <color name="translucent_background3">#00000000</color>
    * */


}
