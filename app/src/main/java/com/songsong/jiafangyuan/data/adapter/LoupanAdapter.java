package com.songsong.jiafangyuan.data.adapter;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.songsong.jiafangyuan.R;
import com.songsong.jiafangyuan.data.fields.FangItemFields;
import com.songsong.jiafangyuan.ui.recycler.MultipleFields;
import com.songsong.jiafangyuan.ui.recycler.MultipleItemEntity;
import com.songsong.jiafangyuan.ui.recycler.MultipleRecyclerAdapter;
import com.songsong.jiafangyuan.ui.recycler.MultipleViewHolder;
import com.songsong.jiafangyuan.utils.image.ImageUtils;

import java.util.List;

/**
 * Created by zhanglin on 2017/9/15.
 */

public class LoupanAdapter extends MultipleRecyclerAdapter {
    public LoupanAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(R.layout.item_loupan);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);

        // 数据
        final int id = entity.getField(MultipleFields.ID);
        final String title = entity.getField(MultipleFields.TITLE);
        final float price = entity.getField(FangItemFields.PRICE);
        final String thumb = entity.getField(FangItemFields.THUMB);
        // 控件
        AppCompatTextView tvTitle = holder.getView(R.id.loupan_item_title);
        AppCompatTextView tvPrice = holder.getView(R.id.loupan_item_price);
        AppCompatImageView mThumb = holder.getView(R.id.loupan_item_thumb);
        // 赋值
        tvTitle.setText(title);
        tvPrice.setText(price + "万");
        ImageUtils.loadImage(mContext, thumb, mThumb);
    }
}
