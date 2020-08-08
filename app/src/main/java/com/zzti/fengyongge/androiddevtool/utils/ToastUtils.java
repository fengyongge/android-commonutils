package com.zzti.fengyongge.androiddevtool.utils;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * @author fengyongge
 * @Description  toast
 */

public class ToastUtils {

	public static Toast toast;

	public static void showToast(Context context, String content){
		if(toast==null){
			toast = Toast.makeText(context,content, Toast.LENGTH_SHORT);
		}else{
			toast.setText(content);
		}
		toast.show();
	}


}