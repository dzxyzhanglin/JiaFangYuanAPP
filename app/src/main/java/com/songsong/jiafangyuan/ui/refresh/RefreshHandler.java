package com.songsong.jiafangyuan.ui.refresh;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.songsong.jiafangyuan.app.Latte;
import com.songsong.jiafangyuan.base.BaseBackFragment;
import com.songsong.jiafangyuan.ui.recycler.DataConverter;
import com.songsong.jiafangyuan.ui.recycler.MultipleRecyclerAdapter;
import com.songsong.jiafangyuan.utils.log.LatteLogger;
import com.songsong.jiafangyuan.utils.net.RestClient;
import com.songsong.jiafangyuan.utils.net.callback.ISuccess;

/**
 * Created by zhanglin on 2017/8/22.
 */

public class RefreshHandler implements
        SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener {
    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private final PagingBean BEAN;
    private final RecyclerView RECYCLERVIEW;
    private MultipleRecyclerAdapter mAdapter = null;
    private final DataConverter CONVERTER;
    private final BaseBackFragment DELEGATE;
    private String URL = null;

    private RefreshHandler(SwipeRefreshLayout swipeRefreshLayout,
                           RecyclerView recyclerView,
                           DataConverter converter,
                           BaseBackFragment delegate,
                           PagingBean bean) {
        this.REFRESH_LAYOUT = swipeRefreshLayout;
        this.RECYCLERVIEW = recyclerView;
        this.CONVERTER = converter;
        this.DELEGATE = delegate;
        this.BEAN = bean;
        REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    public static RefreshHandler create(SwipeRefreshLayout swipeRefreshLayout,
                                        RecyclerView recyclerView,
                                        DataConverter converter,
                                        BaseBackFragment delegate) {
        return new RefreshHandler(swipeRefreshLayout, recyclerView, converter, delegate, new PagingBean());
    }

    private void refresh() {
        REFRESH_LAYOUT.setRefreshing(true);
        Latte.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                RestClient.builder()
                        .url(URL)
                        .success(new ISuccess() {
                            @Override
                            public void onSuccess(String response) {
                                REFRESH_LAYOUT.setRefreshing(false);
                                final JSONObject object = JSON.parseObject(response);
                                BEAN.setTotal(object.getInteger("total"))
                                        .setPageSize(object.getInteger("pageSize"));
                                //设置Adapter
                                mAdapter = DELEGATE.getAdapter(response);
                                mAdapter.setOnLoadMoreListener(RefreshHandler.this, RECYCLERVIEW);
                                RECYCLERVIEW.setAdapter(mAdapter);
                                BEAN.setPageIndex(1);
                                BEAN.setCurrentCount(mAdapter.getData().size());
                            }
                        })
                        .build()
                        .get();
            }
        }, 500);
    }

    public void firstPage(String url) {
        this.URL = url;
        BEAN.setDelayed(500);
        RestClient.builder()
                .url(url)
                .loader(DELEGATE.getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject object = JSON.parseObject(response);
                        BEAN.setTotal(object.getInteger("total"))
                                .setPageSize(object.getInteger("pageSize"));
                        //设置Adapter
                        mAdapter = DELEGATE.getAdapter(response);
                        mAdapter.setOnLoadMoreListener(RefreshHandler.this, RECYCLERVIEW);
                        RECYCLERVIEW.setAdapter(mAdapter);
                        BEAN.addIndex();
                    }
                })
                .build()
                .get();
    }

    private void paging(final String url) {
        final int pageSize = BEAN.getPageSize();
        final int currentCount = BEAN.getCurrentCount();
        final int total = BEAN.getTotal();
        final int index = BEAN.getPageIndex();

        if (mAdapter.getData().size() < pageSize || currentCount >= total) {
            mAdapter.loadMoreEnd(true);
            Toast.makeText(Latte.getApplicationContext(),
                    "已没有更多数据", Toast.LENGTH_LONG).show();
        } else {
            Latte.getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    RestClient.builder()
                            .url(url + index)
                            .success(new ISuccess() {
                                @Override
                                public void onSuccess(String response) {
                                    CONVERTER.clearData();
                                    mAdapter.addData(CONVERTER.setJsonData(response).convert());
                                    //累加数量
                                    BEAN.setCurrentCount(mAdapter.getData().size());
                                    mAdapter.loadMoreComplete();
                                    BEAN.addIndex();
                                }
                            })
                            .build()
                            .get();
                }
            }, 500);
        }
    }

    @Override
    public void onRefresh() {
        LatteLogger.d("刷新");
        refresh();
    }

    @Override
    public void onLoadMoreRequested() {
        LatteLogger.d("加载更多");
        paging(URL);
    }
}
