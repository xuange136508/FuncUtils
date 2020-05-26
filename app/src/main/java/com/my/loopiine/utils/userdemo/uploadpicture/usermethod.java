package com.my.loopiine.utils.userdemo.uploadpicture;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.my.loopiine.uitl.FileUtils;
import com.my.loopiine.uitl.ImageUtils;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

/**
 * 通过相册相机 Base64上传图片的方式
 */

public class usermethod extends Activity{

    // 请求的code
    public final static int REQUEST_TAKE_PHOTO = 1;// 相机照相
    public final static int REQUEST_PICK_PHOTO = 2;// 相册选取
    public final static int REQUEST_CROP_PICTURE = 3;// 裁剪

    private String mImagePath;
    private Uri mImageUri;
    private int type = 1;//1 拍照 2 相册
    private Uri tempUri ;

    //使用
    private void base64Utils(Context mContext){
        //缓存路径存储
        mImagePath = mContext.getExternalCacheDir().getAbsolutePath() + String.valueOf(System.currentTimeMillis()) + ".jpg";
        //sd路径
        String dirs = Environment.getExternalStorageDirectory()+ File.separator+ "tempXMDJ";
        File file = new File(dirs);
        if(!file.exists()){
            file.mkdir();
        }
        //临时存储uri，防止裁剪原图
        tempUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory()+  File.separator+ "tempXMDJ" + File.separator+ "tmpUpload.jpg"));
        //判断是否有SD
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
        }else{
            Toast.makeText(mContext, "请插入SD卡",Toast.LENGTH_LONG).show();
        }



    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 手机拍照
        if (requestCode == REQUEST_TAKE_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                type = 1;
                cropImageUri(mImageUri, 750, 750, REQUEST_CROP_PICTURE);
            }
        }
        // 相册获取
        else if (requestCode == REQUEST_PICK_PHOTO) {
            if (data != null && data.getData() != null) {
                mImageUri = data.getData();
//                String imagePath = ImageUtils.getRealPathFromURI(mContext, uri);
//                Bitmap bmp = ImageUtils.readBitmap(imagePath, 480, 800);
//                mImagePath = mContext.getExternalCacheDir().getAbsolutePath() + String.valueOf(System.currentTimeMillis()) + ".jpg";
//                if(bmp!=null) {
//                    ImageUtils.saveBitmapToLocal(mImagePath, bmp);
//                }
                type = 2;
                cropImageUri(mImageUri, 750, 750, REQUEST_CROP_PICTURE);
            }
        }
        // 裁剪图片
        else if (requestCode == REQUEST_CROP_PICTURE && resultCode == Activity.RESULT_OK) {
            if(type==1){
                if(!TextUtils.isEmpty(mImagePath)){
//                    Bitmap bmp = ImageUtils.readBitmap(mImagePath, 750, 750);
//                    if(bmp!=null) {
//                        ImageUtils.saveBitmapToLocal(mImagePath, bmp);
//                    }
                    //2016-12-22改 uri防止原图片被篡改
                    Bitmap bmp = decodeUriAsBitmap(this,tempUri);
                    if(bmp!=null){
                        String dirs = Environment.getExternalStorageDirectory()+ File.separator+ "tempXMDJ";
                        File file = new File(dirs);
                        if(!file.exists()){
                            file.mkdir();
                        }
                        String tmp = Environment.getExternalStorageDirectory()+ File.separator+ "tempXMDJ" + File.separator+ +System.currentTimeMillis() +"tmp.jpg";
                        boolean res = saveImage(bmp, tmp);
                        if(res){
                            mImagePath = tmp;
                        }
                    }
                    //使用mImagePath进行上传
                    Bitmap xbmp = ImageUtils.decodeUriAsBitmap(this, mImageUri);
                    String base64 = ImageUtils.imgToBase64(xbmp);
                    //bug修改，添加前缀
                    if(!TextUtils.isEmpty(base64)) {
                        base64 = "data:image/png;base64," + base64;
                    }
                     /* if (bmp != null) bmp.recycle();*/
                    FileUtils.deleteFile(mImagePath);
                    mImagePath = "";
                    // 上传图片 base64串post上传

                }
            }else if(type==2){
            }
        }
    }



    //保存图片
    public boolean saveImage(Bitmap photo, String spath) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(spath, false));
            photo.compress(Bitmap.CompressFormat.JPEG, 95, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public static Bitmap decodeUriAsBitmap(Context cxt, Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(cxt.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }



    /**
     * 相机拍照
     */
    private void doTakePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(mImagePath);
        // 设置输出路径
        mImageUri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
    }

    /**
     * 相册获取
     */
    private void doPickPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_PICK_PHOTO);
    }

    /**
     * 裁剪图片
     */
    private void cropImageUri(Uri uri, int outputX, int outputY, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");//设置数据
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例，这里设置的是正方形（长宽比为1:1）
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 750);
        intent.putExtra("outputY", 750);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);//设置输出路径
        intent.putExtra("return-data", false);
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, requestCode);
    }



    private void rxPermission(Context ctx){
        //权限
        RxPermissions.getInstance(ctx)
                .request(Manifest.permission.CAMERA)
                .subscribe(granted -> {
                    if (granted) {
                        //OK
                        //doTakePhoto();// 相机拍照

                        //Toast.makeText(MainActivity.this,"已同意权限",Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ctx,"请同意开启相机权限",Toast.LENGTH_LONG).show();
                    }
                });
    }

}
