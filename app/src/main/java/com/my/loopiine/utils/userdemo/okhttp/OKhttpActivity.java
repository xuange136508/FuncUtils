package com.my.loopiine.utils.userdemo.okhttp;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.my.loopiine.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OKhttpActivity extends AppCompatActivity {

    private LatestPictureFragment pictureFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_okhttp);


        pictureFragment = new LatestPictureFragment();

        setFragment(pictureFragment);

    }


    private void setFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_fragment, fragment);
        if (!(fragment instanceof LatestPictureFragment)) {
            transaction.addToBackStack(null);
        }
        //提交修改
        transaction.commit();
    }


}
