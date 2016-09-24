package com.sss.projectx.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import com.sss.projectx.Others.UserBase;

/**
 * Created by Control & Inst. LAB on 24-Sep-16.
 */
public class PowerReceiver extends BroadcastReceiver {

    UserBase userBase;

    @Override
    public void onReceive(Context context, Intent intent) {
        userBase = new UserBase(context);
        int chargeState = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        switch (chargeState) {
            case BatteryManager.BATTERY_STATUS_CHARGING:
                userBase.setPower("charging");
                break;
            default:
                userBase.setPower("not charging");
        }
    }
}
