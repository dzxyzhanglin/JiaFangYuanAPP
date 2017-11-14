package com.songsong.jiafangyuan.data.adapter;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.songsong.jiafangyuan.R;
import com.songsong.jiafangyuan.data.fields.FangItemFields;
import com.songsong.jiafangyuan.ui.recycler.MultipleFields;
import com.songsong.jiafangyuan.ui.recycler.MultipleItemEntity;
import com.songsong.jiafangyuan.ui.recycler.MultipleRecyclerAdapter;
import com.songsong.jiafangyuan.ui.recycler.MultipleViewHolder;
import com.songsong.jiafangyuan.utils.image.ImageUtils;

import java.util.List;

/**
 * Created by zhanglin on 2017/8/23.
 */

public final class FangAdapter extends MultipleRecyclerAdapter {

    public FangAdapter(List<MultipleItemEntity> data) {
        super(data);
        // 添加布局
        addItemType(R.layout.item_index);
    }

    @Override
    protected void convert(MultipleViewHolder holder, final MultipleItemEntity entity) {
        super.convert(holder, entity);
        // 取出所有的值
        final int id = entity.getField(MultipleFields.ID);
        final String title = entity.getField(FangItemFields.TITLE);
        final String floor = entity.getField(FangItemFields.FLOOR);
        final String area = entity.getField(FangItemFields.AREA);
        final String xiaoqu = entity.getField(FangItemFields.XIAOQU);
        final String desc = entity.getField(FangItemFields.DESC);
        final String price = entity.getField(FangItemFields.PRICE);
        final String thumb = entity.getField(FangItemFields.THUMB);
        // 取出所有的控件
        final AppCompatTextView tvTitle = holder.getView(R.id.tv_item_title);
        final AppCompatTextView tvFloor = holder.getView(R.id.tv_item_floor);
        final AppCompatTextView tvArea = holder.getView(R.id.tv_item_area);
        final AppCompatTextView tvXiaoqu = holder.getView(R.id.tv_item_xiaoqu);
        final AppCompatTextView tvDesc = holder.getView(R.id.tv_item_desc);
        final AppCompatTextView tvPrice = holder.getView(R.id.tv_item_price);
        final AppCompatImageView imgThumb = holder.getView(R.id.img_item_thumb);
        // 赋值
        tvTitle.setText(title);
        tvFloor.setText(floor + "层");
        tvArea.setText(area + "㎡");
        tvXiaoqu.setText(xiaoqu);
        tvDesc.setText(desc);
        tvPrice.setText(price + "元");
        ImageUtils.loadImage(mContext, thumb, imgThumb);
    }

}
