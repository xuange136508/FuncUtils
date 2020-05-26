package com.my.loopiine.test;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.my.loopiine.R;


/**
 * Created by xx on 2017/8/11.
 * http://www.jianshu.com/p/1728b725b4a6
 * 无敌的适配字体的控件
 使用教程：
 xmlns:fit="http://schemas.android.com/apk/res-auto"
 <com.my.loopiine.test.FitTextView
     android:id="@+id/ftv"
     android:layout_centerHorizontal="true"
     fit:fit_text="五月天"
     android:layout_width="wrap_content"
     android:layout_height="match_parent" />

 */

public class FitTextView extends View {

    public static final String MY_LOG = "MAYDAY";
    private Paint mPaint ;
    private String mText = "";
    private int mColor = Color.BLUE;


    public FitTextView(Context context) {
        this(context,null);
    }

    public FitTextView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public FitTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FitText);
        mText = typedArray.getString(R.styleable.FitText_fit_text);
        mColor = typedArray.getColor(R.styleable.FitText_fit_color, Color.BLUE);
        typedArray.recycle();

        initPaint();
    }


    public void setText(String text) {
        mText = text;
        invalidate();
        requestLayout();
    }

    public void setTextColor(int color) {
        mColor = color;
        mPaint.setColor(mColor);
        invalidate();
    }


    /**
     * 初始化画笔设置
     */
    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mPaint.setColor(Color.parseColor("#FF4081"));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPaint.setTextSize(h);
        mPaint.setColor(mColor);
        invalidate();
    }

    //根据字体绘制
    float stringWidth = 0;

    /**
     * 绘制
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        String text = mText;

        //文字的x轴坐标
        stringWidth = mPaint.measureText(text);
        float x = (getWidth() - stringWidth) / 2;
        //文字的y轴坐标
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        //【原本】
        //float y = getHeight() / 2 + (Math.abs(fontMetrics.ascent) - fontMetrics.descent) / 2;
        //【测试】
        float y = getHeight() / 2 + (Math.abs(fontMetrics.ascent) - fontMetrics.descent ) / 2;
        canvas.drawText(text, x, y, mPaint);
        Log.i(MY_LOG,"leading="+ fontMetrics.leading + " top="+ fontMetrics.top + " bottom="+ fontMetrics.bottom
                + " descent="+ fontMetrics.descent + " ascent="+ fontMetrics.ascent);
        //leading = 0.0 top = -65.56641 bottom = 16.259766 descent = 14.6484375 ascent = -55.664063
        Log.i(MY_LOG,"mText="+mText+" x="+x +" y="+y);
    }


    private int mTextValue = 0;
    /**
     * 测量
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(!TextUtils.isEmpty(mText)){
            mTextValue = (int)mPaint.measureText(mText);
            Log.i(MY_LOG,"mTextValue:"+mTextValue);
        }

        int wSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if (wSpecMode == MeasureSpec.AT_MOST && hSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(mTextValue,300);
            Log.i(MY_LOG,"step:1");
        }else  if (wSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(mTextValue,hSpecSize);
            Log.i(MY_LOG,"step:2");
        }else if (hSpecMode ==  MeasureSpec.AT_MOST) {
            setMeasuredDimension(wSpecSize,300);
            Log.i(MY_LOG,"step:3");
        }
    }

    public String getText(){
        return mText;
    }


}
