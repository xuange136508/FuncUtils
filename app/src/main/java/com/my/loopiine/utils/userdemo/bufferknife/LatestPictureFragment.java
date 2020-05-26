package com.my.loopiine.utils.userdemo.bufferknife;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.my.loopiine.R;
import com.my.loopiine.uitl.MD5Util;
import com.my.loopiine.utils.userdemo.okhttp.OkHttpManager;
import com.my.loopiine.utils.userdemo.okhttp.TinGoApi;
import com.my.loopiine.utils.userdemo.okhttp.bean.PictureListItem;
import com.my.loopiine.utils.userdemo.okhttp.callback.StringCallBack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created with com.github.tingolife.fragment
 * User:YangXiuFeng
 * Date:2016/4/11
 * Time:9:13
 */
public class LatestPictureFragment extends Fragment {

    @Bind(R.id.tv)
    TextView recyclerView;
    private View rootView;
    private List<PictureListItem.TngouEntity> tngou;
    String tagUrl;
    int classify;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null) {
            ButterKnife.bind(this, rootView);
            return rootView;
        }
        rootView = inflater.inflate(R.layout.demo_okhttp_fragment, container, false);
        ButterKnife.bind(this, rootView);

        tngou = new ArrayList<>();
        initData();


        return rootView;
    }


    private void initData(){
        getRandom();
        tagUrl = TinGoApi.PIC_LATEST+"?"+"rows=30&classify="+classify;
        OkHttpManager.getOkHttpManager().asyncGet(tagUrl, new StringCallBack() {
            @Override
            public void onError(Call call, Exception e) {
                Log.e("back", e.getLocalizedMessage());
                Toast.makeText(getActivity(), "请检查您的网络！！！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                PictureListItem pictureListItem = new Gson().fromJson(response, PictureListItem.class);
                tngou.addAll(pictureListItem.getTngou());
                if (pictureListItem.getTngou().size() > 0) {

                    Toast.makeText(getActivity(), tngou.get(0).getTitle(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "没有更多图片了", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private int getRandom() {
        Random random = new Random();
        int ran = random.nextInt(8);
        if (classify != ran){
            classify = ran;
            return classify;
        }else{
            classify = getRandom();
        }
        return classify;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        OkHttpManager.getOkHttpManager().cancelTag(MD5Util.getMD5String(tagUrl));
    }
}
