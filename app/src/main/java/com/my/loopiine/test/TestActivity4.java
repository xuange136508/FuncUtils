package com.my.loopiine.test;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.my.loopiine.R;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by xx on 2017/5/4.
 */

public class TestActivity4 extends Activity implements View.OnClickListener {

    private EditText et;
    private Button bt1,bt2,bt3,bt4;
    private SurfaceView sv;
    private int currentPosition ;

    private MediaPlayer media;//多媒体播放类


    //测试连接1
    String path = "http://v11-tt.ixigua.com/05409209013cb1020eb4f21785bc2b2d/599a5136/video/m/2208b10d821f80e440bb72346497372df83114f26c000022f30c676b7d/#mp4";
    String path2 = "http://v7.pstatp.com/ebd2197dce4328fcfb468e85fe2d0cfa/599fc52f/video/m/220c2eea2d52e83452a8aeffbe8ecf123b5114b15200003f7e4039a41d/#mp4";
    //西瓜视频
    String xiguaUrl = "http://v3-tt.ixigua.com/82e438609b28a70a617db69125190479/5a32520a/video/m/220a09c2049cdcc4589891706b863c1e49f11529c4e00008e4a2ef29c0b/";

    //自己爬的
    String crawerUrl = "https://video.letv-cdn.com/ppvod/24A64F7F6F0CFD7F95FE7A70F2CF62BF.m3u8";
    //测三星
    String testSamsung = "http://ali.m.l.cztv.com/channels/lantian/channel15/360p.m3u8";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_test4);
        et = (EditText) findViewById(R.id.et);
        bt1 = (Button) findViewById(R.id.bt1);//播放
        bt2 = (Button) findViewById(R.id.bt2);//暂停
        bt3 = (Button) findViewById(R.id.bt3);//重播
        bt4 = (Button) findViewById(R.id.bt4);//停止
        sv = (SurfaceView) findViewById(R.id.sv);
        //【查看holder】--------------
        sv.getHolder().addCallback(new SurfaceHolder.Callback() {

            public void surfaceDestroyed(SurfaceHolder holder) {//surface不可见调用
                if(media!=null&&media.isPlaying()){
                    currentPosition = media.getCurrentPosition();//得到当前播放位置
                    stop();
                }
            }

            public void surfaceCreated(SurfaceHolder holder) {//surface创建时候调用
                if(currentPosition>0){
                    try {
                        play(currentPosition);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }

            public void surfaceChanged(SurfaceHolder holder, int format, int width,//屏幕大小改变了
                                       int height) {
            }
        });
//---------------------------
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);
    }
    //单击事件
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.bt1:
                try {
                    play(0);//调用播放
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.bt2:
                parse();
                break;
            case R.id.bt3:
                try {
                    replay();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.bt4:
                stop();
                break;
        }


    }
    //暂停
    private void parse() {
        if("继续".equals(bt2.getText().toString().trim())){
            media.start();
            bt2.setText("暂停");
            return ;//return回到调用处等到按键按下
        }

        if(media!=null&&media.isPlaying()){//正在播放
            media.pause();
            bt2.setText("继续");
            return;
        }

    }
    //重新播放
    private void replay() throws IOException {
        if(media!=null&&media.isPlaying()){//正在播放
            media.seekTo(0);//定位重新开始
            return;
        }
        play(0);
    }
    //停止
    private void stop() {
        if(media!=null&&media.isPlaying()){//正在播放
            media.stop();
            media.release();//一定要记得释放资源
            media = null;
            bt1.setEnabled(true);
        }
    }
    //播放音乐
    private void play(final int currentPosition2) throws IOException {
        String path = et.getText().toString().trim();//路径

        media = new MediaPlayer();//实例化多媒体播放器
        media.setAudioStreamType(AudioManager.STREAM_MUSIC);//设置类型为视频
        //---设置影片在surfaceview的Holder播放
        media.setDisplay(sv.getHolder());
        //----------------------------------
        media.setDataSource(testSamsung);//设置数据源
        media.prepareAsync();//异步准备
        media.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                media.start();//开始
                //media.seekTo(currentPosition2);
            }
        });
        //-------//播放完时候的监听器
        media.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {//播放完成调用
                bt1.setEnabled(true);
            }
        });
        //-------//是否出错的监听器
        media.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {//出错调用
                bt1.setEnabled(true);
                return false;
            }
        });

        //---------
        bt1.setEnabled(false);//按钮变成不可用

    }

    private void toast(String str){
        Toast.makeText(this,str,Toast.LENGTH_LONG).show();
    }

}
