package com.my.loopiine.utils.userdemo.annotation.activity;

import com.my.loopiine.R;

@ActivityFragmentInject(contentViewId = R.layout.demo_bufferknife,
        menuId = R.menu.activity_main_drawer,
        hasNavigationView = true,
        toolbarTitle = R.string.app_name,
        toolbarIndicator = R.drawable.a,
        menuDefaultCheckedItem = R.id.add)
public class AnnotationActivity extends BaseActivity {



}
