package com.songsong.jiafangyuan.data.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.songsong.jiafangyuan.data.fields.FangSearchFields;
import com.songsong.jiafangyuan.data.type.FangSearchType;
import com.songsong.jiafangyuan.ui.recycler.DataConverter;
import com.songsong.jiafangyuan.ui.recycler.MultipleFields;
import com.songsong.jiafangyuan.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * Created by zhanglin on 2017/8/30.
 */

public class FangSearchDataConverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        ArrayList<MultipleItemEntity> dataList = new ArrayList<>();

        final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("data");
        final int size = dataArray.size();
        for (int i  = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);

            final int mId = data.getInteger("id");
            final String mCateName = data.getString("cateName");
            final int mCateId = data.getInteger("cateId");
            final String mName = data.getString("name");

            int type = 0;
            int spanSize = 0;
            if (mCateName != null) {
                type = FangSearchType.HEADER;
                spanSize = 4;
            } else if (mCateName == null && mCateId == 0) {
                type = FangSearchType.EDIT;
                spanSize = 4;
            } else {
                type = FangSearchType.BUTTON;
                spanSize = 1;
            }

            MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, type)
                    .setField(MultipleFields.SPAN_SIZE, spanSize)
                    .setField(MultipleFields.ID, mId)
                    .setField(FangSearchFields.CATE_NAME, mCateName)
                    .setField(FangSearchFields.CATE_ID, mCateId)
                    .setField(FangSearchFields.NAME, mName)
                    .build();
            dataList.add(entity);
        }

        return dataList;
    }
}
