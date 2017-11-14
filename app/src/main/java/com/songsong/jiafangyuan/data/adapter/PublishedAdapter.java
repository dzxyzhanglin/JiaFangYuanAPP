package com.songsong.jiafangyuan.data.adapter;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.songsong.jiafangyuan.R;
import com.songsong.jiafangyuan.data.fields.PublishedItemFields;
import com.songsong.jiafangyuan.ui.recycler.MultipleItemEntity;
import com.songsong.jiafangyuan.ui.recycler.MultipleRecyclerAdapter;
import com.songsong.jiafangyuan.ui.recycler.MultipleViewHolder;
import com.songsong.jiafangyuan.utils.image.ImageUtils;

import java.util.List;

/**
 * Created by zhanglin on 2017/10/3.
 */

public class PublishedAdapter extends MultipleRecyclerAdapter {
    public PublishedAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(R.layout.item_published);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        final AppCompatTextView mType = holder.getView(R.id.tv_published_type);
        final AppCompatTextView mTitle = holder.getView(R.id.tv_published_title);
        final AppCompatImageView mThumb = holder.getView(R.id.tv_published_thumb);

        final String type = entity.getField(PublishedItemFields.TYPE);
        final String title = entity.getField(PublishedItemFields.TITLE);
        final String thumb = entity.getField(PublishedItemFields.THUMB);

        mType.setText(type);
        mTitle.setText(title);
        ImageUtils.loadImage(mContext, thumb, mThumb);
    }
}
