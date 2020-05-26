package com.my.loopiine.utils.userdemo.wheelview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

import com.my.loopiine.R;

import java.util.Arrays;

/**
 * Created by Administrator on 2016/11/6.
 */

public class Usage {


    /***
     * <com.haiku.wateroffer.utils.views.wheelview.WheelView
         android:id="@+id/main_wv"
         android:layout_width="match_parent"
         android:layout_height="wrap_content"
         />
     * **/

    private static final String TAG = Usage.class.getSimpleName();
    private static final String[] PLANETS = new String[]{"Mercury", "Venus", "Earth", "Mars", "Jupiter", "Uranus", "Neptune", "Pluto"};


    //使用
    public void user(Context ctx){
        //WheelView wva = (WheelView) findViewById(R.id.main_wv);
        WheelView wva =new WheelView(ctx);
        wva.setOffset(1);
        wva.setItems(Arrays.asList(PLANETS));
        wva.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.d(TAG, "selectedIndex: " + selectedIndex + ", item: " + item);
            }
        });

    }


//点击事件
//@Override
public void onClick(Context ctx) {

        View outerView = LayoutInflater.from(ctx).inflate(R.layout.wheel_view, null);
        WheelView wv = (WheelView) outerView.findViewById(R.id.wheel_view_wv);
        wv.setOffset(2);
        wv.setItems(Arrays.asList(PLANETS));
        wv.setSeletion(3);
        wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.d(TAG, "[Dialog]selectedIndex: " + selectedIndex + ", item: " + item);
            }
        });

        new AlertDialog.Builder(ctx)
                .setTitle("WheelView in Dialog")
                .setView(outerView)
                .setPositiveButton("OK", null)
                .show();


}

    private PopupWindow popupWindow;

    public void animations(Activity ctx){
        View popupWindowView = ctx.getLayoutInflater().inflate(R.layout.demo_pop, null);
        //内容，高度，宽度
        popupWindow = new PopupWindow(popupWindowView, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, true);
        popupWindow.setAnimationStyle(R.style.AnimationBottomFade);
        //菜单背景色
        ColorDrawable dw = new ColorDrawable(0xffffffff);
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.showAtLocation(ctx.getLayoutInflater().inflate(R.layout.activity_main, null), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
        //设置背景半透明
        //backgroundAlpha(0.5f);
        //关闭事件
        popupWindow.setOnDismissListener(new popupDismissListener());

        popupWindowView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
				/*if( popupWindow!=null && popupWindow.isShowing()){
					popupWindow.dismiss();
					popupWindow=null;
				}*/
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
                return false;
            }
        });

        //Button open = (Button)popupWindowView.findViewById(R.id.open);

    }


    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     *
     */
    class popupDismissListener implements PopupWindow.OnDismissListener{
        @Override
        public void onDismiss() {
            //backgroundAlpha(1f);
        }
    }
    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
//    public void backgroundAlpha(float bgAlpha)
//    {
//        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        lp.alpha = bgAlpha; //0.0-1.0
//        getWindow().setAttributes(lp);
//    }

}
