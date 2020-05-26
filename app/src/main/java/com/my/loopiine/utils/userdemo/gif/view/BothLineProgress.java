package com.my.loopiine.utils.userdemo.gif.view;

/**
 * Created by xiexuan on 2017/10/18.
 */

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

/**
 * 中间向两端或两端向中间并发进度条
 * @author Jaiky
 * @date 2015年7月1日
 * PS: Not easy to write code, please indicate.
 */
public class BothLineProgress extends LinearLayout {

    private Context context;

    private Handler handler = new Handler();

    private android.view.ViewGroup.LayoutParams layoutParams = null;


    //现在高度
    private float nowWidth = 0;
    //屏幕宽度
    private int screenWidth = 0;
    //行走距离
    private float runDistance = 1;
    //计时器
    private Timer timer;
    //回调接口
    private OnBothLineProgressFinishListener obpf;
    //模式，默认为中间到两端
    private int mode = MODE_CENTER_TO_BOTH;
    /**
     * 中间到两端延伸
     */
    public static final int MODE_CENTER_TO_BOTH = 0;

    /**
     * 两端到中间延伸
     */
    public static final int MODE_BOTH_TO_CENTER = 1;

    //计算耗时用的，可注释
    private long starttime = 0;

    public BothLineProgress(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public BothLineProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public BothLineProgress(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }


    private void init() {
        screenWidth = context.getResources().getDisplayMetrics().widthPixels;
//      setBackgroundColor(Color.parseColor("#ff8a00"));
    }


    /**
     * 开始启动进度条
     * @param duration 需要多少毫秒跑完进度 （1秒 = 1000毫秒）
     * @param runTime 频率，默认为10，越大更新频率越慢
     * @param mode 模式，可选（BothLineProgress.MODE_CENTER_TO_BOTH） 或（BothLineProgress.MODE_BOTH_TO_CENTER）
     */
    public void startRunProgress(long duration, int runTime, int mode) {
        this.mode = mode;
        if (runTime < 1)
            runTime = 10;
        layoutParams = getLayoutParams();
        nowWidth = layoutParams.width;

        //跑满屏幕或设置XML的进度条长度，需要每10毫秒跑多远距离（根据需求设置布局XML文件android:layout_width="0dp"）
        if (mode == MODE_CENTER_TO_BOTH) {
            runDistance = (float)(screenWidth - nowWidth) / ((float)duration / (float)runTime);
        }
        else {
            if (nowWidth <= 0) {
                nowWidth = screenWidth;
            }
            runDistance = (float)nowWidth / ((float)duration / (float)runTime);
        }

        Log.e("XXX", "每次跑：" + runDistance + "  开始宽度：" + nowWidth + "   频率：" + runTime);
        timer = new Timer();
        timer.schedule(new MyTimerTask(), 0, runTime);

        starttime = System.currentTimeMillis();
    }

    /**
     * 停止进度条
     */
    public void stopProgress(){
        if (timer != null) {
            timer.cancel();
        }
    }

    /**
     * 设置进度条颜色
     * @param colorString 颜色码，如“#000000”
     */
    public void setPorgressColor(String colorString) {
        setBackgroundColor(Color.parseColor(colorString));
    }

    //计时器任务
    private class MyTimerTask extends TimerTask{

        @Override
        public void run() {
            boolean isnext = true;
            if (mode == MODE_CENTER_TO_BOTH)
                isnext = (nowWidth < screenWidth) ? true : false;
            else
                isnext = (nowWidth > 0) ? true : false;

            if (isnext) {
                if (mode == MODE_CENTER_TO_BOTH)
                    nowWidth = nowWidth + runDistance;
                else
                    nowWidth = nowWidth - runDistance;
                Log.i("XXX", nowWidth + "");
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        //设置新布局
                        layoutParams.width = (int)nowWidth;
                        setLayoutParams(layoutParams);
                    }
                });
            }
            else {
                long c = System.currentTimeMillis() - starttime;
                Log.e("XXX", "时间：" + c);
                //回调，到UI线程更新
                if (obpf != null) {
                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            obpf.onFinished();
                        }
                    });
                }
                timer.cancel();
            }
        }

    }



    /**
     * 设置进度条完成回调接口
     * @param obpf
     */
    public void setOnBothLineProgressFinishListener(OnBothLineProgressFinishListener obpf) {
        this.obpf = obpf;
    }


    /**
     * 进度条处理完成回调接口
     * @author Jaiky
     * @date 2015年7月1日
     * PS: Not easy to write code, please indicate.
     */
    public interface OnBothLineProgressFinishListener {
        public void onFinished();
    }


    /**
     * DP转PX
     * @param dpValue
     * @return
     */
    public int dip2px(float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
