package com.my.loopiine.test;

import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.my.loopiine.R;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.my.loopiine.utils.userdemo.gif.view.BothLineProgress;

/**
 * Created by xx on 2017/5/4.
 */

public class TestActivity extends AppCompatActivity  {

    private Button mBtn;
    private ImageView mIvGif;

    private String mBaseUrl = "https://www.9itv.com.cn/jitvui/newPage/tv/VideoDetails.html?nextPage=1&videoId=A709CE7D488E45DC94C5E17DFCCA7B06&playData=&recommendNum=&firstN=website&secondNum=2&appVersion=188&packageName=com.zhiguan.m9ikandian";
    private String resourceId;

    private BothLineProgress bothLineProgress;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_test1);


        ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);

        initView();
        initData();
    }

    private void initView() {
        bothLineProgress = (BothLineProgress)findViewById(R.id.mybp);
        mIvGif = (ImageView)findViewById(R.id.iv_gif);
        mBtn = (Button) findViewById(R.id.btn_change);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //录屏
                // new GifLoadUtils().createGif(TestActivity.this,"test",300);
                //截屏
                //startScreenShot();

                //获取最近使用应用列表
                //getLastUseAppList();

                getExternalData();
            }
        });
    }

    private void getExternalData() {


        // query database
        String volumeName = "external";//internal external

        Uri uri = MediaStore.Audio.Media.getContentUri(volumeName);
        refreshMediaCategory( uri);

        uri = MediaStore.Video.Media.getContentUri(volumeName);
        refreshMediaCategory( uri);

        uri = MediaStore.Images.Media.getContentUri(volumeName);
        refreshMediaCategory( uri);
    }


    private boolean refreshMediaCategory( Uri uri) {
        String[] columns = new String[] {
                "COUNT(*)", "SUM(_size)"
        };
        Cursor c = getContentResolver().query(uri, columns, null, null, null);
        if (c == null) {
            Log.e("xxxx", "fail to query uri:" + uri);
            return false;
        }

        if (c.moveToNext()) {
            Log.v("xxxx", " info >>> count:" + c.getLong(0) + " size:" + c.getLong(1));
            c.close();
            return true;
        }

        return false;
    }


    private void initData() {
        //进度条
        startProgress();

    }



    private void startProgress() {
        //两边进度条
        bothLineProgress.startRunProgress(3000L, 10, BothLineProgress.MODE_CENTER_TO_BOTH);
        bothLineProgress.setOnBothLineProgressFinishListener(new BothLineProgress.OnBothLineProgressFinishListener() {
            @Override
            public void onFinished() {
                toast("录制完成");
            }
        });
    }


    //阴影控件
//        final ShadowLayout shadowLayout = (ShadowLayout) findViewById(R.id.sl);
//        shadowLayout.setIsShadowed(true);
//        shadowLayout.setShadowAngle(45);
//        shadowLayout.setShadowRadius(20);
//        shadowLayout.setShadowDistance(30);
//        shadowLayout.setShadowColor(Color.DKGRAY);


    //全匹配
    private void wholeMatch(){
        //Pattern pattern = Pattern.compile(".*videoId=(.*)&uniqueIdentifier.*");
        Pattern pattern = Pattern.compile(".*videoId=(.*?)&.*");
        Matcher matcher = pattern.matcher(mBaseUrl);
        boolean mRes = matcher.matches();
        if (mRes) {
            resourceId = matcher.group(1);
            Log.i("MovieDetailActivity", "videoId=" + resourceId);
        }
        if (TextUtils.isEmpty(resourceId)) {
            Log.i("MovieDetailActivity", "匹配失败");
        }
    }




    //正则匹配
    private void testMatch(String matchText){
        Pattern pattern = Pattern.compile("^我想看(.*)");
        Matcher matcher = pattern.matcher(matchText);
        boolean mRes = matcher.matches();
        if(mRes ){
            toast("匹配:"+matcher.group(1));
        }else{
            toast("匹配失败");
        }
    }
    private void testMatch2(String matchText){
        Pattern pattern2 = Pattern.compile("(.*)第.*集$");
        Matcher matcher2 = pattern2.matcher(matchText);
        boolean mRes2 = matcher2.matches();
        if(mRes2 ){
            toast("匹配:"+ matcher2.group(1));
        }else{
            toast("匹配失败");
        }
    }
    private void testMatch3(){
        Pattern pattern = Pattern.compile("(http://|https://){1}[//w//.//-/:]+");
         Matcher matcher = pattern.matcher("dsdsds<http://dsds//gfgffdfd>fdf");
         StringBuffer buffer = new StringBuffer();
        while(matcher.find()){
                 buffer.append(matcher.group());
                 buffer.append("/r/n");
         }
        toast(buffer.toString());
    }
    //替换标点字符串
    private void testFu(){
        String s = "年后.c。x，sc,aawf?";
        String str= s.replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……& amp;*（）——+|{}【】‘；：”“’。，、？|-]", "");
        toast(str);
    }


    private void toast(String text){
        Toast.makeText(this,text,Toast.LENGTH_LONG).show();
    }



    /**
     * 切图dialog分享[无法截surfaceView]
     */
    private void startScreenShot() {
        final AlertDialog cutDialog = new AlertDialog.Builder(this).create();
        View dialogView = View.inflate(this, R.layout.activity_screen_shot_demo, null);
        ImageView showImg = (ImageView) dialogView.findViewById(R.id.show_cut_screen_img);
        dialogView.findViewById(R.id.share_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cutDialog.dismiss();
            }
        });
        dialogView.findViewById(R.id.share_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TestActivity.this,"点击了share按钮",Toast.LENGTH_SHORT).show();
            }
        });
        //获取当前屏幕的大小
        int width = getWindow().getDecorView().getRootView().getWidth();
        int height = getWindow().getDecorView().getRootView().getHeight();
        //生成相同大小的图片
        Bitmap temBitmap = Bitmap.createBitmap( width, height, Bitmap.Config.ARGB_8888 );
        //找到当前页面的跟布局
        View view = getWindow().getDecorView().getRootView();
        //设置缓存
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        //从缓存中获取当前屏幕的图片
        temBitmap = view.getDrawingCache();
        showImg.setImageBitmap(temBitmap);

        cutDialog.setView(dialogView);
        Window window = cutDialog.getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager m = window.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.8); // 高度设置为屏幕的0.6
        p.gravity = Gravity.CENTER;//设置弹出框位置
        window.setAttributes(p);
        window.setWindowAnimations(R.style.dialogWindowAnim);
        cutDialog.show();
    }



}
