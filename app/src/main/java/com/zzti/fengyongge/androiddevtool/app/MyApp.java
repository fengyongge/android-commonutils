package com.zzti.fengyongge.androiddevtool.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.SyncStateContract;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.zzti.fengyongge.androiddevtool.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fengyongge
 * @Description
 */
public class MyApp extends Application {

    private static List<Activity> con_list = new ArrayList<Activity>();

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

        LogUtils.init(AppConfig.DEBUG);
        initImageLoad();

//        String processName = getProcessName(this, android.os.Process.myPid());//防止多进程时多次启动Application的onCreate造成多次初始化
//        if (processName != null) {
//            boolean defaultProcess = processName.equals("com.zzti.fengyongge.androiddevtool");
//            // 默认的主进程启动时初始化应用
//            if (defaultProcess) {
//                //初始化
//                LogUtils.init(AppConfig.DEBUG);
//                initImageLoad();
//                Logger.i("22222");
//            }
//            // 其他进程启动时初始化对应内容
//            else if (processName.contains(":webbrowser")) {
//
//            } else if (processName.contains(":wallet")) {
//
//            }
//        }

    }


    /**
     * 初始化imageload
     */
    public void initImageLoad(){

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.drawable.ic_stub) //取消配置防止oom
//                .showImageOnFail(R.drawable.ic_error)
                .cacheInMemory(true)
                .cacheOnDisc(true)
//                .cacheOnDisk(true)
//                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions).build();

        ImageLoader.getInstance().init(config);
    }


    public static void addActivity(Activity activity) {
        con_list.add(activity);
    }

    public static void killActivity() {

        for (int i = 0; i < con_list.size(); i++) {
            con_list.get(i).finish();
        }
    }



    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }


}

