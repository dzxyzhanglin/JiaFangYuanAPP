package com.songsong.jiafangyuan.data.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.songsong.jiafangyuan.data.fields.FuWuItemFields;
import com.songsong.jiafangyuan.ui.recycler.DataConverter;
import com.songsong.jiafangyuan.ui.recycler.ItemType;
import com.songsong.jiafangyuan.ui.recycler.MultipleFields;
import com.songsong.jiafangyuan.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * Created by zhanglin on 2017/9/15.
 */

public class FuWuDataConverter extends DataConverter {

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final ArrayList<MultipleItemEntity> dataList = new ArrayList<>();
        final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("data");

        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final int id = data.getInteger("id");
            final String name = data.getString("name");
            final String phone = data.getString("phone");
            final String project = data.getString("project");
            final String address = data.getString("address");
            final String photo = data.getString("photo");

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, ItemType.NORMAL)
                    .setField(MultipleFields.ID, id)
                    .setField(FuWuItemFields.NAME, name)
                    .setField(FuWuItemFields.PHONE, phone)
                    .setField(FuWuItemFields.PROJECT, project)
                    .setField(FuWuItemFields.ADDRESS, address)
                    .setField(FuWuItemFields.PHOTO, photo)
                    .build();
            dataList.add(entity);
        }
        return dataList;
    }
}
