package com.songsong.jiafangyuan.utils.autoupdate;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.songsong.jiafangyuan.app.ConfigKeys;
import com.songsong.jiafangyuan.app.Latte;
import com.songsong.jiafangyuan.utils.log.LatteLogger;
import com.songsong.jiafangyuan.utils.net.RestClient;
import com.songsong.jiafangyuan.utils.net.callback.ISuccess;

/**
 * Created by zhanglin on 2017/10/3.
 */

public class UpdateUtils {
    private static Activity mActivity;
    private static UpdateUtils updateUtils = null;

    private int localVersionCode = 0;
    private String localvVersionName = "";

    public static UpdateUtils create(Activity activity) {
        mActivity = activity;
        if (updateUtils != null) {
            return updateUtils;
        }
        return new UpdateUtils();
    }

    public void checkVersion() {
        initLocalVersion();

        RestClient.builder()
                .url("api/check_version.php")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        JSONObject obj = JSON.parseObject(response);
                        final int versionCode = obj.getIntValue("versionCode");
                        final String versionName = obj.getString("versionName");

                        if (localVersionCode < versionCode) {
                            // 显示更新dislog
                            showUpdateDialog(versionCode, versionName);
                        }
                    }
                })
                .build()
                .get();
    }

    private void showUpdateDialog(final int versionCode, final String versionName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("更新提示");
        builder.setMessage("您有新的版本下载更新。");
        builder.setNegativeButton("稍后更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String apkName = "fang_android_" + versionName + "_" + versionCode + "_release.apk";
                Intent intent = new Intent(mActivity, UpdateService.class);
                intent.putExtra("apkUrl", Latte.getConfiguration(ConfigKeys.API_HOST) + "api/" + apkName);
                intent.putExtra("apkName", apkName);
                mActivity.startService(intent);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void initLocalVersion() {
        try {
            PackageInfo pi = mActivity.getPackageManager().getPackageInfo(mActivity.getPackageName(), 0);
            localVersionCode = pi.versionCode;
            localvVersionName = pi.versionName;
            LatteLogger.d("当前版本：" + localVersionCode);
        } catch (PackageManager.NameNotFoundException e) {
            LatteLogger.d("获取版本号失败");
        }
    }
}
