package com.songsong.jiafangyuan.fragment.maifang;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.songsong.jiafangyuan.R;
import com.songsong.jiafangyuan.R2;
import com.songsong.jiafangyuan.base.BaseBackFragment;
import com.songsong.jiafangyuan.data.adapter.MaiFangListApater;
import com.songsong.jiafangyuan.data.converter.FangListDataConverter;
import com.songsong.jiafangyuan.listener.ZuFangItemClickListener2;
import com.songsong.jiafangyuan.ui.recycler.MultipleItemEntity;
import com.songsong.jiafangyuan.ui.recycler.MultipleRecyclerAdapter;
import com.songsong.jiafangyuan.ui.refresh.RefreshHandler;

import java.util.List;

import butterknife.BindView;

/**
 * Created by zhanglin on 2017/9/27.
 */

public class MaifangListFragment extends BaseBackFragment {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_search)
    SearchView mSearchView;

    @BindView(R2.id.rv_maifang)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.srl_maifang)
    SwipeRefreshLayout mRefreshLayout = null;

    private RefreshHandler mRefreshHandler = null;

    @Override
    public Object setLayout() {
        return R.layout.fragment_maifang_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initToolbar();
        mRefreshHandler = RefreshHandler.create(mRefreshLayout, mRecyclerView,
                new FangListDataConverter(), this);
    }

    private void initToolbar() {
        initToolbarNav(mToolbar);
        //设置我们的SearchView
        mSearchView.setQueryHint(getResources().getString(R.string.maifang_search));
        mSearchView.setIconifiedByDefault(true);//设置展开后图标的样式,这里只有两种,一种图标在搜索框外,一种在搜索框内
        mSearchView.onActionViewExpanded();// 写上此句后searchView初始是可以点击输入的状态，如果不写，那么就需要点击下放大镜，才能出现输入框,也就是设置为ToolBar的ActionView，默认展开
        mSearchView.requestFocus();//输入焦点
        mSearchView.setSubmitButtonEnabled(true);//添加提交按钮，监听在OnQueryTextListener的onQueryTextSubmit响应
        mSearchView.setFocusable(true);//将控件设置成可获取焦点状态,默认是无法获取焦点的,只有设置成true,才能获取控件的点击事件
        mSearchView.setIconified(false);//输入框内icon不显示
        mSearchView.requestFocusFromTouch();//模拟焦点点击事件
        mSearchView.setFocusable(false);//禁止弹出输入法，在某些情况下有需要
        mSearchView.clearFocus();//禁止弹出输入法，在某些情况下有需要
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mRefreshHandler.firstPage("api/maifang_list.php?p=1&keyword=" + query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                 // 输入框为空就刷新数据
                if (newText == null || "".equals(newText))
                    mRefreshHandler.onRefresh();
                return true;
            }
        });
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        // 入场动画结束后执行  优化,防动画卡顿
        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        initRefreshLayout();
        initRecyclerView();

        // 请求第一条数据
        mRefreshHandler.firstPage("api/maifang_list.php?p=1&keyword=");
    }

    private void initRefreshLayout() {
        mRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        mRefreshLayout.setProgressViewOffset(true, 120, 300);
    }

    private void initRecyclerView() {
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addOnItemTouchListener(
                ZuFangItemClickListener2.create(this));
    }

    @Override
    public MultipleRecyclerAdapter getAdapter(String response) {
        final List<MultipleItemEntity> data =
                new FangListDataConverter()
                        .setJsonData(response)
                        .convert();
        return new MaiFangListApater(data);
    }
}
