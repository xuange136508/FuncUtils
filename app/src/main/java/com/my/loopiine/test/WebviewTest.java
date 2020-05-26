package com.my.loopiine.test;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.my.loopiine.R;

/**
 关于如何获取html内部的视频链接 我有一个方法， 就是在ShouldinterceptRequest这个方法中， 截取所以html需要获取的请求连接， 然后去下载对应的资源
 在下载前记得判断一下链接返回的mimetype的类型， 是视频的格式在考虑下载 这样就可以获得视频的链接了。。
 最近在公司做Webview的缓存设计， webview的自带缓存实在太渣了， 目前我的方法就是用这样的方法 去下载html的资源
 然后自己在后天拼一个出来 返回给Webview，但是目前这种方法最视频的支持不是很好
 */

public class WebviewTest extends Activity {

    WebView webview;
    FrameLayout videoContainer;
    String LOG_TAG = "WebviewTest";
    //短视频各接口地址
    public static final String URL_BASE_SHORTVIDEO = "http://k.360kan.com/pc/list?notad=1&dl=vh5_9ikd&channel_id=0";
    //优酷视频
    public static final String UK_URL = "http://look.appjx.cn/mobile_api.php?mod=news&id=12604";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.act_test_webview);

        webview = (WebView) findViewById(R.id.webView);
        videoContainer = (FrameLayout) findViewById(R.id.videoContainer);

        initWebView();
    }

    private void initWebView() {
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        //缓存:
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setDomStorageEnabled(true);


        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setPluginState(WebSettings.PluginState.ON); // 新加入 2016.11.25 恒利需要加入 用来播放视频的
        webSettings.setBlockNetworkImage(false);
        webSettings.setLoadsImagesAutomatically(true);    //设置自动加载图片
        webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);// 默认缩放模式
        webSettings.setUseWideViewPort(true); // 关键点
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setSupportZoom(true); // 支持缩放

        //自适应屏幕
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);// 解决三星note4显示不全
        //任意比例缩放
        webSettings.setUseWideViewPort(true);
        //缩放至屏幕大小
        webSettings.setLoadWithOverviewMode(true);

        webview.setWebChromeClient(new CustomWebViewChromeClient());
        webview.setWebViewClient(new WebViewClient() {

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
                String js = TagUtils.getJs(url);
                view.loadUrl(js);
            }
        });

        webview.loadUrl(URL_BASE_SHORTVIDEO);
//        webview.loadUrl(UK_URL);
        webview.addJavascriptInterface(new JsObject(),"onClick");
    }

    private WebChromeClient.CustomViewCallback mCallBack;

    private Handler mHandler = new Handler();


    private class CustomWebViewChromeClient extends WebChromeClient{

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            fullScreen();
            webview.setVisibility(View.GONE);
            videoContainer.setVisibility(View.VISIBLE);
            videoContainer.addView(view);
            mCallBack=callback;
            super.onShowCustomView(view, callback);
        }

        @Override
        public void onHideCustomView() {
            fullScreen();
            if (mCallBack!=null){
                mCallBack.onCustomViewHidden();
            }
            webview.setVisibility(View.VISIBLE);
            videoContainer.removeAllViews();
            videoContainer.setVisibility(View.GONE);
            super.onHideCustomView();
        }

        @Override
        public void onProgressChanged(WebView webView, int i) {
            super.onProgressChanged(webView, i);
            if (i == 100) {

            }
        }
    }

    private void fullScreen() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    /**
     * 设置全屏
     */
    private void setFullScreen() {
        // 设置全屏的相关属性，获取当前的屏幕状态，然后设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 全屏下的状态码：1098974464
        // 窗口下的状态吗：1098973440
    }

    /**
     * 退出全屏
     */
    private void quitFullScreen() {
        // 声明当前屏幕状态的参数并获取
        final WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setAttributes(attrs);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }


    @Override
    public void onBackPressed() {
        if (webview.canGoBack()){
            webview.goBack();
        }else {
            super.onBackPressed();
        }
    }

    //js回调
    private class JsObject{
        @JavascriptInterface
        public void fullscreen(){
            //监听到用户点击全屏按钮
            fullScreen();
        }
    }


    //工具类
    static class TagUtils {

        public static String getTagByUrl(String url) {
            if (url.contains("qq")) {
                return "tvp_fullscreen_button"; // http://m.v.qq.com
            } else if (url.contains("youku")) {
                return "x-zoomin";              // http://www.youku.com
            } else if (url.contains("bilibili")) {
                return "icon-widescreen";       // http://www.bilibili.com/mobile/index.html
            } else if (url.contains("acfun")) {
                return "controller-btn-fullscreen"; //http://m.acfun.tv   无效
            } else if (url.contains("le")) {
                return "hv_ico_screen";         // http://m.le.com  无效
            }
            return "";
        }

        //  "javascript:document.getElementsByClassName('" + referParser(url) + "')[0].addEventListener('click',function(){local_obj.playing();return false;});"
        public static String getJs(String url) {
            String tag = getTagByUrl(url);
            if (TextUtils.isEmpty(tag)) {
                return "javascript:";
            } else {
                return "javascript:document.getElementsByClassName('" + tag + "')[0].addEventListener('click',function(){onClick.fullscreen();return false;});";
            }
        }
    }




}
