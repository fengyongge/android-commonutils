
package com.fengyongge.androidcommonutils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * describe
 * 时间转换格式
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2019/6/1
 */
public final class DateUtil {

    public static final String YYYYMMDD_1 = "yyyyMMdd";
    public static final String YYYYMMDD_2 = "yyyy-MM-dd";
    public static final String YYYYMMDD_3 = "yyyy/MM/dd";
    public static final String YYYYMMDD_4 = "yyyy.MM.dd";
    public static final String YYYY_MM_DD_HH_MM_SS_1= "yyyy-MM-dd_HH_mm_ss";
    public static final String YYYY_MM_DD_HH_MM_SS_2 = "yyyy-MM-dd HH:mm:ss";



    /**
     * 时间戳转相应格式的字符串
     * @param timeLongVal
     * @param descFormat
     * @return
     */
    public static String formatDateLongToString(Long timeLongVal, String descFormat) {
        Date date = new Date(timeLongVal);
        SimpleDateFormat df = new SimpleDateFormat(descFormat);
        return df.format(date);
    }


    /**
     * 字符串转为时间戳
     * @param user_time
     * @return
     */
    public static String convertStringToDate(String user_time, String descFormat) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat(descFormat);
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);
        }catch (ParseException e) {
        }
        return re_time;
    }





}
