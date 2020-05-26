package com.my.loopiine.utils.userdemo.annotation.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.my.loopiine.R;
import com.my.loopiine.uitl.uitls2.SlidrUtil;
import com.my.loopiine.uitl.uitls2.SpUtil;
import com.my.loopiine.utils.userdemo.views.slidr.model.SlidrInterface;

@ActivityFragmentInject(contentViewId = R.layout.demo_bufferknife,
        menuId = R.menu.activity_main_drawer,
        hasNavigationView = true,
        toolbarTitle = R.string.app_name,
        toolbarIndicator = R.drawable.a,
        menuDefaultCheckedItem = R.id.add)
public class BaseActivity extends AppCompatActivity {

    /** 标示该activity是否可滑动退出,默认false*/
    protected boolean mEnableSlidr;
    /**布局的id*/
    protected int mContentViewId;
    /** 是否存在NavigationView*/
    protected boolean mHasNavigationView;
    /**菜单的id*/
    private int mMenuId;
    /**Toolbar标题*/
    private int mToolbarTitle;
    /**默认选中的菜单项*/
    private int mMenuDefaultCheckedItem;
    /**Toolbar左侧按钮的样式*/
    private int mToolbarIndicator;

    /**
     * 控制滑动与否的接口
     */
    protected SlidrInterface mSlidrInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getClass().isAnnotationPresent(ActivityFragmentInject.class)) {
            ActivityFragmentInject annotation = getClass()
                    .getAnnotation(ActivityFragmentInject.class);
            mContentViewId = annotation.contentViewId();
            mEnableSlidr = annotation.enableSlidr();
            mHasNavigationView = annotation.hasNavigationView();
            mMenuId = annotation.menuId();
            mToolbarTitle = annotation.toolbarTitle();
            mToolbarIndicator = annotation.toolbarIndicator();
            mMenuDefaultCheckedItem = annotation.menuDefaultCheckedItem();
        } else {
            throw new RuntimeException(
                    "Class must add annotations of ActivityFragmentInitParams.class");
        }


        setContentView(mContentViewId);


        //是否开启侧滑返回功能
        if (mEnableSlidr && !SpUtil.readBoolean("disableSlide")) {
            // 默认开启侧滑，默认是整个页码侧滑
            mSlidrInterface = SlidrUtil
                    .initSlidrDefaultConfig(this, SpUtil.readBoolean("enableSlideEdge"));
        }
        //写入
        SpUtil.writeBoolean("disableSlide", false);
        SpUtil.writeBoolean("enableSlideEdge", true);

    }


}
