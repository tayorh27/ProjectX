package com.sss.projectx.Sensors;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

/**
 * Created by Control & Inst. LAB on 24-Sep-16.
 */
public class startPower extends Activity {
    Context context;

    public startPower(Context context) {
        this.context = context;
    }

    public String CheckPower() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, filter);

        int chargeState = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        String strState;

        switch (chargeState) {
            case BatteryManager.BATTERY_STATUS_CHARGING:
                strState = "charging";
                break;
            default:
                strState = "not charging";
        }
        return strState;
    }
}
