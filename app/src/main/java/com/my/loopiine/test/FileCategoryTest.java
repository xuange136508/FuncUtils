/*
 * Copyright (c) 2010-2011, The MiCode Open Source Community (www.micode.net)
 *
 * This file is part of FileExplorer.
 *
 * FileExplorer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FileExplorer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SwiFTP.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.my.loopiine.test;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Video;
import android.util.Log;
/**
 * //测试拿媒体数据库视频的数量
   new FileCategoryTest().refreshCategoryInfo(TestActivity.this);
 * */
public class FileCategoryTest {

    private static final String LOG_TAG = "FileCategoryHelper";

    public void refreshCategoryInfo(Context mContext) {
        Log.e(LOG_TAG, "start refreshInfo...");
        // query database
        String volumeName = "external";//internal external

//        Uri uri = Audio.Media.getContentUri(volumeName);
//        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//        refreshMediaCategory( mContext ,uri);

//        uri = Video.Media.getContentUri(volumeName);
//        uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
//        refreshMediaCategory( mContext,uri);

        Uri uri = Images.Media.getContentUri(volumeName);
//        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        refreshMediaCategory( mContext,uri);

//        uri = MediaStore.Files.getContentUri(volumeName);
//        refreshMediaCategory( mContext,uri);
    }


    //获取各类媒体文件size总数
    private boolean refreshMediaCategory(Context mContext, Uri uri) {
        String[] columns = new String[] {
                "COUNT(*)", "SUM(_size)"
        };
        Log.e(LOG_TAG, "start query uri:" + uri);
        Cursor c = mContext.getContentResolver().query(uri,null,null, null, null);
        if (c == null) {
            Log.e(LOG_TAG, "fail to query uri:" + uri);
            return false;
        }

        if (c.moveToNext()) {
            Log.e(LOG_TAG, "moveToNext");
//            Log.e(LOG_TAG, "count:" + c.getLong(0) + " size:" + c.getLong(1));
            c.close();
            return true;
        }

        return false;
    }

}
