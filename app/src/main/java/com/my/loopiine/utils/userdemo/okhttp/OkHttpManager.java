package com.my.loopiine.utils.userdemo.okhttp;

import android.os.Handler;
import android.os.Looper;

import com.my.loopiine.uitl.MD5Util;
import com.my.loopiine.utils.userdemo.okhttp.callback.CallBack;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created with com.github.tingolife.Http
 * User:YangXiuFeng
 * Date:2016/3/19
 * Time:16:41
 */
public class OkHttpManager {

    private static OkHttpManager okHttpManager;
    private static OkHttpClient okHttpClient;
    private Handler handler;
    public static OkHttpManager getOkHttpManager() {
        if (okHttpManager == null) {
            synchronized (OkHttpManager.class) {
                okHttpManager = new OkHttpManager();
            }
        }
        return okHttpManager;
    }

    private OkHttpManager() {
        okHttpClient = new OkHttpClient();
        okHttpClient.newBuilder().connectTimeout(5, TimeUnit.SECONDS);
        okHttpClient.newBuilder().readTimeout(5, TimeUnit.SECONDS);
        okHttpClient.newBuilder().writeTimeout(5, TimeUnit.SECONDS);
        handler = new Handler(Looper.getMainLooper());
    }

    /**
     * <p>异步请求</p>
     *
     * @param url
     * @param callBack
     */
    public void asyncGet(String url, final CallBack callBack) {
        Request request = new Request.Builder().url(url).tag(MD5Util.getMD5String(url)).build();
        okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                sendFailResultCallback(call,e,callBack);
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                Object object = null;
                try {
                    object = callBack.parseNetworkResponse(response);
                    sendSuccessResultCallback(object,callBack);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void sendFailResultCallback(final Call call, final Exception e, final CallBack callback)
    {
        if (callback == null) return;

        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                callback.onError(call, e);
                callback.onAfter();
            }
        });
    }

    public void sendSuccessResultCallback(final Object object, final CallBack callback)
    {
        if (callback == null) return;
        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                callback.onResponse(object);
                callback.onAfter();
            }
        });
    }

    public void cancelTag(Object tag)
    {
        for (Call call : okHttpClient.dispatcher().queuedCalls())
        {
            if (tag.equals(call.request().tag()))
            {
                call.cancel();
            }
        }
        for (Call call : okHttpClient.dispatcher().runningCalls())
        {
            if (tag.equals(call.request().tag()))
            {
                call.cancel();
            }
        }
    }
}
