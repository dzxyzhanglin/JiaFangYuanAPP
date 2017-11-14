package com.songsong.jiafangyuan.fragment.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.songsong.jiafangyuan.R;
import com.songsong.jiafangyuan.R2;
import com.songsong.jiafangyuan.base.BaseBackFragment;
import com.songsong.jiafangyuan.data.adapter.EnvAdapter;
import com.songsong.jiafangyuan.data.adapter.FangAdapter;
import com.songsong.jiafangyuan.data.adapter.HuxingAdapter;
import com.songsong.jiafangyuan.data.converter.EnvDataConverter;
import com.songsong.jiafangyuan.data.converter.FangDataConverter;
import com.songsong.jiafangyuan.data.converter.HuxingDataConverter;
import com.songsong.jiafangyuan.listener.ZuFangItemClickListener2;
import com.songsong.jiafangyuan.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by zhanglin on 2017/9/28.
 */

public class LoupanInfoFragment extends BaseBackFragment {
    private static final String ARG_LOUPAN_DATA = "ARG_LOUPAN_DATA";
    private JSONObject mData = null;

    @BindView(R2.id.rv_loupan_env)
    RecyclerView mEnvRecyclerView = null;
    @BindView(R2.id.rv_loupan_huxing)
    RecyclerView mHuxingRecyclerView = null;

    public static LoupanInfoFragment create(String fangInfo) {
        final Bundle args = new Bundle();
        args.putString(ARG_LOUPAN_DATA, fangInfo);
        final LoupanInfoFragment delegate = new LoupanInfoFragment();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle args = getArguments();
        if (args != null) {
            final String fangData = args.getString(ARG_LOUPAN_DATA);
            mData = JSON.parseObject(fangData);
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_loupan_info;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        // 周边环境
        initEnvLayout();
        //  户型
        initHuxingLayout();;
    }


    private void initEnvLayout() {
        final GridLayoutManager manager = new GridLayoutManager(getContext(), 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mEnvRecyclerView.setLayoutManager(manager);

        final ArrayList<MultipleItemEntity> data =
                new EnvDataConverter().convert(mData);
        final EnvAdapter adapter = new EnvAdapter(data);
        mEnvRecyclerView.setAdapter(adapter);
    }

    private void initHuxingLayout() {
        final LinearLayoutManager manager = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mHuxingRecyclerView.setLayoutManager(manager);

        final ArrayList<MultipleItemEntity> data =
                new HuxingDataConverter()
                        .setJsonData(mData.toString())
                        .convert();
        final HuxingAdapter adapter = new HuxingAdapter(data);
        mHuxingRecyclerView.setAdapter(adapter);
    }
}
