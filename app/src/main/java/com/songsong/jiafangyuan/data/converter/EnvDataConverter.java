package com.songsong.jiafangyuan.data.converter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.songsong.jiafangyuan.data.fields.EnvItemFields;
import com.songsong.jiafangyuan.ui.recycler.ItemType;
import com.songsong.jiafangyuan.ui.recycler.MultipleFields;
import com.songsong.jiafangyuan.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * Created by zhanglin on 2017/9/27.
 */

public class EnvDataConverter {
    public ArrayList<MultipleItemEntity> convert(JSONObject obj) {
        ArrayList<MultipleItemEntity> dataList = new ArrayList<>();
        final JSONArray dataArray = obj.getJSONArray("envs");
        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, ItemType.NORMAL)
                    .setField(EnvItemFields.ICON, data.getString("icon"))
                    .setField(EnvItemFields.NAME, data.getString("name"))
                    .build();
            dataList.add(entity);
        }
        return dataList;
    }
}
