package com.sss.projectx.Others;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

import com.sss.projectx.Actions.startCall;
import com.sss.projectx.Actions.startEmail;
import com.sss.projectx.Actions.startSms;
import com.sss.projectx.Sensors.startLight;
import com.sss.projectx.Sensors.startProximity;

/**
 * Created by Control & Inst. LAB on 23-Sep-16.
 */
public class CountDownClass extends CountDownTimer {

    String do_what, getSensor, delayTime;
    UserBase userBase;
    Context context;
    Information information;
    SensorsInfo sensorsInfo;
    startLight sl;
    startProximity sp;

    public CountDownClass(Context context, String do_what, String getSensor, String delayTime, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        try {
            this.do_what = do_what;
            this.getSensor = getSensor;
            this.delayTime = delayTime;
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
            sl = new startLight(context);
            long future = UnitTime.getTimeMilli(delayTime);
            DelayCountDown dc = new DelayCountDown("light",future, 1000);
            dc.start();
        }
        if (getSensor.contains("proximity")) {
            sp = new startProximity(context);
            long future = UnitTime.getTimeMilli(delayTime);
            DelayCountDown dc = new DelayCountDown("proximity",future, 1000);
            dc.start();
        }
        if (getSensor.contains("power")) {
            //startPower sp = new startPower();
            //String charging = sp.CheckPower();
            //new MyApplication().regPower();
            String charging = userBase.getPower();
            if (charging.contains("charging")) {
                Actions();
            }
            //new MyApplication().unregPower();
        }
        if (getSensor.contains("unlock")) {
            //startUnlock su = new startUnlock();
            //su.CheckPower();
            //new MyApplication().regUnlock();
            String un = userBase.getUnlock();
            if (un.contentEquals("unlocked")) {
                Actions();
            }
            //new MyApplication().unregUnlock();
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
            new startSms().Sms(information.sms_number, information.sms_text);
        }
        if (do_what.contains("sound")) {
            Toast.makeText(context, "Playing a music now", Toast.LENGTH_SHORT).show();
            //MyApplication.playSound(information.sound_path);
            //MainActivity activity = new MainActivity();
            //activity.playSound(information.sound_path);
            //new startSound(context, information.sound_path);
            //new BackgroundTask(context).execute();
        }
    }

    class DelayCountDown extends CountDownTimer {

        String what;
        public DelayCountDown(String what, long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            this.what = what;
        }

        @Override
        public void onTick(long l) {

        }

        @Override
        public void onFinish() {
            if(what.contentEquals("light")) {
                Log.e("Current Reading", "Current Reading For Light = " + sl.current);
                if (sl.current == sensorsInfo.light_intensity) {
                    Actions();
                }
            }
            if(what.contentEquals("proximity")) {
                Log.e("Current Reading", "Current Reading For Proximity = " + sp.current);
                if (sp.current == sensorsInfo.proximity_intensity) {
                    Actions();
                }
            }
        }
    }
}
