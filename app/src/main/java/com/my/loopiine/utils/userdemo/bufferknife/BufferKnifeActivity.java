package com.my.loopiine.utils.userdemo.bufferknife;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;

import com.my.loopiine.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BufferKnifeActivity extends Activity {



    //注解使用
    @Bind(R.id.jb)
    public TextView jb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_bufferknife);
        ButterKnife.bind(this);//进行动态绑定


    }


    @OnClick(R.id.jb)
    public void testClick(){
    }

}
