package com.sss.projectx;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;

import com.sss.projectx.Others.Database;
import com.sss.projectx.Others.UserBase;
import com.sss.projectx.Receivers.PowerReceiver;
import com.sss.projectx.Receivers.UnlockReceiver;

/**
 * Created by Control & Inst. LAB on 23-Sep-16.
 */
public class MyApplication extends Application {

    private static MyApplication sInstance;
    private static Database localDB;
    UserBase userBase;
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        localDB = new Database(this);
        userBase = new UserBase(this);
        //mediaPlayer = MediaPlayer.create(this, Uri.parse("file://" + "/storage/sdcard0/Music/Simi_FreshBazecom_-_Open_Close.mp3"));
        //mediaPlayer.start();

        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(new PowerReceiver(), filter);

        IntentFilter filter2 = new IntentFilter(Intent.ACTION_USER_PRESENT);
        registerReceiver(new UnlockReceiver(), filter2);
    }

    public static MyApplication getInstance() {
        return sInstance;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }

    public void playSound(String path) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });

    }

    public void regPower() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(new PowerReceiver(), filter);
    }

    public void unregPower() {
        unregisterReceiver(new PowerReceiver());
    }

    public void regUnlock() {
        IntentFilter filter2 = new IntentFilter(Intent.ACTION_USER_PRESENT);
        registerReceiver(new UnlockReceiver(), filter2);
    }

    public void unregUnlock() {
        unregisterReceiver(new UnlockReceiver());
    }


    public synchronized static Database getWritableDatabase() {
        if (localDB == null) {
            localDB = new Database(getAppContext());
        }
        return localDB;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        userBase.setLogged(false);
        unregPower();
        unregUnlock();
    }
}
