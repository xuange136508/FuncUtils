package com.my.loopiine.utils.userdemo.gif.screenshot;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by xiexuan on 2017/10/18.
 */

/*
    * 这个类就是加工了SurfaceView之后的类，所有要运动的物件都最终放在这里进行绘制
    */
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private Thread thread; // SurfaceView通常需要自己单独的线程来播放动画
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;


    public MySurfaceView(Context context) {
        super(context);
        this.surfaceHolder = this.getHolder();
        this.surfaceHolder.addCallback(this);
    }


    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.surfaceHolder = this.getHolder();
        this.surfaceHolder.addCallback(this);
    }


    @Override
    public void run() {
        while (true) {
            canvas = this.surfaceHolder.lockCanvas(); // 通过lockCanvas加锁并得到該SurfaceView的画布
            canvas.drawColor(Color.BLUE);
            this.surfaceHolder.unlockCanvasAndPost(canvas); // 释放锁并提交画布进行重绘
            try {
                Thread.sleep(100); // 这个就相当于帧频了，数值越小画面就越流畅
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        this.thread = new Thread(this);
        this.thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        // 这里是SurfaceView发生变化的时候触发的部分
    }
}