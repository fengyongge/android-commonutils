package com.zzti.fengyongge.androiddevtool.broadcast;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class BroadcastUtils {

    public static final String ACTION = "com.diankai.sdc.broadcast";
    public static final int REFRESH_ADDSTAFF= 100;//刷新



    public static Intent createIntent(int type){
        Intent intent = new Intent(ACTION);
        intent.putExtra("type", type);
        return intent;
    }


}
