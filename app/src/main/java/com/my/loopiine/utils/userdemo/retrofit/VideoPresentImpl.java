package com.my.loopiine.utils.userdemo.retrofit;

import com.socks.library.KLog;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * ClassName: IVideoListInteractorImpl<p>
 * Author: oubowu<p>
 * Fuction: 视频列表Model层接口实现<p>
 * CreateDate: 2016/2/23 17:10<p>
 * UpdateUser: <p>
 * UpdateDate: <p>
 */
public class VideoPresentImpl  {

    public static final int NETEASE_NEWS_VIDEO = 1;

    public Subscription requestVideoList(final RequestCallback<List<VideoBean>> callback, final String id, int startPage) {
        return RetrofitManager.getInstance(NETEASE_NEWS_VIDEO)
                .getVideoListObservable(id, startPage).doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        callback.beforeRequest();
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                    //平铺数据
                    .flatMap(
                        new Func1<Map<String, List<VideoBean>>, Observable<VideoBean>>() {
                            @Override
                            public Observable<VideoBean> call(Map<String, List<VideoBean>> map) {
                                // 通过id取到list
                                return Observable.from(map.get(id));
                            }
                        })
                .toSortedList(new Func2<VideoBean, VideoBean, Integer>() {
                    @Override
                    public Integer call(VideoBean VideoBean, VideoBean VideoBean2) {
                        // 时间排序
                        return VideoBean2.ptime.compareTo(VideoBean.ptime);
                    }
                }).subscribe(new Subscriber<List<VideoBean>>() {
                    @Override
                    public void onCompleted() {
                        callback.requestComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e + "\n" + e.getLocalizedMessage());
                        callback.requestError(e + "\n" + e.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(List<VideoBean> data) {
                        callback.requestSuccess(data);
                    }
                });
    }
}

/***
 *  另一种情况 返回json对象不是json数组
 public Subscription requestPhotoDetail(final RequestCallback<SinaPhotoDetail> callback, String id) {
  return RetrofitManager.getInstance(HostType.SINA_NEWS_PHOTO).getSinaPhotoDetailObservable(id)
     .doOnSubscribe(new Action0() {
            @Override
            public void call() {
            callback.beforeRequest();
            }
    }).subscribeOn(AndroidSchedulers.mainThread())
     .subscribe(new Subscriber<SinaPhotoDetail>() {
            @Override
            public void onCompleted() {
            callback.requestComplete();
            }

            @Override
            public void onError(Throwable e) {
            callback.requestError(e.getLocalizedMessage());
            }

            @Override
            public void onNext(SinaPhotoDetail sinaPhotoDetail) {
            callback.requestSuccess(sinaPhotoDetail);
            }
            });
 }

 * ***/