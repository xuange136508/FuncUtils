package com.my.loopiine.utils.userdemo.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.my.loopiine.R;
import com.my.loopiine.uitl.uitls2.MeasureUtil;
import com.my.loopiine.uitl.uitls2.ViewUtil;

public class CollapsToolbarActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            // 设置全屏，并且不会Activity的布局让出状态栏的空间
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            ViewUtil.showStatusBar(this);
        }
        super.onCreate(savedInstanceState);

        setContentView(R.layout.demo_collasping_toolbar);

        initView();
    }


    protected void initView() {

        final CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolbarLayout.setTitle("今天天气好");
        toolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.accent));
        toolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.material_white));

        //沉浸状态栏效果
        materialCollapsingForKitkat(toolbarLayout);

        //设置toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        //设置toolbar标题
        setToolbarTitle("新闻");
        //设置toolbar 指示器按钮样式
        setToolbarIndicator(R.mipmap.ic_list_white);
        }


    }


    protected void setToolbarTitle(String str) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(str);
        }
    }

    protected void setToolbarTitle(int strId) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(strId);
        }
    }

    protected void setToolbarIndicator(int resId) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(resId);
        }
    }


    /**
     * 4.4设置全屏并动态修改Toolbar的位置实现类5.0效果，确保布局延伸到状态栏的效果
     *
     * @param toolbarLayout
     */
    private void materialCollapsingForKitkat(final CollapsingToolbarLayout toolbarLayout) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {

            // 设置Toolbar对顶部的距离
            final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            final FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) toolbar
                    .getLayoutParams();
            layoutParams.topMargin = MeasureUtil.getStatusBarHeight(this);

            /*// 算出伸缩移动的总距离
            final AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
            final int[] verticalMoveDistance = new int[1];
            toolbarLayout.getViewTreeObserver()
                    .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            verticalMoveDistance[0] = toolbarLayout
                                    .getMeasuredHeight() - MeasureUtil
                                    .getToolbarHeight(NewsDetailActivity.this);
                            toolbarLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                    });
            appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                int lastVerticalOffset = 0;

                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    // KLog.e(lastVerticalOffset + ";" + verticalOffset);
                    if (lastVerticalOffset != verticalOffset && verticalMoveDistance[0] != 0) {
                        layoutParams.topMargin = (int) (statusBarHeight - Math
                                .abs(verticalOffset) * 1.0f / verticalMoveDistance[0] * statusBarHeight);
                        // KLog.e(layoutParams.topMargin);
                        toolbar.setLayoutParams(layoutParams);
                    }
                    lastVerticalOffset = verticalOffset;
                }
            });*/
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        }
    }

}
