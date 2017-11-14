package com.songsong.jiafangyuan.utils.autoupdate;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.NotificationCompat;

import com.songsong.jiafangyuan.R;
import com.songsong.jiafangyuan.app.Contstants;
import com.songsong.jiafangyuan.utils.file.FileUtil;
import com.songsong.jiafangyuan.utils.log.LatteLogger;
import com.songsong.jiafangyuan.utils.net.RestClient;
import com.songsong.jiafangyuan.utils.net.callback.IError;
import com.songsong.jiafangyuan.utils.net.callback.IFailure;
import com.songsong.jiafangyuan.utils.net.callback.ISuccess;

import java.io.File;

/**
 * Created by zhanglin on 2017/9/18.
 */

public class UpdateService extends Service {
    private String apkUrl;
    private String apkName;
    private String filePath;
    private NotificationManager notificationManager;
    private Notification mNotification;

    @Override
    public void onCreate() {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            notifyUser(getString(R.string.update_download_failed), 0);
            stopSelf();
        }
        apkUrl = intent.getStringExtra("apkUrl");
        apkName = intent.getStringExtra("apkName");
        filePath = Environment.getExternalStorageDirectory().getPath()
                    + "/" + Contstants.sdCardDir + "/" + apkName;
        LatteLogger.d("filePath =" + filePath);

        notifyUser(getString(R.string.update_download_start), 0);
        startDownload();

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void notifyUser(String doing, int progress) {
        String title = getString(R.string.app_name) + "新版下载";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentTitle(title);

        if (progress > 0 && progress < 100) {
            builder.setContentText(doing).setProgress(0, 0, true);
        } else if (progress == 100) {
            builder.setContentText(doing).setProgress(0, 0, false);
        } else {
            builder.setContentText(doing).setProgress(0, 0, false);
        }

        builder.setAutoCancel(true);
        builder.setWhen(System.currentTimeMillis());
        builder.setTicker(title);
        builder.setContentIntent(progress == 100 ? getContentIntent(progress) :
            PendingIntent.getActivity(this, 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT));

        mNotification = builder.build();
        notificationManager.notify(0, mNotification);
    }

    private PendingIntent getContentIntent(int progress) {
        File apkFile = new File(filePath);
        final Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setAction(Intent.ACTION_INSTALL_PACKAGE);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            intent.setAction(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        intent.setDataAndType(FileUtil.getUri(apkFile),
                "application/vnd.android.package-archive");

        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }

    private void startDownload() {
        notifyUser("正在下载中...", 1);

        RestClient.builder()
                .url(apkUrl)
                .dir(Contstants.sdCardDir)
                .name(apkName)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        notifyUser(getString(R.string.update_download_finish),100);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        notifyUser(getString(R.string.update_download_failed), 0);
                        stopSelf();
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        notifyUser(getString(R.string.update_download_failed), 0);
                        stopSelf();
                    }
                })
                .build()
                .download();
    }
}
