package com.test.jar;

import android.util.Log;

/**
 * Created by xx on 2017/5/4.
 */

public class CallTest {

    public static int getNumber(int a,int b){
        return a+b;
    }


    public CallTest() {
        Log.d("test", "CallTest:constructor called");
    }

    public void doSomething() {
        Log.d("test", "CallTest:doSomething called");
    }

}
