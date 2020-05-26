package com.my.loopiine.ui.main.model;

import android.text.TextUtils;
import android.util.Log;

import com.my.loopiine.ui.Constant;
import com.my.loopiine.ui.main.domain.NoteListDomain;

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
public class NotesListModelImpl implements NotesListModel {


    @Override
    public void GetNotesList(final String type, final int page, final GetNotesListenter listener) {

        Observable<List<NoteListDomain>> observable = Observable.create(new Observable.OnSubscribe<List<NoteListDomain>>() {
            @Override
            public void call(Subscriber<? super List<NoteListDomain>> subscriber) {
                List<NoteListDomain> imageListDomainList = new ArrayList();

                int pages = page;
                imageListDomainList = manyParseData(imageListDomainList,pages);
                //调用onNext
                subscriber.onNext(imageListDomainList);


            }
        });

        Subscriber<List<NoteListDomain>> subscriber = new Subscriber<List<NoteListDomain>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                listener.OnError((Exception) e);
            }

            @Override
            public void onNext(List<NoteListDomain> imageListDomains) {
                Log.i("信息",imageListDomains.get(0).toString());
                listener.onSuccess(imageListDomains);
            }
        };

        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);


    }


    public interface GetNotesListenter {
        void onSuccess(List<NoteListDomain> imageList);

        void OnError(Exception e);
    }


    //多次解析调用
    public List<NoteListDomain>  manyParseData(List<NoteListDomain> imageList, int range){
        for (int i = 0; i < 10; i++) {
            int page = i;
            try {
                String url = Constant.BASE_NOTE_URL + (Constant.current_note - range*10 - page)+".htm";
                String mtitle ="漂亮的女神";
                Log.i("信息",url);
                Document document = Jsoup.connect(url).get();
                Element element = document.getElementsByClass("content").first();
                //解析title
                Elements metainfo = document.getElementsByTag("meta");
                for (int j=0;j<metainfo.size();j++) {
                    String title = metainfo.get(j).attr("content");
                    if(j==(metainfo.size()-1)){
                        mtitle = title;
                    }
                }
                //解析小说
                String text = "";
                Elements img = element.getElementsByTag("p");
                for (Element imageListElement : img) {
                    String tmp = imageListElement.text();
                    if(!TextUtils.isEmpty(tmp)){
                        if(tmp.length()>30) {
                            text = tmp;
                        }
                    }
                }
                imageList.add(new NoteListDomain(text, mtitle, url));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //返回
        return imageList;

    }



}
