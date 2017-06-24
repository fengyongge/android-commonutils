package com.zzti.fengyongge.androiddevtool.net.api;

import android.content.Context;
import android.content.SharedPreferences;
import com.zzti.fengyongge.androiddevtool.app.AppConfig;
import com.zzti.fengyongge.androiddevtool.app.MyApp;
import com.zzti.fengyongge.androiddevtool.encrypt.EncryptionRule;
import com.zzti.fengyongge.androiddevtool.myinterface.ApiCallback;
import com.zzti.fengyongge.androiddevtool.net.NetUtils;
import com.zzti.fengyongge.androiddevtool.utils.PreferencesUtils;
import com.zzti.fengyongge.androiddevtool.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by fengyongge on 2017/5/23.
 */

public class Api {

    private static Api mInstance;
    private static SharedPreferences sp;
    private static MyApp myApp;
    private static Context cxt;
    private static String staff_id="",supplier_id="";

    public static Api Inst(Context context) {

        if (mInstance == null) {
            synchronized (NetUtils.class) {
                if (mInstance == null) {

                    mInstance = new Api();
                }
            }
        }

        cxt = context;
        staff_id= PreferencesUtils.getString(cxt,"");
        supplier_id= PreferencesUtils.getString(cxt,"");

//        if(NetUtils.isNetworkAvailable(context)){
//            ToastUtils.showToast(context,"网络异常");
//        }

         return mInstance;
    }


    /**
     * 登录接口
     * @param mobile
     * @param password
     * @param callback
     */
    public void Login(final String mobile, final String password, final ApiCallback callback) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("timestamp", getTime());
        map.put("mobile", mobile);
        map.put("password", password);
        map.put("visitSource", "1");
        String sign = EncryptionRule.encryption(AppConfig.APPSECRET, map);

        String method = "staffservice/login";
        final String apiUri = AppConfig.BASE_URL +  method;
        List<NetUtils.Param> params = new ArrayList<NetUtils.Param>();
        params =NetUtils. getParameter(map,sign);

        NetUtils.getInstance()._postAsyn(apiUri, callback, params);

    }

    /**
     * 获取标签列表
     */
    public void getLabels(final ApiCallback callback) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("timestamp", getTime());
        String sign = EncryptionRule.encryption(AppConfig.APPSECRET, map);
        String s = NetUtils.getpParameter(map,sign);

        String method = "memberservice/queryStaffTag/suppliers/" + supplier_id + "/operator/" + staff_id + "?"+s;
        final String apiUri = AppConfig.BASE_URL + method;
        NetUtils.getInstance()._getAsyn(apiUri, callback);
    }

    /**
     * 删除标签
     */
    public void deleteTag(String tag_id, final ApiCallback callback) {

        Map<String, String> map = new TreeMap<String, String>();
        map.put("timestamp", getTime());
        map.put("supplier_id", supplier_id);
        map.put("operator_id", staff_id);
        map.put("tagids", tag_id);
        String sign = EncryptionRule.encryption(AppConfig.APPSECRET, map);


        String method = "memberservice/delMemberTag/suppliers/" + supplier_id + "/operator/" + staff_id;
        final String apiUri = AppConfig.BASE_URL + method;
        List<NetUtils.Param> params = new ArrayList<NetUtils.Param>();
        params =NetUtils. getParameter(map,sign);
        NetUtils.getInstance()._deleteAsyn(apiUri, callback, params);
    }

    /**
     * 修改标签
     */
    public void updateMemberTag(final String name, String tag_id, final ApiCallback callback) {

        Map<String, String> map = new TreeMap<String, String>();
        map.put("timestamp", getTime());
        map.put("supplier_id", supplier_id);
        map.put("operator_id", staff_id);
        map.put("tagid", tag_id);
        map.put("name", name);
        String sign = EncryptionRule.encryption(AppConfig.APPSECRET, map);

        String method = "memberservice/updateMemberTag/suppliers/" + supplier_id + "/operator/" + staff_id;
        final String apiUri = AppConfig.BASE_URL + method;
        List<NetUtils.Param> params = new ArrayList<NetUtils.Param>();
        params =NetUtils. getParameter(map,sign);
        NetUtils.getInstance()._putAsyn(apiUri, callback, params);
    }


    /**
     * 添加标签
     */
    public void addMemberTag(final String name, final ApiCallback callback) {

        Map<String, String> map = new TreeMap<String, String>();
        map.put("timestamp", getTime());
        map.put("name", name);
        map.put("type", "2");
        String sign = EncryptionRule.encryption(AppConfig.APPSECRET, map);


        String method = "memberservice/addMemberTag/suppliers/" + supplier_id + "/operator/" + staff_id;
        final String apiUri = AppConfig.BASE_URL + method;
        List<NetUtils.Param> params = new ArrayList<NetUtils.Param>();
        params =NetUtils. getParameter(map,sign);
        NetUtils.getInstance()._postAsyn(apiUri, callback, params);
    }

    /**
     * 更新接口
     * @param callback
     */
    public void update(final ApiCallback callback) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("timestamp", getTime());
        map.put("type", "android");
        String sign = EncryptionRule.encryption(AppConfig.APPSECRET, map);
        String s = NetUtils.getpParameter(map,sign);

        String method = "apk?"+s;
        final String apiUri = AppConfig.BASE_URL + method;
        NetUtils.getInstance()._getAsyn(apiUri, callback);
    }



    public void meetingASssistant(int pageIndex,final ApiCallback callback) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("timestamp",getTime());
        map.put("page",pageIndex+"");
        map.put("per_page","20");
        String sign = EncryptionRule.encryption(AppConfig.APPSECRET, map);

        String s = NetUtils.getpParameter(map,sign);
        String method ="meeting/assistant?"+s;
        final String apiUri = AppConfig.BASE_URL +  method;
        NetUtils.getInstance()._getAsyn(apiUri, callback);
    }

    

    private String getTime() {
        return System.currentTimeMillis() + "";
    }
}