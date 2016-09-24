package com.sss.projectx.Sensors;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

/**
 * Created by Control & Inst. LAB on 24-Sep-16.
 */
public class startPower extends Activity {
    String strState;

    public startPower() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(broadcastReceiver, filter);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //context.unregisterReceiver(this);
            int chargeState = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

            switch (chargeState) {
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    strState = "charging";
                    break;
                default:
                    strState = "not charging";
            }
        }
    };

    public String CheckPower() {
        return strState;
    }
}
