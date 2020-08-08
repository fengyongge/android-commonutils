package com.zzti.fengyongge.androiddevtool.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.telephony.TelephonyManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * describe
 * 手机参数相关
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/8/8
 */
public class DeviceUtils {

    /**
     * imei
     */
    public static final int IMEI = 1;

    /**
     * imsi
     */
    public static final int IMSI = 2;
    /**
     * 手机型号
     */
    public static final int MODEL = 3;
    /**
     * 手机号码
     */
    public static final int PHONENO = 4;


    private DeviceUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");

    }


    /**
     * 获取版本号(内部识别号)
     */
    public static int getVersionCode(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
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



    /**
     * 获取手机的一些信息
     *
     * @param context 上下文
     * @param type    imei imsi model phoneno
     * @return
     */
    public static String getPhoneInfo(Context context, int type) {
        TelephonyManager mTm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        switch (type) {
            case IMEI:
                return mTm.getDeviceId();
            case IMSI:
                return mTm.getSubscriberId();
            case MODEL:
                return android.os.Build.MODEL;
            case PHONENO:
                return mTm.getLine1Number();
            default:
                return "";
        }
    }

    /**
     * 获取手机cpu信息
     */
    public static String[] getCpuInfo() {
        String str1 = "/proc/cpuinfo";
        String str2 = "";
        String[] cpuInfo = {"", ""}; // 1-cpu型号 //2-cpu频率
        String[] arrayOfString;
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            for (int i = 2; i < arrayOfString.length; i++) {
                cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
            }
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            cpuInfo[1] += arrayOfString[2];
            localBufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cpuInfo;
    }


    /**
     * * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) * *
     *
     * @param context
     */
    public static void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }

    /**
     * * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases) * *
     *
     * @param context
     */
    public static void cleanDatabases(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/databases"));
    }

    /**
     * * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs) *
     *
     * @param context
     */
    public static void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/shared_prefs"));
    }

    /**
     * * 按名字清除本应用数据库 * *
     *
     * @param context
     * @param dbName
     */
    public static void cleanDatabaseByName(Context context, String dbName) {
        context.deleteDatabase(dbName);
    }

    /**
     * * 清除/data/data/com.xxx.xxx/files下的内容 * *
     *
     * @param context
     */
    public static void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }

    /**
     * * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
     *
     * @param context
     */
    public static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(context.getExternalCacheDir());
        }
    }
    /**
     * * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除 * *
     *
     * @param filePath
     * */
    public static void cleanCustomCache(String filePath) {
        deleteFilesByDirectory(new File(filePath));
    }

    /**
     * * 清除本应用所有的数据 * *
     *
     * @param context
     * @param filepath
     */
    public static void cleanApplicationData(Context context, String... filepath) {
        cleanInternalCache(context);
        cleanExternalCache(context);
        cleanDatabases(context);
        cleanSharedPreference(context);
        cleanFiles(context);
        if (filepath == null) {
            return;
        }
        for (String filePath : filepath) {
            cleanCustomCache(filePath);
        }
    }

    /**
     * * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * *
     *
     * @param directory
     */
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }

    /**
     *
     * //Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
     * //Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
     * @param file
     * @return
     * @throws Exception
     */
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }


    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }


    public static String getCacheSize(File file) throws Exception {
        return getFormatSize(getFolderSize(file));
    }


}
