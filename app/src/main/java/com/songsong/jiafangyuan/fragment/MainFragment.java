package com.songsong.jiafangyuan.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.songsong.jiafangyuan.R;
import com.songsong.jiafangyuan.event.StartBrotherEvent;
import com.songsong.jiafangyuan.event.TabSelectedEvent;
import com.songsong.jiafangyuan.fragment.home.HomeTabFragment;
import com.songsong.jiafangyuan.fragment.maifang.MaiFangTabFragment;
import com.songsong.jiafangyuan.fragment.user.UserFangTabFragment;
import com.songsong.jiafangyuan.fragment.zufang.ZuFangTabFragment;
import com.songsong.jiafangyuan.ui.view.BottomBar;
import com.songsong.jiafangyuan.ui.view.BottomBarTab;
import com.songsong.jiafangyuan.utils.log.LatteLogger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by zhanglin on 2017/9/23.
 */

public class MainFragment extends SupportFragment {
    private static final int REQ_MSG = 10;

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;

    private SupportFragment[] mFragments = new SupportFragment[4];

    private BottomBar mBottomBar;

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public BottomBar getmBottomBar() {
        return mBottomBar;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SupportFragment firstFragment = findChildFragment(HomeTabFragment.class);
        if (firstFragment == null) {
            mFragments[FIRST] = HomeTabFragment.newInstance();
            mFragments[SECOND] = MaiFangTabFragment.newInstance();
            mFragments[THIRD] = ZuFangTabFragment.newInstance();
            mFragments[FOURTH] = UserFangTabFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_tab_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOURTH]);
        } else {
            mFragments[FIRST] = firstFragment;
            mFragments[SECOND] = findChildFragment(MaiFangTabFragment.class);
            mFragments[THIRD] = findChildFragment(ZuFangTabFragment.class);
            mFragments[FOURTH] = findChildFragment(UserFangTabFragment.class);
        }
    }

    private void initView(View view) {
        EventBus.getDefault().register(this);
        mBottomBar = (BottomBar) view.findViewById(R.id.bottomBar);

        mBottomBar
                .addItem(new BottomBarTab(_mActivity, R.drawable.ic_index_white, "首页"))
                .addItem(new BottomBarTab(_mActivity, R.drawable.ic_maifang_white, "买房"))
                .addItem(new BottomBarTab(_mActivity, R.drawable.ic_zufang_white, "租房"))
                .addItem(new BottomBarTab(_mActivity, R.drawable.ic_user_white, "我的"));

        // 模拟未读消息
        //mBottomBar.getItem(FOURTH).setUnreadCount(9);

        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
                // 在FirstPagerFragment,FirstHomeFragment中接收, 因为是嵌套的Fragment
                // 主要为了交互: 重选tab 如果列表不在顶部则移动到顶部,如果已经在顶部,则刷新
                EventBus.getDefault().post(new TabSelectedEvent(position));
            }
        });
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == REQ_MSG && resultCode == RESULT_OK) {

        }
    }

    /**
     * start other BrotherFragment
     */
    @Subscribe
    public void startBrother(StartBrotherEvent event) {
        start(event.targetFragment);
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }
}
