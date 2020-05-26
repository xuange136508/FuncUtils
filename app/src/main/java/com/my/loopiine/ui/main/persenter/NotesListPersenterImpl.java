package com.my.loopiine.ui.main.persenter;

import com.my.loopiine.ui.main.domain.NoteListDomain;
import com.my.loopiine.ui.main.model.AVListModel;
import com.my.loopiine.ui.main.model.AVListModelImpl;
import com.my.loopiine.ui.main.model.NotesListModel;
import com.my.loopiine.ui.main.model.NotesListModelImpl;
import com.my.loopiine.ui.main.view.NotesListView;

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
public class NotesListPersenterImpl implements NotesListModelImpl.GetNotesListenter,NotesPersenter{

    private NotesListView imageListView;
    private NotesListModel imageListModel;

    public NotesListPersenterImpl(NotesListView imageListView) {
        this.imageListView = imageListView;
        this.imageListModel=new NotesListModelImpl();
    }


    @Override
    public void onSuccess(List<NoteListDomain> imageList) {
        imageListView.receiveNotesList(imageList);
        imageListView.hideLoading();
    }

    @Override
    public void OnError(Exception e) {
        imageListView.showLoadFaild(e);
        imageListView.hideLoading();
    }

    @Override
    public void startGetNoteList(String type,int page) {
        imageListView.showLaoding();
        imageListModel.GetNotesList(type,page,this);
    }
}
