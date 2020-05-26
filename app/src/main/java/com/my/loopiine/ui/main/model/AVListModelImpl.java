package com.my.loopiine.ui.main.model;

import android.util.Log;

import com.my.loopiine.ui.Constant;
import com.my.loopiine.ui.main.domain.AVListDomain;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * ============================================================
 * 项目名称：Girls
 * 包名称：com.flyou.girls.ui.ImageList.domain
 * 文件名：ImageListModelImpl
 * 类描述：
 * 创建人：flyou
 * 邮箱：fangjaylong@gmail.com
 * 创建时间：2016/4/19 15:45
 * 修改备注：
 * 版本：@version  V1.0
 * ============================================================
 * http://m.xxxiao.com/page/1
 **/
public class AVListModelImpl implements AVListModel {


    @Override
    public void GetImageList(final String type, final int page, final GetImageListenter listener) {


        Observable<List<AVListDomain>> observable = Observable.create(new Observable.OnSubscribe<List<AVListDomain>>() {
            @Override
            public void call(Subscriber<? super List<AVListDomain>> subscriber) {
                List<AVListDomain> imageListDomainList = new ArrayList();

                int pages = page;
                //i = 24 获取数据卡顿
                for (int i = 0; i < 10; i++) {
                    imageListDomainList = manyParseData(imageListDomainList,i,pages);
                }
                //调用onNext
                subscriber.onNext(imageListDomainList);


            }
        });

        Subscriber<List<AVListDomain>> subscriber = new Subscriber<List<AVListDomain>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                listener.OnError((Exception) e);
            }

            @Override
            public void onNext(List<AVListDomain> imageListDomains) {
                Log.i("信息",imageListDomains.get(0).toString());
                listener.onSuccess(imageListDomains);
            }
        };

        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    public interface GetImageListenter {
        void onSuccess(List<AVListDomain> imageList);

        void OnError(Exception e);
    }


    //多次解析调用
    public List<AVListDomain>  manyParseData(List<AVListDomain> imageList, int page,int range){
        try {

            String url = Constant.BASE_AV_URL + (Constant.current - range*181 - page)+".htm";
            String mtitle ="漂亮的女神";
            Log.i("信息",url);
            Document document = Jsoup.connect(url).get();
            Element element = document.getElementsByClass("pics").first();
            //解析title
            Elements metainfo = document.getElementsByTag("meta");
            for (int i=0;i<metainfo.size();i++) {
                String title = metainfo.get(i).attr("content");
                if(i==(metainfo.size()-1)){
                    mtitle = title;
                }
            }
            //解析图片
            Elements img = element.getElementsByTag("img");
            for (Element imageListElement : img) {
                String imageUrl = imageListElement.attr("src");
                imageList.add(new AVListDomain(imageUrl,url,mtitle));
                break;
            }
            //返回
            return imageList;

        } catch (IOException e) {
            e.printStackTrace();
            //返回
            return imageList;
        }

    }



}
