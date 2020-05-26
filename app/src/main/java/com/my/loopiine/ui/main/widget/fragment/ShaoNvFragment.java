package com.my.loopiine.ui.main.widget.fragment;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.my.loopiine.R;
import com.my.loopiine.adapter.recyclerview.CommonAdapter;
import com.my.loopiine.adapter.recyclerview.ViewHolder;
import com.my.loopiine.ui.Constant;
import com.my.loopiine.ui.main.domain.AVListDomain;
import com.my.loopiine.ui.main.persenter.AVListPersenter;
import com.my.loopiine.ui.main.persenter.AVListPersenterImpl;
import com.my.loopiine.ui.main.view.AVListView;
import com.my.loopiine.ui.typeImageList.widget.TypeImageActivity2;

import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

/**
 * 项目名称：Girls
 **/
public class ShaoNvFragment extends BaseFragment implements AVListView, SwipeRefreshLayout.OnRefreshListener {
    private AVListPersenter mPersenter;
    private String mType;
    private int mCurrentPage = 0;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private CommonAdapter mAdapter;
    private List<AVListDomain> mImageListDomains;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initData() {
        mPersenter = new AVListPersenterImpl(this);
        switch (getClass().getSimpleName()) {
            case "NewFragment":
                mType = Constant.NEW;
                break;
            case "XinGanFragment":
                mType = Constant.XINGGAN;
                break;
            case "ShaoNvFragment":
                mType = Constant.SHAONV;
                break;
            case "AVNoteFragment":
                break;
            default:
                mType = Constant.NEW;
                break;


        }
        mPersenter.startGetImageList(mType, mCurrentPage);
    }

    @Override
    protected void initView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) context.findViewById(R.id.sw_layout);
        mRecyclerView = (RecyclerView) context.findViewById(R.id.receiverview);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void showLaoding() {
        mSwipeRefreshLayout.measure(View.MEASURED_SIZE_MASK, View.MEASURED_HEIGHT_STATE_SHIFT);
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoadFaild(Exception e) {
        if (mCurrentPage!=1){
            Toast.makeText(context, "你太贪心了，已经没有更多图片了", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void receiveImageList(List<AVListDomain> imageListDomains) {
        if (null == imageListDomains || imageListDomains.size() == 0) {
            Toast.makeText(context, "没有发现更多数据", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mCurrentPage == 0) {
            mImageListDomains=imageListDomains;
            mAdapter = new CommonAdapter<AVListDomain>(context, R.layout.view_item_fragment_main, mImageListDomains) {
                @Override
                public void convert(ViewHolder holder, final AVListDomain imageListDomain) {
                    holder.setText(R.id.tv_title, imageListDomain.getTitle());
                    holder.setImageWithUrl(R.id.iv_cover, imageListDomain.getImageUrl());
                    holder.setOnClickListener(R.id.iv_cover, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(context,TypeImageActivity2.class);
                            intent.putExtra("linkUrl",imageListDomain.getParentUrl());
                            intent.putExtra("title",imageListDomain.getTitle());
                            ActivityOptionsCompat options =
                                    ActivityOptionsCompat.makeSceneTransitionAnimation(context);
                            ActivityCompat.startActivity(context, intent, options.toBundle());
                        }
                    });
                }
            };
            mLayoutManager = new LinearLayoutManager(context);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setItemAnimator(new SlideInLeftAnimator());

            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.addOnScrollListener(mOnScrollListener);
        } else {
            int lastPosition = mImageListDomains.size() - 1;
            mImageListDomains.addAll(imageListDomains);
            mAdapter.notifyItemRangeInserted(lastPosition, imageListDomains.size());
        }


    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        private int lastVisibleItem;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == mAdapter.getItemCount()
                    ) {
                mCurrentPage++;
                mPersenter.startGetImageList(mType, mCurrentPage);
            }
        }
    };

    @Override
    public void onRefresh() {
        mCurrentPage=1;
        mPersenter.startGetImageList(mType, mCurrentPage);
    }
}
