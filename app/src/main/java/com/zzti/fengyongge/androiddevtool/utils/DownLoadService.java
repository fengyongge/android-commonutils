package com.zzti.fengyongge.androiddevtool.utils;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.zzti.fengyongge.androiddevtool.R;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import java.io.File;

/**
 * @author fengyongge
 * @Description
 * @date 2017/7/17
 */
public class DownLoadService extends Service {

    File file;
    private File fileName= Environment.getExternalStorageDirectory();//目标文件存储的文件夹路径
    private String destFileName = "test.apk";//目标文件存储的文件名
    private Context mContext;
    private int preProgress = 0;
    private int NOTIFY_ID = 1000;
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;
    private String download_url="";


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mContext = this;
        download_url = intent.getStringExtra("download_url");
        loadFile();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 下载文件
     */
    private void loadFile() {
        initNotification();

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            file = new File(fileName, destFileName);

        } else {
            file = new File("/data/data/com.diankai.ecosphere/temp", "ecosphere.apk");

            if (!file.exists()) {
                file.mkdirs();
            }

        }


        FinalHttp finalHttp = new FinalHttp();

        finalHttp.download(download_url, file.getAbsolutePath(), new AjaxCallBack<File>() {
            @Override
            public void onFailure(Throwable t, String strMsg) {
                super.onFailure(t, strMsg);

                cancelNotification();// 取消通知

            }

            @Override
            public void onLoading(long total, long progress) {
                super.onLoading(progress, total);

                updateNotification(progress * 100 / total);// 更新前台通知
            }

            @Override
            public void onSuccess(File t) {
                // 安装软件
                cancelNotification();
                installApk(file);
                super.onSuccess(t);
            }
        });
    }



    /**
     * 安装软件
     *
     * @param file
     */
    private void installApk(File file) {
        Uri uri = Uri.fromFile(file);
        Intent install = new Intent(Intent.ACTION_VIEW);
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        install.setDataAndType(uri, "application/vnd.android.package-archive");
        // 执行意图进行安装
        mContext.startActivity(install);
    }


    /**
     * 初始化Notification通知
     */
    public void initNotification() {
        builder = new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.drawable.test)// 设置通知的图标
                .setContentText("正在下载0%")// 进度Text
                .setContentTitle("下载"+getAppName(mContext))// 标题
                .setProgress(100, 0, false);// 设置进度条
        notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);// 获取系统通知管理器
        notificationManager.notify(NOTIFY_ID, builder.build());// 发送通知 
    }

    /**
     * 更新通知
     */
    public void updateNotification(long progress) {
        int currProgress = (int) progress;
        if (preProgress < currProgress) {
            builder.setContentText("正在下载"+progress + "%");
            builder.setProgress(100, (int) progress, false);
            notificationManager.notify(NOTIFY_ID, builder.build());
        }
        preProgress = (int) progress;

    }

    /**
     * 取消通知
     */
    public void cancelNotification() {
        notificationManager.cancel(NOTIFY_ID);
    }



    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}