package com.my.loopiine.ui.main.view;


import com.my.loopiine.ui.main.domain.NoteListDomain;
import com.my.loopiine.ui.main.domain.VideoListDomain;

import java.util.List;

/**
 * ============================================================
 * 项目名称：Girls
 * 包名称：com.flyou.girls.ui.ImageList.view
 * 文件名：ImageListView
 * 类描述：
 * 创建人：flyou
 * 邮箱：fangjaylong@gmail.com
 * 创建时间：2016/4/19 16:42
 * 修改备注：
 * 版本：@version  V1.0
 * ============================================================
 **/
public interface VideosListView {
    void showLaoding();

    void hideLoading();

    void showLoadFaild(Exception e);

    void receiveVideoList(List<VideoListDomain> imageListDomains);

}
