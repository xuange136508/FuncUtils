package com.my.loopiine.test;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView;

/**
 * 实现弹性webview的一种方案
 */

public class FlexiableWebView extends WebView {
    //移动因子,比如在你可以上拉或者下拉的时候,手指在屏幕上移动了200px,
    // 那么布局只移动50px,这个可以根据需求自己调整,越大,越容易拉动
    private static final float DISTANCE_SCALE = 0.25f;

    //动画执行的时间
    private static final long ANIM_TIME = 300;
    //是否能够下拉
    private boolean canPullDown = false;
    //是否能够上拉
    private boolean canPullUp = false;
    //布局是否移动过
    private boolean isMoved = false;
    //用来记录原始的布局位置信息,等下拉后者上拉后好还原成原来的位置
    private Rect rect = new Rect();
    //手指按下时的位置Y,如果手指滑动并没有移动,而是内容的滚动,则这个值实时更新为手指当前的位置Y
    private float startY = 0;
    //是不是第一次,该参数用于onlayout中,因为我发现WebView是ScrollView不一样,
    //WebView的onLayout方法会被连续调用,而ScrollView只调用一次
    private boolean isOnce = false;

    public FlexiableWebView(Context context) {
        this(context, null);
    }

    public FlexiableWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlexiableWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //指获取自一次的位置信息,后不在更改,这个地方在WebView中会执行多次,
        // 所以用一个boolean值来控制
        if (!isOnce) {
            rect.set(this.getLeft(), this.getTop(), this.getRight(), this.getBottom());
            isOnce = true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                //手指按下时初始化参数
                canPullDown = isCanPullDown();
                canPullUp = isCanPullUp();
                startY = e.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!canPullUp && !canPullDown) {
                    //跟定义参数的地方一样,如果不是有布局的下拉或者下拉引起的移动,
                    // 这参数实时更新为的当前手指所在的位置
                    startY = e.getY();
                    canPullDown = isCanPullDown();
                    canPullUp = isCanPullUp();
                    //获取参数后并调试判断
                    break;
                }
                //获取此刻手指的Y值,用于计算滑动的距离
                float nowY = e.getY();
                //手指在屏幕上滑动的距离
                int deltaY = (int) (nowY - startY);
                //判断是否应该移动布局
                //1.webView滑动到顶部且手指下拉
                //2.webView滑动到底部且手指上拉
                //3.webView的内容小于webview的高度
                boolean shouldMove = ((canPullDown && deltaY > 0) || (canPullUp && deltaY < 0) || (canPullUp && canPullDown));
                if (shouldMove) {
                    //利用滑动因子设置滑动的距离
                    int offset = (int) (deltaY * DISTANCE_SCALE);
                    //更新布局的位置
                    this.layout(rect.left, rect.top + offset, rect.right, rect.bottom + offset);
                    //布局已经更改
                    isMoved = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                //如果布局没有更改,则跳出判断
                if (!isMoved)
                    break;
                // 开启动画
                TranslateAnimation anim = new TranslateAnimation(0, 0, this.getTop(), rect.top);
                // 设置动画时间
                anim.setDuration(ANIM_TIME);
                // 给view设置动画
                this.setAnimation(anim);
                // 设置回到正常的布局位置
                this.layout(rect.left, rect.top, rect.right, rect.bottom);
                // 将标志位重置
                canPullDown = false;
                canPullUp = false;
                isMoved = false;
                break;
        }
        return super.onTouchEvent(e);
    }


    /**
     * 判断是否到达顶部,可以下拉
     *
     * @return
     */
    private boolean isCanPullDown() {
        return getScrollY() == 0;
    }

    /**
     * 判断是不死到达底部,可以上拉
     *
     * @return
     */
    private boolean isCanPullUp() {
        return ((int) (getContentHeight() * getScale())) <= (getHeight() + getScrollY());
    }
}
