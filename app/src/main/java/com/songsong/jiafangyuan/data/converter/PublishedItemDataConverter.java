package com.songsong.jiafangyuan.data.converter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.songsong.jiafangyuan.data.fields.PublishedItemFields;
import com.songsong.jiafangyuan.ui.recycler.ItemType;
import com.songsong.jiafangyuan.ui.recycler.MultipleFields;
import com.songsong.jiafangyuan.ui.recycler.MultipleItemEntity;
import com.songsong.jiafangyuan.utils.log.LatteLogger;

import java.util.ArrayList;

/**
 * Created by zhanglin on 2017/10/3.
 */

public class PublishedItemDataConverter {

    public ArrayList<MultipleItemEntity> convert(JSONArray data) {
        final ArrayList<MultipleItemEntity> dataList = new ArrayList<>();
        if (data != null && data.size() > 0) {
            final int size = data.size();
            for (int i = 0; i < size; i++) {
                final JSONObject item = data.getJSONObject(i);
                final int id = item.getInteger("id");
                final String type = item.getString("type");
                final String title = item.getString("title");
                final String thumb = item.getString("thumb");

                MultipleItemEntity entity = MultipleItemEntity.builder()
                        .setField(MultipleFields.ITEM_TYPE, ItemType.NORMAL)
                        .setField(MultipleFields.ID, id)
                        .setField(PublishedItemFields.TYPE, type)
                        .setField(PublishedItemFields.TITLE, title)
                        .setField(PublishedItemFields.THUMB, thumb)
                        .build();
                dataList.add(entity);
            }
        }
        return dataList;
    }
}
