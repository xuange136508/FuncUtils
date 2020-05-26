package com.my.loopiine.app;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.my.loopiine.BuildConfig;
import com.socks.library.KLog;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.GINGERBREAD;

public class App extends Application {


    private static Context sApplicationContext;

    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplicationContext = this;
        KLog.init(BuildConfig.LOG_DEBUG);
        //严苛模式
//        enabledStrictMode();
        //内存泄漏监听
        refWatcher = LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(Context context) {
        App application = (App) context.getApplicationContext();
        return application.refWatcher;
    }

    private void enabledStrictMode() {
        if (SDK_INT >= GINGERBREAD) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder() //
                    .detectAll() //
                    .penaltyLog() //
                    .penaltyDeath() //
                    .build());
        }
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    // 获取ApplicationContext
    public static Context getContext() {
        return sApplicationContext;
    }

}
