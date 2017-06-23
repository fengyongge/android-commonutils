package com.zzti.fengyongge.androiddevtool.app;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
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

        //初始化
        LogUtils.init(AppConfig.DEBUG);



        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
//				.showImageOnFail(R.drawable.test)
//				.showImageOnFail(R.drawable.test)
                .cacheInMemory(true).cacheOnDisc(true).build();

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


}

