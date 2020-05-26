package com.my.loopiine.ui.main.widget.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.my.loopiine.R;
import com.my.loopiine.adapter.recyclerview.CommonAdapter;
import com.my.loopiine.adapter.recyclerview.ViewHolder;
import com.my.loopiine.ui.Constant;
import com.my.loopiine.ui.TextDetialActivity;
import com.my.loopiine.ui.main.domain.NoteListDomain;
import com.my.loopiine.ui.main.domain.VideoListDomain;
import com.my.loopiine.ui.main.persenter.NotesListPersenterImpl;
import com.my.loopiine.ui.main.persenter.NotesPersenter;
import com.my.loopiine.ui.main.persenter.VideosListPersenterImpl;
import com.my.loopiine.ui.main.persenter.VideosPersenter;
import com.my.loopiine.ui.main.view.NotesListView;
import com.my.loopiine.ui.main.view.VideosListView;
import com.my.loopiine.utils.userdemo.vitamio.VideoPlayerActivity;
import com.socks.library.KLog;

import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

/**
 * 项目名称：Girls
 **/
public class VideosNoteFragment extends BaseFragment implements VideosListView, SwipeRefreshLayout.OnRefreshListener {

    private VideosPersenter mPersenter;
    private String mType;
    private int mCurrentPage = 0;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mLayoutManager;
    private CommonAdapter mAdapter;
    private List<VideoListDomain> mVideosListDomain;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initData() {
        mPersenter = new VideosListPersenterImpl(this);
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
        mPersenter.startGetVideoList(mType, mCurrentPage);
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
    public void receiveVideoList(List<VideoListDomain> xVideoListDomain) {
        if (null == xVideoListDomain || xVideoListDomain.size() == 0) {
            Toast.makeText(context, "没有发现更多数据", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mCurrentPage == 0) {
            mVideosListDomain = xVideoListDomain;
            mAdapter = new CommonAdapter<VideoListDomain>(context, R.layout.view_item_fragment_text, mVideosListDomain) {
                @Override
                public void convert(ViewHolder holder, final VideoListDomain imageListDomain) {

                    holder.setText(R.id.tv_title, imageListDomain.getTitle());
                    holder.setOnClickListener(R.id.iv_cover, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(context,VideoPlayerActivity.class);
                            intent.putExtra("linkUrl",imageListDomain.getParentUrl());
                            intent.putExtra("title",imageListDomain.getTitle());
                            intent.putExtra("videoUrl",imageListDomain.getVideoUrl());
                            intent.putExtra("imageUrl",imageListDomain.getImageUrl());
                            startActivity(intent);
                        }
                    });
                }
            };
            mLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setItemAnimator(new SlideInLeftAnimator());
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.addOnScrollListener(mOnScrollListener);
        } else {
            int lastPosition = xVideoListDomain.size() - 1;
            mVideosListDomain.addAll(xVideoListDomain);
            mAdapter.notifyItemRangeInserted(lastPosition, xVideoListDomain.size());
        }


    }

//    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
//
//        private int lastVisibleItem;
//
//        @Override
//        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//            super.onScrolled(recyclerView, dx, dy);
//            lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
//        }
//
//        @Override
//        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//            super.onScrollStateChanged(recyclerView, newState);
//            if (newState == RecyclerView.SCROLL_STATE_IDLE
//                    && lastVisibleItem + 1 == mAdapter.getItemCount()) {
//                mCurrentPage++;
//                mPersenter.startGetVideoList(mType, mCurrentPage);
//            }
//        }
//    };


    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        private int lastVisibleItem;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (calculateRecyclerViewFirstPosition() == mAdapter.getItemCount() - 1 ) {
                mCurrentPage++;
                mPersenter.startGetVideoList(mType, mCurrentPage);
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }
    };


    private int[] mVisiblePositions;
    /**
     * 计算RecyclerView当前第一个完全可视位置
     */
    private int calculateRecyclerViewFirstPosition() {
        // 判断LayoutManager类型获取第一个完全可视位置
        if (mLayoutManager instanceof StaggeredGridLayoutManager) {
            if (mVisiblePositions == null) {
                mVisiblePositions = new int[(mLayoutManager).getSpanCount()];
            }
            ((StaggeredGridLayoutManager) mLayoutManager)
                    .findLastCompletelyVisibleItemPositions(mVisiblePositions);
            int max = -1;
            for (int pos : mVisiblePositions) {
                max = Math.max(max, pos);
            }
            return max;
        }
        return 0;
    }


    @Override
    public void onRefresh() {
        mCurrentPage=1;
        mPersenter.startGetVideoList(mType, mCurrentPage);
    }
}
