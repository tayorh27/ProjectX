package com.sss.projectx.Others;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

import com.sss.projectx.Actions.startCall;
import com.sss.projectx.Actions.startEmail;
import com.sss.projectx.Actions.startSound;
import com.sss.projectx.Sensors.*;
import com.sss.projectx.MainActivity;

/**
 * Created by Control & Inst. LAB on 23-Sep-16.
 */
public class CountDownClass extends CountDownTimer {

    String do_what, getSensor;
    UserBase userBase;
    Context context;
    Information information;
    SensorsInfo sensorsInfo;

    public CountDownClass(Context context, String do_what, String getSensor, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        try {
            this.do_what = do_what;
            this.getSensor = getSensor;
            this.context = context;
            userBase = new UserBase(context);
            information = userBase.getActionSettings();
            sensorsInfo = userBase.getSensorsSettings();
        } catch (Exception e) {
            Log.e("Error from Act..", e.toString());
        }
    }

    @Override
    public void onTick(long l) {

    }

    @Override
    public void onFinish() {
        if (getSensor.contains("light")) {
            startLight sl = new startLight(context);
            Log.e("Current Reading", "Current Reading For Light = " + sl.current);
            if (sl.current > sensorsInfo.light_intensity) {
                Actions();
            }
        }
        if (getSensor.contains("proximity")) {
            startProximity sp = new startProximity(context);
            Log.e("Current Reading", "Current Reading For Proximity = " + sp.current);
            int closeness = sensorsInfo.proximity_intensity - sp.current;
            if (closeness <= 5) {
                Actions();
            }
        }
        if(getSensor.contains("power")){
            startPower sp = new startPower(context);
            String charging = sp.CheckPower();
            if(!charging.contains("charging")){
                Actions();
            }
        }
        if(getSensor.contains("unlock")){
            startUnlock su = new startUnlock(context);
            su.CheckPower();
            if(su.strState.contentEquals("unlocked")){
                Actions();
            }
        }
    }

    public void Actions() {
        if (do_what.contains("call")) {
            Toast.makeText(context, "Making a Call", Toast.LENGTH_SHORT).show();
            new startCall(context, information.call_number);
        }
        if (do_what.contains("email")) {
            Toast.makeText(context, "Sending an Email", Toast.LENGTH_SHORT).show();
            startEmail se = new startEmail(context, information.email_from, information.email_to, information.email_subject, information.email_message);
            se.sendMessage();
        }
        if (do_what.contains("sms")) {
            Toast.makeText(context, "Sending a text message", Toast.LENGTH_SHORT).show();
            MainActivity mainActivity = new MainActivity();
            mainActivity.startSms(information.sms_number, information.sms_text);
        }
        if (do_what.contains("sound")) {
            Toast.makeText(context, "Playing a music now", Toast.LENGTH_SHORT).show();
            new startSound(context, information.sound_path);
        }
    }
}
