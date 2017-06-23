package com.zzti.fengyongge.androiddevtool.myinterface;

import com.alibaba.fastjson.JSONObject;


public interface ApiCallback {
	
	public void onDataSuccess(JSONObject data);
	public void onDataError(JSONObject data);
	public void onNetError(String data);


}