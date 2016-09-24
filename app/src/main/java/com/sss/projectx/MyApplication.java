package com.sss.projectx;

import android.app.Application;
import android.content.Context;

import com.sss.projectx.Others.Database;

/**
 * Created by Control & Inst. LAB on 23-Sep-16.
 */
public class MyApplication extends Application {

    private static MyApplication sInstance;
    private static Database localDB;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        localDB = new Database(this);
    }

    public static MyApplication getInstance(){
        return sInstance;
    }

    public static Context getAppContext(){
        return sInstance.getApplicationContext();
    }

    public synchronized static Database getWritableDatabase(){
        if (localDB == null){
            localDB = new Database(getAppContext());
        }
        return localDB;

    }
}
