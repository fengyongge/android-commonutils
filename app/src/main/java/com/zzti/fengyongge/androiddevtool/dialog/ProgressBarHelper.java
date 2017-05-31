package com.zzti.fengyongge.androiddevtool.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.zzti.fengyongge.androiddevtool.R;

public class ProgressBarHelper {

	public static Dialog createWindowsBar(Activity ctx) {

        Dialog dialog = new Dialog(ctx, R.style.DialogStyle);
        dialog.setContentView(R.layout.popup_window);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = 0f;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
	}
	
	

}
