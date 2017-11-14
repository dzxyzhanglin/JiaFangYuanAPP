package com.songsong.jiafangyuan.fragment.sign;

import com.songsong.jiafangyuan.app.AccountManager;
import com.songsong.jiafangyuan.listener.ISignListener;
import com.songsong.jiafangyuan.utils.storage.LattePreference;

/**
 * Created by zhanglin on 2017/9/20.
 */

public class SignHandler {
    public static void onLoginSuccess(String response, ISignListener listener) {
        AccountManager.setSignState(true);
        LattePreference.setAppProfile(response);

        listener.onLoginSuccess();
    }

    public static void onLoginFailure(String msg, ISignListener listener) {
        listener.onLoginFailure(msg);
    }

    public static void onLogout(ISignListener listener) {
        AccountManager.setSignState(false);
        listener.onLogout();
    }
}
