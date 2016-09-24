package com.sss.projectx.Receivers;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.sss.projectx.Others.UserBase;

/**
 * Created by Control & Inst. LAB on 24-Sep-16.
 */
public class UnlockReceiver extends BroadcastReceiver {

    UserBase userBase;

    @Override
    public void onReceive(Context context, Intent intent) {
        userBase = new UserBase(context);
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        if (keyguardManager.isKeyguardSecure()) {
            //phone was unlocked, do stuff here
            userBase.setUnlock("unlocked");
        }
    }
}
