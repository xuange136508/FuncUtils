package com.my.loopiine.ui.typeImageList.persenter;


import com.my.loopiine.ui.typeImageList.domain.TypeImageDomain2;
import com.my.loopiine.ui.typeImageList.model.TypeImageListModel2;
import com.my.loopiine.ui.typeImageList.model.TypeImageListModelImpl2;
import com.my.loopiine.ui.typeImageList.view.TypeImageListView2;

import java.util.List;

/**
 * ============================================================
 * 项目名称：Girls
 * 包名称：com.flyou.girls.ui.typeImageList.persenter
 * 文件名：TypeImageListPersenterImpl
 * 类描述：
 * 创建人：flyou
 * 邮箱：fangjaylong@gmail.com
 * 创建时间：2016/4/20 14:48
 * 修改备注：
 * 版本：@version  V1.0
 * ============================================================
 **/
public class TypeImageListPersenterImpl2 implements TypeImageListModelImpl2.OnGetTypeImageListener,TypeImageListPersenter2 {
    private TypeImageListView2 mTypeImageListView;
    private TypeImageListModel2 mTypeImageListModel;

    public TypeImageListPersenterImpl2(TypeImageListView2 typeImageListView) {
        mTypeImageListView = typeImageListView;
        mTypeImageListModel=new TypeImageListModelImpl2();
    }

    @Override
    public void onSuccess(List<TypeImageDomain2> typeImageDomains) {
        mTypeImageListView.hideLoading();
        mTypeImageListView.receiveImageList(typeImageDomains);
    }

    @Override
    public void OnError(Exception e) {
        mTypeImageListView.hideLoading();
    }



    @Override
    public void startGetImageList(String url) {
        mTypeImageListModel.getTypeImageList(url,this);
        mTypeImageListView.showLaoding();

    }
}
