package com.zzti.fengyongge.androiddevtool.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.zzti.fengyongge.androiddevtool.R;
import com.zzti.fengyongge.androiddevtool.broadcast.BroadcastUtils;

public class BroadcastSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_sample);
        initReceiver();

    }


    private void initReceiver() {
        IntentFilter intentFilter = new IntentFilter("com.diankai.sdc.broadcast");
        getApplicationContext().registerReceiver(bReceiver, intentFilter);
    }

    private BroadcastReceiver bReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int t = intent.getIntExtra("type", BroadcastUtils.REFRESH_ADDSTAFF);
            if (t == BroadcastUtils.REFRESH_ADDSTAFF) {

            }
        }
    };



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bReceiver != null) {
            try {
                getApplicationContext().unregisterReceiver(bReceiver);
            } catch (IllegalArgumentException e) {

                if (e.getMessage().contains("Receiver not registered")) {
                    // Ignore this exception. This is exactly what is desired
                } else {
                    // unexpected, re-throw
                    throw e;
                }
            }
        }
    }
}
