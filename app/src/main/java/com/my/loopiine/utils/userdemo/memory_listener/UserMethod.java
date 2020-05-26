package com.my.loopiine.utils.userdemo.memory_listener;

import android.app.Activity;
import android.os.Bundle;

import com.my.loopiine.app.App;
import com.squareup.leakcanary.RefWatcher;

/**
 * 简介
 * http://blog.csdn.net/watermusicyes/article/details/46333925
 */

public class UserMethod extends Activity{

    private RefWatcher refWatcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    //建议放在BaseActivity或者BaseFragment
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //添加监听
        refWatcher = App.getRefWatcher(this);
        refWatcher.watch(this);
    }
}
