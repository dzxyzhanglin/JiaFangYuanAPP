package com.songsong.jiafangyuan.fragment.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.ToxicBakery.viewpager.transforms.DefaultTransformer;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.songsong.jiafangyuan.R;
import com.songsong.jiafangyuan.R2;
import com.songsong.jiafangyuan.base.BaseBackFragment;
import com.songsong.jiafangyuan.ui.banner.HolderCreator;
import com.songsong.jiafangyuan.utils.log.LatteLogger;
import com.songsong.jiafangyuan.utils.net.RestClient;
import com.songsong.jiafangyuan.utils.net.callback.ISuccess;
import com.songsong.jiafangyuan.utils.pay.AppPay;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhanglin on 2017/9/27.
 */

public class ZuFangDetailFragment extends BaseBackFragment {
    private int mId = -1;
    private static final String ARG_FANG_ID = "ARG_FANG_ID";

    @BindView(R2.id.detail_banner)
    ConvenientBanner<String> mBanner = null;
    @BindView(R2.id.toolbar)
    Toolbar mToolbar;

    @OnClick(R2.id.tv_zufang_trade)
    void onTrade() {
        AppPay.create(this)
                .beginPayDialog();
    }

    public static ZuFangDetailFragment create(int id) {
        final Bundle args = new Bundle();
        args.putInt(ARG_FANG_ID, id);
        final ZuFangDetailFragment fragment = new ZuFangDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            mId = args.getInt(ARG_FANG_ID);
            LatteLogger.d("fang id = " + mId);
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_zufang_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initToolbarNav(mToolbar);
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        // 入场动画结束后执行  优化,防动画卡顿
        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        initData();
    }

    private void initData() {
        RestClient.builder()
                .loader(getContext())
                .url("api/maifang_detail.php?id=" + mId)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject data =
                                JSON.parseObject(response).getJSONObject("data");
                        mToolbar.setTitle(data.getString("title"));

                        initBanner(data);
                        initFangInfo(data);
                    }
                })
                .build()
                .get();
    }

    private void initBanner(JSONObject data) {
        final JSONArray array = data.getJSONArray("banners");
        final List<String> images = new ArrayList<>();
        final int size = array.size();
        for (int i = 0; i < size; i++) {
            images.add(array.getString(i));
        }
        mBanner
                .setPages(new HolderCreator(), images)
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setPageTransformer(new DefaultTransformer())
                .startTurning(3000)
                .setCanLoop(true);
    }

    private void initFangInfo(JSONObject data) {
        final String fangData = data.toJSONString();
        getSupportDelegate().
                loadRootFragment(R.id.frame_fang_info, FangInfoFragment.create(fangData));
    }
}
