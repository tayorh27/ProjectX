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

    Context context;
    public String strState = "";

    public startUnlock(Context context) {
        this.context = context;
    }

    public void CheckPower() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_USER_UNLOCKED);
        registerReceiver(new receiver(), filter);
    }

    class receiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            KeyguardManager keyguardManager = (KeyguardManager)context.getSystemService(Context.KEYGUARD_SERVICE);
            if (keyguardManager.isKeyguardSecure()) {

                //phone was unlocked, do stuff here
                strState = "unlocked";

            }
        }
    }
}
