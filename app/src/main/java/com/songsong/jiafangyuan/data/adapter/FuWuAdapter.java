package com.songsong.jiafangyuan.data.adapter;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.songsong.jiafangyuan.R;
import com.songsong.jiafangyuan.data.fields.FuWuItemFields;
import com.songsong.jiafangyuan.ui.recycler.MultipleItemEntity;
import com.songsong.jiafangyuan.ui.recycler.MultipleRecyclerAdapter;
import com.songsong.jiafangyuan.ui.recycler.MultipleViewHolder;
import com.songsong.jiafangyuan.utils.image.ImageUtils;

import java.util.List;

/**
 * Created by zhanglin on 2017/9/15.
 */

public class FuWuAdapter extends MultipleRecyclerAdapter {
    public FuWuAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(R.layout.item_fuwu);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);

        final String name = entity.getField(FuWuItemFields.NAME);
        final String phone = entity.getField(FuWuItemFields.PHONE);
        final String project = entity.getField(FuWuItemFields.PROJECT);
        final String address = entity.getField(FuWuItemFields.ADDRESS);
        final String photo = entity.getField(FuWuItemFields.PHOTO);

        AppCompatTextView tvName = holder.getView(R.id.service_item_name);
        AppCompatTextView tvPhone = holder.getView(R.id.service_item_phone);
        AppCompatTextView tvProject = holder.getView(R.id.service_item_project);
        AppCompatTextView tvAddress = holder.getView(R.id.service_item_address);
        AppCompatImageView ivPhote = holder.getView(R.id.service_item_photo);

        tvName.setText(name);
        tvPhone.setText(phone);
        tvProject.setText(project);
        tvAddress.setText(address);
        ImageUtils.loadImage(mContext, photo, ivPhote);
    }
}
