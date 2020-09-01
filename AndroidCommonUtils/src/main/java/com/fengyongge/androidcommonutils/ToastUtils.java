package com.fengyongge.androidcommonutils;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * describe
 * toast
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2019/6/1
 */

public final class ToastUtils {

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