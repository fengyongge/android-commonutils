package com.fengyongge.androidcommonutils;

import android.content.res.Resources;
import android.util.TypedValue;


/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2019/6/1
 */
public final class SizeUtils {

    private SizeUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,  Resources.getSystem().getDisplayMetrics());
    }




}
