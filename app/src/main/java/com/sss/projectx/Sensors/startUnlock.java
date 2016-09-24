package com.sss.projectx.Sensors;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by Control & Inst. LAB on 24-Sep-16.
 */
public class startUnlock extends Activity {

    public String strState = "";

    public startUnlock() {
    }

    public void CheckPower() {
        BroadcastReceiver receiverME = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                KeyguardManager keyguardManager = (KeyguardManager)context.getSystemService(Context.KEYGUARD_SERVICE);
                if (keyguardManager.isKeyguardSecure()) {
                    //phone was unlocked, do stuff here
                    strState = "unlocked";

                }
            }
        };
        IntentFilter filter = new IntentFilter(Intent.ACTION_USER_PRESENT);
        registerReceiver(receiverME, filter);
    }
}
