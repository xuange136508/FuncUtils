package com.my.loopiine.utils.userdemo.gif;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.my.loopiine.R;
import com.my.loopiine.test.TestActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by xx on 2017/9/25.
 * Gif生成器
 *
 * 使用：
    new GifLoadUtils().createGif(this,"test",300);
 */
public class GifLoadUtils {

    public static final String TAG = "GifTest";



    /**
     * 生成gif图
     * @param file_name 保存文件名
     * @param delay     帧之间间隔的时间
     */
    public void createGif(Context context, String file_name, int delay) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        AnimatedGifEncoder localAnimatedGifEncoder = new AnimatedGifEncoder();
        localAnimatedGifEncoder.start(baos);//start
        localAnimatedGifEncoder.setRepeat(0);//设置生成gif的开始播放时间。0为立即开始播放
        localAnimatedGifEncoder.setDelay(delay);
//        if (pics.isEmpty()) {
        //测试图片
        localAnimatedGifEncoder.addFrame(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_header));
        localAnimatedGifEncoder.addFrame(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
        localAnimatedGifEncoder.addFrame(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_play_circle));
//        } else {
//            for (int i = 0; i < pics.size(); i++) {
//                // Bitmap localBitmap = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(pics.get(i)), 512, 512);
//                localAnimatedGifEncoder.addFrame(BitmapFactory.decodeFile(pics.get(i)));
//            }
//        }
        localAnimatedGifEncoder.finish();//finish

        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/GIFMaker");
        if (!file.exists()) file.mkdir();
        String path = Environment.getExternalStorageDirectory().getPath() + "/GIFMaker/" + file_name + ".gif";
        Log.d(TAG, "createGif: ---->" + path);

        try {
            FileOutputStream fos = new FileOutputStream(path);
            baos.writeTo(fos);
            baos.flush();
            fos.flush();
            baos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //mIvGif.setImageURI(Uri.parse(path));
        Toast.makeText(context, "Gif已生成。保存路径：\n" + path, Toast.LENGTH_LONG).show();
    }



}
