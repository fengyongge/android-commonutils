package com.zzti.fengyongge.androiddevtool.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;


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
    android.support.v7.app.AlertDialog.Builder builder;
    private  File fileName= Environment.getExternalStorageDirectory();//目标文件存储的文件夹路径
    private String destFileName = "test.apk";//目标文件存储的文件名

    /**
     * 创建更新提醒对话框
     * @param context
     * @param message
     * @param download_url
     * @param isForceUpdate
     */
    public void showUpdateDialog(final Context context, String message, final String download_url, boolean isForceUpdate) {

        this.context=context;
        update_item = LayoutInflater.from(context).inflate(R.layout.update_item, null);
        tvMessage = (TextView) update_item.findViewById(R.id.tvMessage);
        tvConfirm = (TextView) update_item.findViewById(R.id.tvConfirm);
        tvCancle = (TextView) update_item.findViewById(R.id.tvCancle);
        tvMessage.setText(Html.fromHtml(message));
        tvMessage.setMovementMethod(ScrollingMovementMethod.getInstance());// 滚动

        downloadalert = new AlertDialog.Builder(context).setView(update_item).create();
        downloadalert.setCanceledOnTouchOutside(false);
        downloadalert.show();

        if(isForceUpdate){
            tvCancle.setVisibility(View.GONE);//不显示取消
            downloadalert.setCancelable(false);//返回键失效
        }else{
            tvCancle.setVisibility(View.VISIBLE);
        }

        tvConfirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                tvConfirm.setClickable(false);
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

                    file = new File(fileName,destFileName);

                } else {
                    file = new File("/data/data/com.diankai.ecosphere/temp", "test.apk");

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

                        Toast.makeText(context, "下载失败"+strMsg, Toast.LENGTH_SHORT).show();

                        downloadalert.dismiss();
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

                        installApk(context,file);
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
                downloadalert.dismiss();
            }
        });
    }


    /**
     * 安装软件
     *
     * @param file
     */
    private void installApk(Context context,File file) {
        Uri uri = Uri.fromFile(file);
        Intent install = new Intent(Intent.ACTION_VIEW);
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        install.setDataAndType(uri, "application/vnd.android.package-archive");
        context.startActivity(install);
    }



    /**
     * 创建更新提醒对话框
     * @param context
     * @param message
     * @param download_url
     * @param isForceUpdate
     */
    public void showMaterialUpdateDialog(final Context context, String message, final String download_url, boolean isForceUpdate) {

        builder = new android.support.v7.app.AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle("APP更新")
                .setMessage(Html.fromHtml(message))
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context, DownLoadService.class);
                        intent.putExtra("download_url",download_url);
                        context.startService(intent);
                    }
                });

        if(isForceUpdate){

        }else{
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        }
        builder.show();

    }

}


