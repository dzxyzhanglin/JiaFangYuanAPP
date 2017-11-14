package com.songsong.jiafangyuan.data.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.songsong.jiafangyuan.data.fields.HuxingItemFields;
import com.songsong.jiafangyuan.ui.recycler.DataConverter;
import com.songsong.jiafangyuan.ui.recycler.ItemType;
import com.songsong.jiafangyuan.ui.recycler.MultipleFields;
import com.songsong.jiafangyuan.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * Created by zhanglin on 2017/9/15.
 */

public class HuxingDataConverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final ArrayList<MultipleItemEntity> dataList = new ArrayList<>();
        final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("huxing");

        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final String title = data.getString("title");
            final String taonei = data.getString("taonei");
            final int total = data.getInteger("total");
            final int yishou = data.getInteger("yishou");
            final String thumb = data.getString("thumb");

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, ItemType.NORMAL)
                    .setField(HuxingItemFields.TITLE, title)
                    .setField(HuxingItemFields.TAONEI, taonei)
                    .setField(HuxingItemFields.TOTAL, total)
                    .setField(HuxingItemFields.YISHOU, yishou)
                    .setField(HuxingItemFields.THUMB, thumb)
                    .build();
            dataList.add(entity);
        }
        return dataList;
    }
}
