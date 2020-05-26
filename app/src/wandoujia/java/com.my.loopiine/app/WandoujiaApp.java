package com.my.loopiine.app;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import com.my.loopiine.BuildConfig;
import com.socks.library.KLog;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.GINGERBREAD;

public class WandoujiaApp extends Application {


    private static Context sApplicationContext;


    @Override
    public void onCreate() {
        super.onCreate();
        sApplicationContext = this;
        Log.i("test","WandoujiaApp");

    }


    // 获取ApplicationContext
    public static Context getContext() {
        return sApplicationContext;
    }

}
