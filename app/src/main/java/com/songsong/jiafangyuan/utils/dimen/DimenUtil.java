package com.songsong.jiafangyuan.utils.dimen;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.songsong.jiafangyuan.app.Latte;


/**
 * Created by zhanglin on 2017/8/22.
 */

public class DimenUtil {
    public static int getScreenWidth() {
        final Resources resources = Latte.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight() {
        final Resources resources = Latte.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }
}
