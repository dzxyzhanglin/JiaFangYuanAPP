package com.songsong.jiafangyuan.fragment.zufang;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.songsong.jiafangyuan.R;
import com.songsong.jiafangyuan.R2;
import com.songsong.jiafangyuan.base.BaseBackFragment;
import com.songsong.jiafangyuan.data.adapter.PublishedAdapter;
import com.songsong.jiafangyuan.data.converter.PublishedItemDataConverter;
import com.songsong.jiafangyuan.ui.recycler.BaseDecoration;
import com.songsong.jiafangyuan.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by zhanglin on 2017/10/1.
 */

public class ZuFangPublishedFragment extends BaseBackFragment {
    private static final String ARG_DATA = "ARG_DATA";

    @BindView(R2.id.rv_zufang_published)
    RecyclerView mRecyclerView = null;

    private static JSONArray mData = null;

    public static ZuFangPublishedFragment create(String data) {
        final Bundle args = new Bundle();
        args.putString(ARG_DATA, data);
        final ZuFangPublishedFragment fragment = new ZuFangPublishedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            mData = JSON.parseArray(args.getString(ARG_DATA));
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_published_item;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);

        final ArrayList<MultipleItemEntity> data =
                new PublishedItemDataConverter()
                .convert(mData);
        PublishedAdapter adapter = new PublishedAdapter(data);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addItemDecoration(
                BaseDecoration.create(Color.GRAY, 1));
    }
}
