package com.my.loopiine.test;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.my.loopiine.R;

//用于测试webview缓存
//缓存图片： http://blog.csdn.net/rnzuozuo/article/details/45075959
//  http://www.360doc.com/content/15/0917/10/20918780_499684489.shtml
//  http://www.tuicool.com/articles/VFzY3y3

public class WebviewTest2 extends Activity {

    private FlexiableWebView webview;
    private FrameLayout videoContainer;
    private ProgressBar progress;

    String LOG_TAG = "WebviewCache";
    //短视频各接口地址
    public static final String URL_BASE_SHORTVIDEO = "http://k.360kan.com/pc/list?notad=1&dl=vh5_9ikd&channel_id=0";
    //优酷视频
    public static final String UK_URL = "http://look.appjx.cn/mobile_api.php?mod=news&id=12604";




    public static final String CSDN = "http://blog.csdn.net/fishmai/article/details/52398504";
    public static final String URL_9I = "https://www.9ikandian.com/jitvui/index2.html?poltId=81db97c32d9a4cbd97d6fc2cc9883be8";
    public static final String URL_9I_MY = "https://www.9ikandian.com/jitvui/page/my/my.html?count=1&uniqueIdentifier=86586302446831138BC1AC76812&userToken=&firstN=preinstalled&secondNum=1";
    public static final String URL_9I_MY_LOGIN = "https://www.9ikandian.com/jitvui/page/my/my.html?count=1&uniqueIdentifier=86586302446831138BC1AC76812&userToken=C288FF88D579562FA6E69240A5D75341&firstN=preinstalled&secondNum=1";

    //电视台
    public static final String URL_9I_TVTAI = "https://www.9itv.com.cn/jitvui/index2.html?poltId=4200782a467749b8bd6ef3db03885af8&name=%E7%94%B5%E8%A7%86%E5%8F%B0&uniqueIdentifier=8688530273640852C5BB844DA8A&userToken=&firstN=website&secondNum=2&channelType=website&channelNumber=2";
    //详情页
    public static final String URL_9I_MOVIE_DETAIL = "https://www.9ikandian.com/jitvui/newPage/tv/VideoDetails.html?nextPage=1&videoId=4061DC9EB05A41249E275CB28370D95D&resourceType=tv&name=%25E9%2593%2581%25E8%25A1%2580%25E5%25B0%2586%25E5%2586%259B&parentPage=index&selectchannel=tv&playData=&recommendNum=&typeId=BA933B92836943788F50E212DC239EB9&uniqueIdentifier=8688530273640852C5BB844DA8A&userToken=&firstN=website&secondNum=2";

    //美拍
    public static final String URL_MEI_PAI = "http://www.meipai.com/media/796503798?from=qq";

    public static final String URL_TEST_BUG= "https://www.9ikandian.com/jitvui/index2.html?poltId=81db97c32d9a4cbd97d6fc2cc9883be8&name=%E7%B2%BE%E9%80%89&uniqueIdentifier=866655022507507C4072F85A22F&userToken=&firstN=website&secondNum=2&appVersion=193&packageName=com.zhiguan.m9ikandian&channelType=website&channelNumber=2";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.act_test_webview);

        webview = (FlexiableWebView) findViewById(R.id.webView);
        videoContainer = (FrameLayout) findViewById(R.id.videoContainer);
        progress = (ProgressBar) findViewById(R.id.progress);

        initWebView();

    }

    private void initWebView() {
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);

        //webview缓存
        WebSettings webseting = webview.getSettings();
        webseting.setDomStorageEnabled(true);
        //开启 database storage API 功能
        webseting.setDatabaseEnabled(true);
        webseting.setAppCacheMaxSize(1024*1024*8);//设置缓冲大小，我设的是8M
        String appCacheDir = this.getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getAbsolutePath();
        webseting.setAppCachePath(appCacheDir);
        //设置数据库缓存路径
        webseting.setDatabasePath(appCacheDir);
        webseting.setAllowFileAccess(true);
        webseting.setAppCacheEnabled(true);
        // 支持多窗口
        webseting.setSupportMultipleWindows(true);
        // 设置 缓存模式
//        if (NetUtils.isNetworkAvailable(this)) {
//            webseting.setCacheMode(WebSettings.LOAD_DEFAULT);
//        } else {
        webseting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        }
        /**
         *  Webview在安卓5.0之前默认允许其加载混合网络协议内容
         *  在安卓5.0之后，默认不允许加载http与https混合内容，需要设置webview允许其加载混合网络协议内容
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webseting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        }

        webview.setWebChromeClient(new CustomWebViewChromeClient());
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                // 不要使用super，否则有些手机访问不了，因为包含了一条 handler.cancel()
                // super.onReceivedSslError(view, handler, error);
                // 接受所有网站的证书，忽略SSL错误，执行访问网页
                handler.proceed();
                //默认的处理方式，WebView变成空白页
                //handler.cancel();
                // 其他处理
                //handleMessage(Message msg);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(LOG_TAG,url);
                if (url.contains("web/live")) {

                    return true;
                }
                // 如果不是如上特殊情况，则判断该跳什么页面
                else {
                    return false;
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                String js = TagUtils.getJs(url);
//                view.loadUrl(js);
            }

        });

        //webview预加载背景
        webview.setBackgroundResource(R.mipmap.ic_bg_home_frame);
        webview.setBackgroundColor(Color.argb(0, 0, 0, 0));

//        webview.loadUrl(URL_9I_TVTAI);
        //webview.loadUrl(URL_TEST_BUG);
        String s = "https://www.9itv.com.cn/jitvui/page/search/search.html?uniqueIdentifier=822678180545260847303051FD3&userToken=&firstN=website&secondNum=2&appVersion=195&packageName=com.zhiguan.m9ikandian&deviceType=Android";
        webview.loadUrl(s);
    }


    private class CustomWebViewChromeClient extends WebChromeClient{


        @Override
        public void onProgressChanged(WebView webView, int i) {
            super.onProgressChanged(webView, i);
            if (i == 100) {
                progress.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public void onBackPressed() {
        if (webview.canGoBack()){
            webview.goBack();
        }else {
            super.onBackPressed();
        }
    }


    @Override
    public void onDestroy() {
        webview.destroy();
        super.onDestroy();
    }



}

