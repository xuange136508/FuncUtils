package com.my.loopiine.test;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.my.loopiine.R;


/**
 * Created by xx on 2016/12/2.
 */
public class VerticalView extends RelativeLayout implements View.OnClickListener {

    private Context mContext;
    private TextView tv_data;
    private Handler mHandler;


    public VerticalView(Context context) {
        this(context, null, null);
    }

    public VerticalView(Context context, AttributeSet attrs) {
        this(context, attrs, null);
    }

    public VerticalView(Context context, AttributeSet attrs, String status) {
        super(context, attrs);
        this.mContext = context;
        initView(context);
        initData();
    }


    private void initView(Context context) {
        View.inflate(context, R.layout.vertical_view, this);
        tv_data = (TextView) findViewById(R.id.tv_data);
        tv_data.setOnClickListener(this);

    }

    private int mTvHeight = 30;
    private int mAllHeight = 60;

    private void initData() {
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTvHeight = tv_data.getHeight();
                mAllHeight= getHeight();
                animaterStart();
            }
        },2000);

    }

    private void animaterStart() {
        ValueAnimator transXAnimator = ValueAnimator.ofInt(mTvHeight, 0);
        transXAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                tv_data.layout(0, value,tv_data.getWidth(),value + tv_data.getHeight());
            }
        });
        transXAnimator.setDuration(3000);
        transXAnimator.setInterpolator(new LinearInterpolator());
        transXAnimator.start();
        ValueAnimator alphaXAnimator = ValueAnimator.ofFloat(1.0f, 0f);
        alphaXAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                tv_data.setAlpha(value);
                if(value==0){
//                    mHandler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
                            animaterStartReset();
//                        }
//                    },2000);
                }
            }
        });
        alphaXAnimator.setDuration(1500);
        alphaXAnimator.setInterpolator(new LinearInterpolator());
        alphaXAnimator.start();
    }


    private void animaterStartReset() {
        ValueAnimator transXAnimator = ValueAnimator.ofInt(mAllHeight,mTvHeight);
        transXAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                tv_data.layout(0, value,tv_data.getWidth(),value + tv_data.getHeight());
            }
        });
        transXAnimator.setDuration(3000);
        transXAnimator.setInterpolator(new LinearInterpolator());
        transXAnimator.start();
        ValueAnimator alphaXAnimator = ValueAnimator.ofFloat(0f, 1.0f);
        alphaXAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                tv_data.setAlpha(value);
                if(value==1){
//                    mHandler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
                            animaterStart();
//                        }
//                    },2000);
                }
            }
        });
        alphaXAnimator.setDuration(1500);
        alphaXAnimator.setInterpolator(new LinearInterpolator());
        alphaXAnimator.start();
    }


    public int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_data:

                break;
        }
    }

}
