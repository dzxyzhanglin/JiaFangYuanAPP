package com.songsong.jiafangyuan.app;

import android.content.Context;
import android.os.Handler;

/**
 * Created by zhanglin on 2017/9/23.
 */

public class Latte {
    public static Config init(Context context) {
        Config.newInstance()
                .getLatteConfigs()
                .put(ConfigKeys.APPLICATION_CONTEXT, context.getApplicationContext());
        return Config.newInstance();
    }


    public static Config getConfigurator() {
        return Config.newInstance();
    }

    public static <T> T getConfiguration(Object key) {
        return getConfigurator().getConfig(key);
    }

    public static Context getApplicationContext() {
        return getConfiguration(ConfigKeys.APPLICATION_CONTEXT);
    }

    public static Handler getHandler() {
        return getConfiguration(ConfigKeys.HANDLER);
    }
}
