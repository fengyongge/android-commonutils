package com.fengyongge.androidcommonutils;


/**
 * describe
 * 校验正确性
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2019/6/1
 */
public final class ValidUtils {
    /**
     * 是否为Email
     */
    public static boolean isValidEmail(String paramString) {
        String regex = "[a-zA-Z0-9_\\.]{1,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}";
        if (paramString.matches(regex)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 是否为手机号
     */
    public static boolean isValidMobiNumber(String paramString) {
        String regex = "^1\\d{10}$";
        if (paramString.matches(regex)) {
            return true;
        }
        return false;
    }


}
