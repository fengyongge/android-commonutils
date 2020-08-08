package com.zzti.fengyongge.androiddevtool;

import android.app.Application;

public class AndroidUtilsApp extends Application {

    private AndroidUtilsApp(){

    }

    public static AndroidUtilsApp getInstance() {
        return AndroidUtilsApp.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {

        private static final AndroidUtilsApp INSTANCE = new AndroidUtilsApp();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
