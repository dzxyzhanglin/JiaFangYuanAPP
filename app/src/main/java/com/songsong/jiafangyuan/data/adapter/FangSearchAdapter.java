package com.songsong.jiafangyuan.data.adapter;

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import com.songsong.jiafangyuan.R;
import com.songsong.jiafangyuan.data.fields.FangSearchFields;
import com.songsong.jiafangyuan.data.type.FangSearchType;
import com.songsong.jiafangyuan.ui.recycler.MultipleItemEntity;
import com.songsong.jiafangyuan.ui.recycler.MultipleRecyclerAdapter;
import com.songsong.jiafangyuan.ui.recycler.MultipleViewHolder;

import java.util.List;

/**
 * Created by zhanglin on 2017/9/28.
 */

public class FangSearchAdapter extends MultipleRecyclerAdapter {

    public FangSearchAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(FangSearchType.HEADER, R.layout.item_search_header);
        addItemType(FangSearchType.BUTTON, R.layout.item_search_content);
        addItemType(FangSearchType.EDIT, R.layout.item_search_key);
        // 设置宽度监听
        setSpanSizeLookup(this);
        openLoadAnimation();
        // 多次执行动画
        isFirstOnly(false);
    }

    @Override
    protected void convert(MultipleViewHolder holder, final MultipleItemEntity entity) {
        final String mCateName;
        final String mName;
        switch (holder.getItemViewType()) {
            case FangSearchType.HEADER:
                mCateName = entity.getField(FangSearchFields.CATE_NAME);
                holder.setText(R.id.search_tv_header, mCateName);
                break;
            case FangSearchType.BUTTON:
                final AppCompatButton mButton = holder.getView(R.id.search_btn_name);
                mName = entity.getField(FangSearchFields.NAME);
                mButton.setText(mName);

                mButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean selected = view.isSelected();
                        mButton.setSelected(!selected);
                    }
                });
                break;
            case FangSearchType.EDIT:
                final AppCompatEditText mEdit = holder.getView(R.id.search_key);
                mName = entity.getField(FangSearchFields.NAME);
                mEdit.setHint("输入关键词，如如附近公交、轻轨、学校");
                break;
            default:
        }
    }
}
