package com.my.loopiine.ui.main.persenter;

import com.my.loopiine.ui.main.domain.NoteListDomain;
import com.my.loopiine.ui.main.domain.VideoListDomain;
import com.my.loopiine.ui.main.model.NotesListModel;
import com.my.loopiine.ui.main.model.NotesListModelImpl;
import com.my.loopiine.ui.main.model.VideosListModel;
import com.my.loopiine.ui.main.model.VideosListModelImpl;
import com.my.loopiine.ui.main.view.NotesListView;
import com.my.loopiine.ui.main.view.VideosListView;

import java.util.List;

/**
 * ============================================================
 * 项目名称：Girls
 * 包名称：com.flyou.girls.ui.ImageList.persenter
 * 文件名：ImageListPersenterImpl
 * 类描述：
 * 创建人：flyou
 * 邮箱：fangjaylong@gmail.com
 * 创建时间：2016/4/19 16:48
 * 修改备注：
 * 版本：@version  V1.0
 * ============================================================
 **/
public class VideosListPersenterImpl implements VideosListModelImpl.GetVideosListenter,VideosPersenter{

    private VideosListView imageListView;
    private VideosListModel imageListModel;

    public VideosListPersenterImpl(VideosListView imageListView) {
        this.imageListView = imageListView;
        this.imageListModel=new VideosListModelImpl();
    }


    @Override
    public void onSuccess(List<VideoListDomain> imageList) {
        imageListView.receiveVideoList(imageList);
        imageListView.hideLoading();
    }

    @Override
    public void OnError(Exception e) {
        imageListView.showLoadFaild(e);
        imageListView.hideLoading();
    }

    @Override
    public void startGetVideoList(String type,int page) {
        imageListView.showLaoding();
        imageListModel.GetVideosList(type,page,this);
    }
}
