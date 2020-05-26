package com.my.loopiine.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.my.loopiine.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;

import dalvik.system.DexClassLoader;

/**
 * Created by xx on 2017/5/4.
 */

public class TestActivity2 extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new VerticalView(this));
//        setContentView(R.layout.act_test2);
//        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });


        //1加载jar
//        loadJar();

        //2加载so
        //(1)正常加载
//        String res = new JNITest().test();
//        toast(res);
        //(2)加载assets文件夹下
//        File dir = this.getDir("jniLibs", Activity.MODE_PRIVATE);
//        File distFile = new File(dir.getAbsolutePath() + File.separator + "libJNISample.so");
//        if (copyFileFromAssets(this, "libJNISample.so", distFile.getAbsolutePath())){
//            //使用load方法加载内部储存的SO库
//            System.load(distFile.getAbsolutePath());
//            String res = new JNITest().test();
//            toast(res);
//        }
        //(3)直接加载sd卡
//        File dir = this.getDir("jniLibs", Activity.MODE_PRIVATE);
//        File distFile = new File(dir.getAbsolutePath() + File.separator + "libJNISample.so");
//        if (copyFileFromExtentions(this, "libJNISample.so", distFile.getAbsolutePath())){
//            //使用load方法加载内部储存的SO库
//            System.load(distFile.getAbsolutePath());
//            String res = new JNITest().test();
//            toast(res);
//        }


        //3加载插件


        //4加载SDK方案
//        LoadApkInfo();-->click();


    }

    private void toast(String str){
        Toast.makeText(this,str,Toast.LENGTH_LONG).show();
    }

    //----------------------------------------【1】------------------------------------------------
    //注意：在实践中发现，自己新建一个Java工程然后导出jar是无法使用的
    //将打包好的jar拷贝到SDK安装目录android-sdk-windows\platform-tools下，DOS进入这个目录，执行命名：
    //dx --dex --output=dxtest.jar mysdk.jar
    //或者
    //不能忘记使用dx命令对test.jar处理，否则的话会报Java.lang.ClassNotFoundException异常.dx命令位于sdk\build-tools\android-4.4W下面
    // （android-4.4W是android的一个版本，其他android版本下面也能找到dx.bat）. cmd命令行模式下进入dx.bat所在目录，
    // 使用dx --dex --output=dynamic.jar test.jar ,dynamic.jar是对test.jar处理后得到的jar文件.
    private void loadJar() {
//        final File optimizedDexOutputPath = new File(Environment.getExternalStorageDirectory()+ File.separator + "mysdk.jar");
//        DexClassLoader cl = new DexClassLoader(optimizedDexOutputPath.getAbsolutePath(),
//                Environment.getExternalStorageDirectory().toString(), null, getClassLoader());
//        Class libClazz = null;
//        try {
//            // 载入从网络上下载的类
//            libClazz = cl.loadClass("");
//            CallTest lib = (CallTest)libClazz.newInstance();
//            Toast.makeText(this, lib.helloWorld(), Toast.LENGTH_SHORT).show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        String libPath = Environment.getExternalStorageDirectory()+ File.separator + "dxtest.jar"; // 要动态加载的jar
        File dexDir = getDir("dex", MODE_PRIVATE); // 优化后dex的路径
        /**
         * 进行动态加载，利用java的反射调用com.test.dynamic.MyClass的方法
         */
        DexClassLoader classLoader = new DexClassLoader(libPath, dexDir.getAbsolutePath(), null, getClassLoader());
        try {
            Class<Object> cls = (Class<Object>)
                    classLoader.loadClass("com.test.jar.CallTest");
            Object object = cls.newInstance();
            Method method = cls.getMethod("doSomething",  new  Class[ 0 ]);
            method.invoke(object, new  Object[]{});
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    //---------------------------------【2】--------------------------------------
    //从assets复制文件到sd
    public static boolean copyFileFromAssets(Context context, String fileName, String path) {
        boolean copyIsFinish = false;
        try {
            InputStream is = context.getAssets().open(fileName);
            File file = new File(path);
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] temp = new byte[1024];
            int i = 0;
            while ((i = is.read(temp)) > 0) {
                fos.write(temp, 0, i);
            }
            fos.close();
            is.close();
            copyIsFinish = true;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("MainActivity", "[copyFileFromAssets] IOException "+e.toString());
        }
        return copyIsFinish;
    }
    //外部sd卡复制到内部存储
    public static boolean copyFileFromExtentions(Context context, String fileName, String path) {
        boolean copyIsFinish = false;
        try {
            FileInputStream is = new FileInputStream(new File(Environment.getExternalStorageDirectory(),fileName));
            File file = new File(path);
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] temp = new byte[1024];
            int i = 0;
            while ((i = is.read(temp)) > 0) {
                fos.write(temp, 0, i);
            }
            fos.close();
            is.close();
            copyIsFinish = true;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("MainActivity", "[copyFileFromAssets] IOException "+e.toString());
        }
        return copyIsFinish;
    }





}
