package com.songsong.jiafangyuan.fragment.user.item;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.songsong.jiafangyuan.R;
import com.songsong.jiafangyuan.R2;
import com.songsong.jiafangyuan.base.BaseBackFragment;
import com.songsong.jiafangyuan.utils.log.LatteLogger;
import com.songsong.jiafangyuan.utils.net.RestClient;
import com.songsong.jiafangyuan.utils.net.callback.IError;
import com.songsong.jiafangyuan.utils.net.callback.ISuccess;

import butterknife.BindView;

/**
 * 已发布的需求
 * Created by zhanglin on 2017/10/1.
 */

public class DemandFragment extends BaseBackFragment {
    @BindView(R2.id.toolbar)
    Toolbar mToolbar;

    @BindView(R2.id.tab_demand)
    TabLayout mTabLayout = null;
    @BindView(R2.id.vp_demand)
    ViewPager mViewPager = null;

    @Override
    public Object setLayout() {
        return R.layout.fragment_demand;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mToolbar.setTitle(R.string.demand);
        initToolbarNav(mToolbar);

        initTabLayout();
        initData();
    }

    private void initTabLayout() {
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setSelectedTabIndicatorColor(
                ContextCompat.getColor(getContext(), R.color.colorAccent));
        mTabLayout.setTabTextColors(ColorStateList.valueOf(Color.WHITE));
        mTabLayout.setBackgroundColor(
                ContextCompat.getColor(getContext(), R.color.colorPrimary));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initData() {
        RestClient.builder()
                .url("api/published.php")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final PagerAdapter adapter =
                                new DemandPagerAdapter(getChildFragmentManager(), response);
                        mViewPager.setAdapter(adapter);
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Toast.makeText(getContext(), R.string.http_error, Toast.LENGTH_LONG).show();
                    }
                })
                .build()
                .get();
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        // 入场动画结束后执行  优化,防动画卡顿
        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }
}
