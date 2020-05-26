package com.my.loopiine.uitl;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.my.loopiine.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图片工具类
 * Created by hyming on 2016/7/14.
 */
public class ImageUtils {

    private final static int LOADING_IMG = R.mipmap.ic_image_loading;// 正在加载图片
    private final static int ERROR_IMG = R.mipmap.ic_image_loading;// 错误图片

    // 加载图片
    public static void showImage(Fragment fragment, String url, ImageView iv) {
        Glide.with(fragment).load(getUrl(url)).placeholder(LOADING_IMG)
                .error(ERROR_IMG).into(iv);
    }

    // 加载图片
    public static void showImage(Activity activity, String url, ImageView iv) {
        Glide.with(activity).load(getUrl(url)).placeholder(LOADING_IMG)
                .error(ERROR_IMG).into(iv);
    }

    // 加载图片
    public static void showImage(Context cxt, String url, ImageView iv) {
        Glide.with(cxt).load(getUrl(url)).placeholder(LOADING_IMG)
                .error(ERROR_IMG).into(iv);
    }

    public static void showCircleImage(final Context context, String url, final ImageView iv) {
        makeCircleImage(context, getUrl(url), iv);
    }

    public static void showCircleImage(final Context context, int url, final ImageView iv) {
        makeCircleImage(context, url, iv);
    }

    private static void makeCircleImage(final Context context, Object url, final ImageView iv) {
        Glide.with(context).load(url)
                .asBitmap().centerCrop().placeholder(LOADING_IMG)
                .error(ERROR_IMG).into(new BitmapImageViewTarget(iv) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                iv.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    private static String getUrl(String url) {
        if (!TextUtils.isEmpty(url) && !url.startsWith("http://")) {
            return "http://" + url;
        }
        return url;
    }


    /**
     * 图片转Base64
     *
     * @param bitmap
     * @return
     */
    public static String imgToBase64(Bitmap bitmap) {
        if (bitmap == null) {
            //bitmap not found!!
            return null;
        }
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            //70 是压缩率，表示压缩30%; 如果不压缩是100，表示压缩率为0
            bitmap.compress(Bitmap.CompressFormat.PNG, 70, out);

            out.flush();
            out.close();

            byte[] imgBytes = out.toByteArray();
            return Base64.encodeToString(imgBytes, Base64.DEFAULT);
        } catch (Exception e) {
            return null;
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
     * 根据Uri获取图片路径
     *
     * @param cxt
     * @param contentUri
     * @return
     */
    public static String getRealPathFromURI(Context cxt, Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = cxt.getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor!=null){
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                res = cursor.getString(column_index);
            }
            cursor.close();
        }

        return res;
    }

    /**
     * 根据图片路径获取bitmap
     *
     * @param filePath
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap readBitmap(String filePath, int reqWidth, int reqHeight) {
        try {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, options);

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;

            Bitmap bm = BitmapFactory.decodeFile(filePath, options);
            return bm;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 计算图片缩放
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
        }
        return inSampleSize;
    }

    /**
     * 将图片内容解析成字节数组
     *
     * @param inStream
     * @return byte[]
     * @throws Exception
     */
    public static byte[] readStream(InputStream inStream) throws Exception {
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();
        return data;

    }

    /**
     * 把Bitmap转Byte
     */
    public static byte[] bitmap2Bytes(Bitmap bitmap) {
        if (bitmap == null) {
            //bitmap not found!!
            return null;
        }
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            return out.toByteArray();
        } catch (Exception e) {
            return null;
        }
    }

    public static void saveBitmapToLocal(String path, Bitmap mBitmap) {
        File f = new File(path);
        try {
            f.createNewFile();
        } catch (IOException e) {
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 95, fOut); ////30 是压缩率，表示压缩70%; 如果不压缩是100，表示压缩率为0
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
