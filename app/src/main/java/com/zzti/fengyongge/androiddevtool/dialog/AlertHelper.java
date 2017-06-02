package com.zzti.fengyongge.androiddevtool.dialog;

import android.content.Context;
import android.view.View;
import com.zzti.fengyongge.androiddevtool.myinterface.AlertCallback;

/**
 * @author fengyongge
 * @Description 自定义对话框
 */
public class AlertHelper {

    public static void scanDialog(Context context, String message, final AlertCallback alertCallback) {

        new AlertDialog(context).builder()
                .setMsg(message)
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        alertCallback.onConfirm();
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertCallback.onCancel();
            }
        }).show();
    }

}
