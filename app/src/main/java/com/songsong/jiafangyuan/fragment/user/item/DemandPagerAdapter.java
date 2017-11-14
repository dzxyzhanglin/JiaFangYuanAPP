package com.songsong.jiafangyuan.fragment.user.item;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.songsong.jiafangyuan.fragment.loupan.LoupanPublishedFragment;
import com.songsong.jiafangyuan.fragment.maifang.MaiFangPublishedFragment;
import com.songsong.jiafangyuan.fragment.zufang.ZuFangPublishedFragment;

import java.util.Arrays;
import java.util.List;

/**
 * Created by zhanglin on 2017/10/1.
 */

public class DemandPagerAdapter extends FragmentPagerAdapter {
    private final List<String> TAB_TITLES =
            Arrays.asList(new String[]{"我的卖房", "我的楼盘", "我的出租"});
    private static final String ARG_MAIFANG = "maifang";
    private static final String ARG_LOUPAN = "loupan";
    private static final String ARG_CHUZU = "chuzu";

    private final JSONObject mData;

    public DemandPagerAdapter(FragmentManager fm, String data) {
        super(fm);
        this.mData = JSON.parseObject(data).getJSONObject("data");
    }


    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return MaiFangPublishedFragment.create(mData.getJSONArray(ARG_MAIFANG).toString());
        } else if (position == 1) {
            return LoupanPublishedFragment.create(mData.getJSONArray(ARG_LOUPAN).toString());
        } else if (position == 2) {
            return ZuFangPublishedFragment.create(mData.getJSONArray(ARG_CHUZU).toString());
        }
        return null;
    }

    @Override
    public int getCount() {
        return TAB_TITLES.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
