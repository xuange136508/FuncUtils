package com.my.loopiine.uitl;

import android.content.Context;
import android.os.Environment;

import static android.R.attr.data;

/**
 * Created by xx on 2017/6/6.
 */

public class AllPathUtils {



    // 可以看到，当SD卡存在或者SD卡不可被移除的时候，就调用getExternalCacheDir()方法来获取缓存路径，否则就调用getCacheDir()方法来获取缓存路径
    public String getDiskCacheDir(Context context) {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }


    private void loadPath(){


        /*
        Environment.getDataDirectory() = /data
        Environment.getDownloadCacheDirectory() = /cache
        Environment.getExternalStorageDirectory() = /mnt/sdcard
        Environment.getExternalStoragePublicDirectory(“test”) = /mnt/sdcard/test
        Environment.getRootDirectory() = /system
        getPackageCodePath() = /data/app/com.my.app-1.apk
        getPackageResourcePath() = /data/app/com.my.app-1.apk
        getCacheDir() = /data/data/com.my.app/cache
        getDatabasePath(“test”) = /data/data/com.my.app/databases/test
        getDir(“test”, Context.MODE_PRIVATE) = /data/data/com.my.app/app_test
        getExternalCacheDir() = /mnt/sdcard/Android/data/com.my.app/cache
        getExternalFilesDir(“test”) = /mnt/sdcard/Android/data/com.my.app/files/test
        getExternalFilesDir(null) = /mnt/sdcard/Android/data/com.my.app/files
        getFilesDir() = /data/data/com.my.app/files
        */


        /*
            获取so位置
            try {
                PackageManager pm = getPackageManager();
                ApplicationInfo infos = pm.getApplicationInfo(getPackageName(),PackageManager.GET_META_DATA);
                Log.i("path","So:"+infos.nativeLibraryDir);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        */
        // so存放路径1：(5.0以下) /data/app-lib/com.my.loopiine-1
        // so存放路径2：(5.0以上) data/app/packname-1(-2)/lib/arm/... [/data/app/com.my.loopiine-1/lib/arm]
        //通过PackageManager安装后，在小于Android 5.0的系统中，.so文件位于app的nativeLibraryPath目录中；
        //在大于等于Android 5.0的系统中，.so文件位于app的nativeLibraryRootDir/CPU_ARCH目录中。


//        通过Context.getExternalFilesDir()方法可以获取到 SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
//        通过Context.getExternalCacheDir()方法可以获取到 SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
//        而且上面二个目录分别对应 设置->应用->应用详情里面的”清除数据“与”清除缓存“选项

    }


}
