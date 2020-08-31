package com.fengyongge.androidcommonutils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

import com.zzti.fengyongge.androiddevtool.AndroidUtilsApp;

/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/8/8
 */
public class SizeUtils {

    /**
     * 获取Resource对象
     */
    public static Resources getResources() {
        return AndroidUtilsApp.getInstance().getResources();
    }


    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }


}
