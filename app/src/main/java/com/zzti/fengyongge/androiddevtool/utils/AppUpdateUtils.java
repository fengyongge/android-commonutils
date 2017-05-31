package com.zzti.fengyongge.androiddevtool.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Process;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import com.zzti.fengyongge.androiddevtool.R;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import java.io.File;

/**
 * @author fengyongge
 * @Description
 */
public class AppUpdateUtils {

     File file;
     View update_item;
     TextView tvConfirm;
     TextView tvCancle;
     TextView tvMessage;

     AlertDialog downloadalert;
     Context context;

    /**
     * 创建更新提醒对话框
     * @param context
     * @param message
     * @param download_url
     * @param status 0 强制 1 不强制
     */
    public void showUpdateDialog(final Context context, String message, final String download_url, String status) {

        this.context=context;
        update_item = LayoutInflater.from(context).inflate(R.layout.update_item, null);
        tvMessage = (TextView) update_item.findViewById(R.id.tvMessage);
        tvConfirm = (TextView) update_item.findViewById(R.id.tvConfirm);
        tvCancle = (TextView) update_item.findViewById(R.id.tvCancle);
        tvMessage.setText(Html.fromHtml(message));
        tvMessage.setMovementMethod(ScrollingMovementMethod.getInstance());// 滚动

        downloadalert = new AlertDialog.Builder(context).setView(update_item).create();
        downloadalert.show();


        //是否强制
        if (status.equals("0")) {
            tvCancle.setVisibility(View.GONE);//不显示取消
            downloadalert.setCancelable(false);//强制更新
        } else {
            tvCancle.setVisibility(View.VISIBLE);
        }

        tvConfirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                tvConfirm.setClickable(false);
                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {


                    // 下载新的apk 替换安装
                    file = new File(Environment.getExternalStorageDirectory(),
                            "ecosphere.apk");

                } else {

                    file = new File("/data/data/com.diankai.ecosphere/temp",
                            "ecosphere.apk");
                    if (!file.exists()) {
                        file.mkdirs();
                    }

                }
                tvMessage.setText("下载进度:0%");

                FinalHttp finalHttp = new FinalHttp();

                finalHttp.download(download_url, file.getAbsolutePath(), new AjaxCallBack<File>() {
                    @Override
                    public void onFailure(Throwable t, String strMsg) {
                        super.onFailure(t, strMsg);

                        ToastUtils.showToast(context.getApplicationContext(), "下载失败");
//                         loadMainUI();
                        downloadalert.dismiss();
                        if (t != null)
                            t.printStackTrace();
                    }

                    @Override
                    public void onLoading(long count, long current) {
                        super.onLoading(count, current);
                        int process = (int) (current * 100 / count);
                        tvMessage.setText("下载进度:" + process + "%");
                        tvMessage.setGravity(Gravity.CENTER);
                    }

                    @Override
                    public void onSuccess(File t) {

                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.setDataAndType(Uri.parse("file://" + file.toString()),
                                "application/vnd.android.package-archive");
                        context.startActivity(i);
                        Process.killProcess(Process.myPid());
                        tvConfirm.setClickable(true);
                        downloadalert.dismiss();
                        super.onSuccess(t);
                    }
                });

            }
        });
        tvCancle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
//                edit.putLong("currenttime", System.currentTimeMillis());
//                edit.commit();
                downloadalert.dismiss();
            }
        });
    }


}
