package com.my.loopiine.ui;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.my.loopiine.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;
import zhou.widget.RichText;

public class TextDetialActivity extends AppCompatActivity implements View.OnClickListener {


    private String text;
    private String title;
    private String url;
    private Toolbar mToolbar;
    private RichText richtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏

        setContentView(R.layout.activity_notes_detial);
        initView();
        initDate();

    }

    private void initDate() {
        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("linkUrl");
        text = getIntent().getStringExtra("text");
        mToolbar.setTitle(title);
        setSupportActionBar(mToolbar);
        richtext.setRichText(text);



    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        richtext = (RichText) findViewById(R.id.rich_text);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.download:
                Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {


                    }
                });
                Subscriber<String> subscriber = new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(String s) {
                    }
                };
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(subscriber);
                break;
        }
    }
}
