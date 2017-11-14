package com.songsong.jiafangyuan.listener;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.songsong.jiafangyuan.base.BaseBackFragment;
import com.songsong.jiafangyuan.fragment.detail.LoupanDetailFragment;
import com.songsong.jiafangyuan.fragment.detail.ZuFangDetailFragment;
import com.songsong.jiafangyuan.ui.recycler.MultipleFields;
import com.songsong.jiafangyuan.ui.recycler.MultipleItemEntity;

/**
 * Created by zhanglin on 2017/9/27.
 */

public class LoupanItemClickListener extends SimpleClickListener {
    private final BaseBackFragment FRAGMENT;

    private LoupanItemClickListener(BaseBackFragment fragment) {
        FRAGMENT = fragment;
    }

    public static SimpleClickListener create(BaseBackFragment fragment) {
        return new LoupanItemClickListener(fragment);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final MultipleItemEntity entity = (MultipleItemEntity) baseQuickAdapter.getData().get(position);
        final int mId = entity.getField(MultipleFields.ID);
        final LoupanDetailFragment fragment = LoupanDetailFragment.create(mId);
        FRAGMENT.getSupportDelegate().start(fragment);
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
