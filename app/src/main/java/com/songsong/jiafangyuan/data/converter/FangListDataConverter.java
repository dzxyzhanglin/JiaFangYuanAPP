package com.songsong.jiafangyuan.data.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.songsong.jiafangyuan.data.fields.FangItemFields;
import com.songsong.jiafangyuan.ui.recycler.DataConverter;
import com.songsong.jiafangyuan.ui.recycler.ItemType;
import com.songsong.jiafangyuan.ui.recycler.MultipleFields;
import com.songsong.jiafangyuan.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * Created by zhanglin on 2017/9/3.
 */

public class FangListDataConverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final ArrayList<MultipleItemEntity> dataList = new ArrayList<>();
        final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("data");

        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final int id = data.getInteger("id");
            final String title = data.getString("title");
            final int type = data.getInteger("type");
            final float price = data.getFloat("price");
            final String thumb = data.getString("thumb");

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, ItemType.NORMAL)
                    .setField(MultipleFields.ID, id)
                    .setField(MultipleFields.TITLE, title)
                    .setField(FangItemFields.TYPE, type)
                    .setField(FangItemFields.PRICE, price)
                    .setField(FangItemFields.THUMB, thumb)
                    .build();
            dataList.add(entity);
        }
        return dataList;
    }
}
