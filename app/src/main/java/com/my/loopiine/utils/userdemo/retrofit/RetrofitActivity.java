package com.my.loopiine.utils.userdemo.retrofit;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.my.loopiine.R;
import com.socks.library.KLog;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;

public class RetrofitActivity extends Activity implements RequestCallback<List<VideoBean>>{

    // 热点视频
    public static final String VIDEO_HOT_ID = "V9LG4B3A0";

    //注解使用
    @Bind(R.id.jb)
    public TextView jb;
    @Bind(R.id.rl_viewgroup)
    public RelativeLayout rl_viewgroup;

    private VideoPresentImpl mVideoListInteractor;
    protected Subscription mSubscription;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_bufferknife);
        ButterKnife.bind(this);//进行动态绑定

        mVideoListInteractor = new VideoPresentImpl();

    }


    @OnClick(R.id.jb)
    public void testClick(){
        mSubscription = mVideoListInteractor.requestVideoList(this,VIDEO_HOT_ID, 0);
    }


    @Override
    public void onDestroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }


/***实现接口回调***/
    @Override
    public void beforeRequest() {
        //showProgress();
    }

    @Override
    public void requestError(String msg) {
        KLog.e(msg);
        Snackbar.make(rl_viewgroup,"msg",Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void requestComplete() {
        //hideProgress();
    }

    @Override
    public void requestSuccess(List<VideoBean> data) {
        if(data!=null && data.size()>0){
            jb.setText(data.get(0).toString());
        }
    }
}
