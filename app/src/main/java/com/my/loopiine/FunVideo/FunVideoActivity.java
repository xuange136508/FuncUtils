package com.my.loopiine.FunVideo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.my.loopiine.R;
import com.my.loopiine.adapter.recyclerview.CommonAdapter;
import com.my.loopiine.adapter.recyclerview.ViewHolder;
import com.my.loopiine.ui.main.domain.ImageListDomain;
import com.my.loopiine.ui.main.widget.fragment.AVNoteFragment;
import com.my.loopiine.ui.main.widget.fragment.NewFragment;
import com.my.loopiine.ui.main.widget.fragment.ShaoNvFragment;
import com.my.loopiine.ui.main.widget.fragment.VideosNoteFragment;
import com.my.loopiine.ui.main.widget.fragment.XinGanFragment;
import com.my.loopiine.ui.typeImageList.widget.TypeImageActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

public class FunVideoActivity extends AppCompatActivity{

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private CommonAdapter mAdapter;

    private List<FunVideoList> mList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_funvideo);

        initView();
        initData();
    }

    private void initData() {
        mList = new ArrayList<>();

        mAdapter = new CommonAdapter<FunVideoList>(this, R.layout.view_item_funvideo_list, mList) {
            //回调
            @Override
            public void convert(ViewHolder holder, final FunVideoList funVideoBean) {
                holder.setText(R.id.tv_title, funVideoBean.getTitle());
                holder.setImageWithUrl(R.id.iv_cover, funVideoBean.getImgUrl());
                holder.setOnClickListener(R.id.iv_cover, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(FunVideoActivity.this,FunVideoDetailActivity.class);
                        intent.putExtra("playUrl",funVideoBean.getPlayUrl());
                        startActivity(intent);
                    }
                });
            }
        };
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new SlideInLeftAnimator());
        mRecyclerView.setAdapter(mAdapter);

        getDataFromUrl();
    }

    private void getDataFromUrl() {
        //普通的网络请求
        String myUrl = "";
        String respondString = null;
        try {
            URL url = new URL(myUrl);
            //打开连接
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            if(200 == urlConnection.getResponseCode()){
                //得到输入流
                InputStream is =urlConnection.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while(-1 != (len = is.read(buffer))){
                    baos.write(buffer,0,len);
                    baos.flush();
                }
                respondString  = baos.toString("utf-8");
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }

        //json解析
        //respondString
        mAdapter.notifyDataSetChanged();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.receiverview);

    }

}
