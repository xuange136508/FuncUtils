package com.my.loopiine.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.my.loopiine.R;
import com.my.loopiine.bean.Person;
import com.my.testpackage.CallTest;

/**
 * Created by xx on 2017/5/4.
 * gradle使用
 * https://chenzhongjin.cn/2016/06/23/%E6%AD%A3%E7%A1%AE%E7%9A%84%E5%A7%BF%E5%8A%BF%E6%9D%A5%E4%BC%98%E5%8C%96%E4%BD%A0%E7%9A%84build-gradle/
 * gradle差异化构建
 * http://www.cnblogs.com/travellife/p/Gradle-shi-xian-Android-duo-qu-dao-ding-zhi-hua-da.html
 */

public class StartActivity extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        int res = CallTest.getNumber(2,3);
        Toast.makeText(this,"res="+res,Toast.LENGTH_LONG).show();
        Toast.makeText(this,getResources().getString(R.string.application_name),Toast.LENGTH_LONG).show();

        new Person();


    }



}
