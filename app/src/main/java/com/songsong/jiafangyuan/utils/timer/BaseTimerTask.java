package com.songsong.jiafangyuan.utils.timer;

import java.util.TimerTask;

/**
 * Created by zhanglin on 2017/8/22.
 */

public class BaseTimerTask extends TimerTask {
    private ITimerListener mITimerListener = null;

    public BaseTimerTask(ITimerListener timerListener) {
        this.mITimerListener = timerListener;
    }

    @Override
    public void run() {
        if (mITimerListener != null) {
            mITimerListener.onTimer();
        }
    }
}
