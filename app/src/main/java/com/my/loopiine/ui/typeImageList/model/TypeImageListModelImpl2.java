package com.my.loopiine.ui.typeImageList.model;


import android.util.Log;

import com.my.loopiine.ui.typeImageList.domain.TypeImageDomain2;

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
 * 包名称：com.flyou.girls.ui.typeImageList.model
 * 文件名：TypeImageListModelImpl
 * 类描述：
 * 创建人：flyou
 * 邮箱：fangjaylong@gmail.com
 * 创建时间：2016/4/20 14:43
 * 修改备注：
 * 版本：@version  V1.0
 * http://m.xxxiao.com/41125
 * ============================================================
 **/
public class TypeImageListModelImpl2 implements TypeImageListModel2 {
    @Override
    public void getTypeImageList(final String url, final TypeImageListModelImpl2.OnGetTypeImageListener litener) {

        Observable<List<TypeImageDomain2>> observable = Observable.create(new Observable.OnSubscribe<List<TypeImageDomain2>>() {
            @Override
            public void call(Subscriber<? super List<TypeImageDomain2>> subscriber) {
                List<TypeImageDomain2> imageListDomainList = new ArrayList();
                try {
                    Log.i("信息",url);
                    Document document = Jsoup.connect(url).get();
                    Element element = document.getElementsByClass("pics").first();

                    Elements img = element.getElementsByTag("img");
                    for (Element imageListElement : img) {
                        String imageUrl = imageListElement.attr("src");
                        imageListDomainList.add(new TypeImageDomain2(imageUrl));

                    }
                    subscriber.onNext(imageListDomainList);

                } catch (IOException e) {
                    subscriber.onError(e);

                }
            }
        });


        Subscriber<List<TypeImageDomain2>> subscriber = new Subscriber<List<TypeImageDomain2>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                litener.OnError((Exception) e);
            }

            @Override
            public void onNext(List<TypeImageDomain2> typeImageDomains) {
                litener.onSuccess(typeImageDomains);

            }
        };


        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public interface OnGetTypeImageListener {
        void onSuccess(List<TypeImageDomain2> imageDomainList);

        void OnError(Exception e);
    }
}
