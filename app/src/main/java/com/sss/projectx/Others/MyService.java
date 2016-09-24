package com.sss.projectx.Others;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Control & Inst. LAB on 24-Sep-16.
 */
public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String actions = intent.getStringExtra("actions");
            String sensors = intent.getStringExtra("sensors");
            String delay_time = intent.getStringExtra("delay_time");
            long future = intent.getLongExtra("future", 0);
            CountDownClass countDownClass = new CountDownClass(this, actions, sensors, delay_time, future, 1000);
            countDownClass.start();
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
