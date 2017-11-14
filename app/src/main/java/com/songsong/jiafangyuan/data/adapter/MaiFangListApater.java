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
 * Created by zhanglin on 2017/9/3.
 */

public class MaiFangListApater extends MultipleRecyclerAdapter {

    public MaiFangListApater(List<MultipleItemEntity> data) {
        super(data);
        addItemType(R.layout.item_fang);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity item) {
        super.convert(holder, item);
        // 数据
        final int id = item.getField(MultipleFields.ID);
        final String title = item.getField(MultipleFields.TITLE);
        final int type = item.getField(FangItemFields.TYPE);
        final float price = item.getField(FangItemFields.PRICE);
        final String thumb = item.getField(FangItemFields.THUMB);
        // 控件
        AppCompatTextView tvTitle = holder.getView(R.id.fang_item_title);
        AppCompatTextView tvType = holder.getView(R.id.fang_item_type);
        AppCompatTextView tvPrice = holder.getView(R.id.fang_item_price);
        AppCompatImageView mThumb = holder.getView(R.id.fang_item_thumb);
        // 赋值
        tvTitle.setText(title);
        tvType.setText(type == 1 ? "委托代理交易" : "双方直接交易");
        tvPrice.setText((price / 10000) + "万");
        ImageUtils.loadImage(mContext, thumb, mThumb);
    }
}
