package com.songsong.jiafangyuan.data.adapter;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.songsong.jiafangyuan.R;
import com.songsong.jiafangyuan.data.fields.HuxingItemFields;
import com.songsong.jiafangyuan.ui.recycler.MultipleItemEntity;
import com.songsong.jiafangyuan.ui.recycler.MultipleRecyclerAdapter;
import com.songsong.jiafangyuan.ui.recycler.MultipleViewHolder;
import com.songsong.jiafangyuan.utils.image.ImageUtils;

import java.util.List;

/**
 * Created by zhanglin on 2017/9/15.
 */

public class HuxingAdapter extends MultipleRecyclerAdapter {
    public HuxingAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(R.layout.item_loupan_huxing);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);

        final String title = entity.getField(HuxingItemFields.TITLE);
        final String taonei = entity.getField(HuxingItemFields.TAONEI);
        final int total = entity.getField(HuxingItemFields.TOTAL);
        final int yishou = entity.getField(HuxingItemFields.YISHOU);
        final String thumb = entity.getField(HuxingItemFields.THUMB);

        AppCompatTextView tvTitle = holder.getView(R.id.loupan_detail_huxing_item_title);
        AppCompatTextView tvTaonei = holder.getView(R.id.loupan_detail_huxing_taonei);
        AppCompatTextView tvTotal = holder.getView(R.id.loupan_detail_huxing_total);
        AppCompatTextView tvYishou = holder.getView(R.id.loupan_detail_huxing_yishou);
        AppCompatImageView ivThumb = holder.getView(R.id.loupan_detail_huxing_item_thumb);

        tvTitle.setText(title);
        tvTaonei.setText("套内 " + taonei + " 平米");
        tvTotal.setText("共" + total + "套");
        tvYishou.setText("已售" + yishou + "套");
        ImageUtils.loadImage(mContext, thumb, ivThumb);
    }
}
