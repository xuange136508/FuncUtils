package com.my.loopiine.utils.userdemo.vitamio;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.my.loopiine.R;
import com.my.loopiine.ui.ImageDetialActivity;
import com.my.loopiine.uitl.uitls2.ViewUtil;
import com.my.loopiine.utils.userdemo.views.loading.ThreePointLoadingView;
import com.socks.library.KLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.VideoView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

//测试：
//http://c.m.163.com/nc/video/list/V9LG4B3A0/n/0-10.html
//http://flv2.bn.netease.com/videolib3/1610/30/mgCDA2121/HD/mgCDA2121-mobile.mp4
//AV:
//1 http://xljav.bb149.com/mp4/32038.mp4
//2 http://www.futporno.com/media/videos/iphone/1743.mp4
public class VideoPlayerActivity extends AppCompatActivity implements  View.OnTouchListener, View.OnClickListener{

    //注解使用
    @Bind(R.id.tpl_view)
    public ThreePointLoadingView mLoadingView;
    @Bind(R.id.video_view)
    public VideoView mVideoView;
    @Bind(R.id.rl_bg)
    public View mBgView;

    private VideoPlayController mPlayController;
    private BroadcastReceiver mBatInfoReceiver;

    private float mDownX;
    private float mDownY;

    /** 是否需要自动恢复播放，用于自动暂停，恢复播放 */
    private boolean needResume;

    private String sdcardString = Environment.getExternalStorageDirectory() + File.separator + "你懂的" + File.separator ;
    private int count = 1;

    //自己爬的
    String crawerUrl = "https://video.letv-cdn.com/ppvod/24A64F7F6F0CFD7F95FE7A70F2CF62BF.m3u8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_video_play);
        ButterKnife.bind(this);//进行动态绑定
        File file = new File(sdcardString);
        if(!file.exists()){
            file.mkdirs();
        }

        initView();
        registerScreenBroadCastReceiver();
        //playVideo("http://flv2.bn.netease.com/videolib3/1610/30/mgCDA2121/HD/mgCDA2121-mobile.mp4");
        //av1 (www.tttt77.com)
        //playVideo("http://xljav.bb149.com/mp4/32038.mp4");//OR +?jdfwkey=flqvn3
        //av2 (xvideo)
        //playVideo("http://www.futporno.com/media/videos/iphone/3867.mp4");
        //av3 (http://www.oge99.com/vodlist/?6.html)
        //playVideo("http://201606mp4.11bubu.com/20160705/ftn-034/1/xml/91_ff9ea7110d4b487589994172d1183c26.mp4");
        playVideo(crawerUrl);

        //下载
        //downloading("http://www.futporno.com/media/videos/iphone/3867.mp4");
        //downloading("http://xljav.bb149.com/mp4/32038.mp4");
    }


    protected void initView() {

        //   主题设置了<item name="android:windowIsTranslucent">true</item>不能自动旋转屏幕了，这里强制开启就可以了 -_-
        //this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        ViewUtil.setFullScreen(this);

        String videoUrl = getIntent().getStringExtra("videoUrl");

        mBgView = findViewById(R.id.rl_bg);
        mBgView.setOnTouchListener(this);

        mLoadingView = (ThreePointLoadingView) findViewById(R.id.tpl_view);
        mLoadingView.setOnClickListener(this);

        mVideoView = (VideoView) findViewById(R.id.video_view);
        mVideoView.setZOrderOnTop(true);
        mVideoView.getHolder().setFormat(PixelFormat.TRANSPARENT);

    }

    public void playVideo(String path) {
        if (Vitamio.isInitialized(getApplicationContext())) {

            mVideoView.setVideoPath(path);
            //设置缓存大小
            mVideoView.setBufferSize(512 * 1024);

            mPlayController = new VideoPlayController(this, mVideoView, mBgView);

            mVideoView.requestFocus();
            mVideoView.setOnTouchListener(this);

            mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.setPlaybackSpeed(1.0f);
                    hideProgress();
                }
            });
            mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    hideProgress();
                    toast("视频播放出错了╮(╯Д╰)╭");
                    //重新请求
                    Log.i("信息","重新请求:"+count+"次");
                    playVideo(path);
                    return true;
                }
            });
            //受限于网速等原因，播放网络视频时一般都会要加上缓冲处理，一般可以通过设置加大缓冲和显示正在缓冲的进度条来改善体验。
            mVideoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {
                    switch (what) {
                        case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                            //开始缓存，暂停播放
                            if (mVideoView.isPlaying()) {
                                mVideoView.pause();
                                needResume = true;
                            }
                            mLoadingView.setVisibility(View.VISIBLE);
                            break;
                        case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                            //缓存完成，继续播放
                            if (needResume)
                                mVideoView.start();
                            mLoadingView.setVisibility(View.GONE);
                            break;
                        case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                            //显示 下载速度
                            KLog.e("download rate:" + extra);
                            break;
                    }
                    return true;
                }
            });



        } else {
            toast("播放器还没初始化完哎，等等咯╮(╯Д╰)╭ ");
        }
    }

    // 注册监听屏幕亮闭屏
    public void registerScreenBroadCastReceiver() {
        final IntentFilter filter = new IntentFilter();
        // 屏幕灭屏广播
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        mBatInfoReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                if (mVideoView != null && mPlayController != null && mVideoView
                        .isPlaying() && intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                    // 屏幕关闭并且视频正在播放的时候，暂停播放
                    mVideoView.pause();
                    mPlayController.hide();
                } else if (mVideoView != null && mPlayController != null && intent.getAction()
                        .equals(Intent.ACTION_SCREEN_ON)) {
                    // 亮屏的时候，显示控制器
                    mPlayController.show();
                }
            }
        };
        registerReceiver(mBatInfoReceiver, filter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        mVideoView.setVisibility(View.INVISIBLE);
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rl_bg && mPlayController != null) {
            if (mPlayController.isShowing()) {
                mPlayController.hide();
            } else {
                mPlayController.show();
            }
        }
    }


    public void showProgress() {
        mLoadingView.play();
    }

    public void hideProgress() {
        mLoadingView.stop();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // 保持屏幕比例正确
        mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
        mPlayController.hide();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(mDownX - event.getX()) > 50 || Math.abs(mDownY - event.getY()) > 50) {
                    // 移动超过一定距离，ACTION_UP取消这次事件
                    mDownX = Integer.MAX_VALUE;
                    mDownY = Integer.MAX_VALUE;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mPlayController != null && Math.abs(mDownX - event.getX()) <= 50 && Math
                        .abs(mDownY - event.getY()) <= 50) {
                    // 解决与背景点击事件的冲突
                    if (mPlayController.isShowing()) {
                        mPlayController.hide();
                    } else {
                        mPlayController.show();
                    }
                }
                break;
        }
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPlayController != null) {
            mPlayController.onDestroy();
        }
        unregisterReceiver(mBatInfoReceiver);
    }


    public void toast(String msg) {
        showSnackbar(msg);
    }

    protected void showSnackbar(String msg) {
        Snackbar.make(getWindow().getDecorView(), msg, Snackbar.LENGTH_SHORT).show();
    }



    //新增文件下载
    private void downloading(String mImageurl){
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String fileName = System.currentTimeMillis() + ".mp4";
                File file = new File(Environment.getExternalStorageDirectory() + File.separator+"Girls", fileName);
                File dirs = new File(Environment.getExternalStorageDirectory() + File.separator+"Girls");

                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {
                    try {
                        if (!dirs.exists()){
                            dirs.mkdirs();
                        }
                        // 从网络上获取图片
                        URL url = new URL(mImageurl);
                        HttpURLConnection conn = null;
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setConnectTimeout(5000);
                        conn.setRequestMethod("GET");
                        conn.setDoInput(true);
                        //获取内容长度
                        int contentLength = conn.getContentLength();

                        if (conn.getResponseCode() == 200) {
                            InputStream is = conn.getInputStream();
                            FileOutputStream fos = new FileOutputStream(file);
                            byte[] buffer = new byte[1024];
                            int len = 0;
                            int total = 0;
                            while ((len = is.read(buffer)) != -1) {
                                fos.write(buffer, 0, len);
                                //获取进度
                                total += len;
                                long progress = total * 100 / contentLength;
                                Log.i("信息", "run: progress:" + progress);
                            }
                            is.close();
                            fos.close();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    subscriber.onNext(file.getAbsolutePath());
                } else {
                    Toast.makeText(VideoPlayerActivity.this, "没有发现sd卡，无法下载", Toast.LENGTH_SHORT).show();
                }

            }
        });
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
                Toast.makeText(VideoPlayerActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNext(String s) {
                Toast.makeText(VideoPlayerActivity.this, "下载成功：" + s, Toast.LENGTH_SHORT).show();
            }
        };
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
