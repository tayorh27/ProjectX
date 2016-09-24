package com.sss.projectx.Actions;

import android.app.Activity;
import android.telephony.SmsManager;

/**
 * Created by Control & Inst. LAB on 23-Sep-16.
 */
public class startSms extends Activity{

    public void Sms(String number, String text) {

        SmsManager manager = SmsManager.getDefault();
        manager.sendTextMessage(number, null, text, null, null);
    }

}
