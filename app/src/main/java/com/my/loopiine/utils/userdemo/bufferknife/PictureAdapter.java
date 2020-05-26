package com.my.loopiine.utils.userdemo.bufferknife;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 注解在适配器上的使用

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.PictureHolder> {

    List<PictureListItem.TngouEntity> tngouEntityList;
    private Activity context;

    public PictureAdapter(List<PictureListItem.TngouEntity> tngouEntityList, Activity context) {
        this.tngouEntityList = tngouEntityList;
        this.context = context;
    }

    @Override
    public PictureHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.picture_list_item, parent, false);
        return new PictureHolder(view);
    }

    @Override
    public void onBindViewHolder(PictureHolder holder, final int position) {

    }

    @Override
    public int getItemCount() {
        return tngouEntityList.size();
    }

    class PictureHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.picture)
        ImageView picture;
        @Bind(R.id.picture_title)
        TextView pictureTitle;
        @Bind(R.id.root)
        LinearLayout root;
        @Bind(R.id.gallery_size)
        TextView gallerySize;
        public PictureHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
 */