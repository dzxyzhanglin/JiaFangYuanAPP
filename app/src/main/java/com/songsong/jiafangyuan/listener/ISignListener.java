package com.songsong.jiafangyuan.listener;

/**
 * Created by zhanglin on 2017/9/20.
 */

public interface ISignListener {
    void onLoginSuccess();

    void onLoginFailure(String msg);

    void onLogout();
}
