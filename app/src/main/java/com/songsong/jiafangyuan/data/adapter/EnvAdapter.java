package com.songsong.jiafangyuan.data.adapter;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.songsong.jiafangyuan.R;
import com.songsong.jiafangyuan.data.fields.EnvItemFields;
import com.songsong.jiafangyuan.ui.recycler.MultipleItemEntity;
import com.songsong.jiafangyuan.ui.recycler.MultipleRecyclerAdapter;
import com.songsong.jiafangyuan.ui.recycler.MultipleViewHolder;
import com.songsong.jiafangyuan.utils.image.ImageUtils;

import java.util.List;

/**
 * Created by zhanglin on 2017/9/27.
 */

public class EnvAdapter extends MultipleRecyclerAdapter {
    public EnvAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(R.layout.item_fang_detail_env);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        // 数据
        final String icon = entity.getField(EnvItemFields.ICON);
        final String name = entity.getField(EnvItemFields.NAME);
        // 控件
        AppCompatImageView ivIcon = holder.getView(R.id.fang_detail_env_icon);
        AppCompatTextView tvName = holder.getView(R.id.fang_detal_env_name);

        tvName.setText(name);
        ImageUtils.loadImage(mContext, icon, ivIcon);
    }
}
