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
 * Created by zhanglin on 2017/9/24.
 */

public class FangDataConverter extends DataConverter {

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("data");
        final ArrayList<MultipleItemEntity> dataList = convert(dataArray);

        return dataList;
    }

    public ArrayList<MultipleItemEntity> convert(JSONArray dataArray) {
        final ArrayList<MultipleItemEntity> dataList = new ArrayList<>();
        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final int id = data.getInteger("id");
            final String title = data.getString("title");
            final String floor = data.getString("floor");
            final String area = data.getString("area");
            final String xiaoqu = data.getString("xiaoqu");
            final String desc = data.getString("desc");
            final String price = data.getString("price");
            final String thumb = data.getString("thumb");

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, ItemType.NORMAL)
                    .setField(MultipleFields.ID, id)
                    .setField(FangItemFields.TITLE, title)
                    .setField(FangItemFields.FLOOR, floor)
                    .setField(FangItemFields.AREA, area)
                    .setField(FangItemFields.XIAOQU, xiaoqu)
                    .setField(FangItemFields.DESC, desc)
                    .setField(FangItemFields.PRICE, price)
                    .setField(FangItemFields.THUMB, thumb)
                    .build();
            dataList.add(entity);
        }

        return dataList;
    }
}
