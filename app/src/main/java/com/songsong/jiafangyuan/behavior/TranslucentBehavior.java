package com.songsong.jiafangyuan.behavior;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.songsong.jiafangyuan.R;
import com.songsong.jiafangyuan.ui.recycler.RgbValue;
import com.songsong.jiafangyuan.utils.log.LatteLogger;

/**
 * Created by zhanglin on 2017/9/27.
 */

public class TranslucentBehavior extends CoordinatorLayout.Behavior<Toolbar> {

    //顶部距离
    private int mDistanceY = 0;
    //颜色变化速度
    private static final int SHOW_SPEED = 3;
    //定义变化的颜色
    private final RgbValue RGB_VALUE = RgbValue.create(0, 141, 255);

    public TranslucentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, Toolbar child, View dependency) {
        return dependency.getId() == R.id.zufang_detail;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, Toolbar child, View directTargetChild, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, Toolbar child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        //增加滑动距离
        if (mDistanceY >= 0) {
            mDistanceY += dy;
        } else {
            mDistanceY = 0;
        }
        //toolbar的高度
        final int targetHeight = child.getHeight();

        // 修正 mDistanceY
        if (mDistanceY < 0)
            mDistanceY = 0;

        //当滑动时，并且距离小于 toolbar 高度的时候，调整渐变色
        if (mDistanceY >= 0 && mDistanceY <= targetHeight) {
            final float scale = (float) mDistanceY / targetHeight;
            final float alpha = scale * 255;
            child.setBackgroundColor(Color.argb((int) alpha, RGB_VALUE.red(), RGB_VALUE.green(), RGB_VALUE.blue()));
        } else if (mDistanceY > targetHeight) {
            child.setBackgroundColor(Color.rgb(RGB_VALUE.red(), RGB_VALUE.green(), RGB_VALUE.blue()));
        }
    }
}
