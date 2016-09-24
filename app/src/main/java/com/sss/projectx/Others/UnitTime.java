package com.sss.projectx.Others;

/**
 * Created by Control & Inst. LAB on 23-Sep-16.
 */
public class UnitTime {

    public static long getTimeMilli(String time) {
        long milli = 0;
        switch (time) {
            case "5 seconds":
                milli = 5 * 1000;
                break;
            case "20 seconds":
                milli = 20 * 1000;
                break;
            case "30 seconds":
                milli = 30 * 1000;
                break;
            case "40 seconds":
                milli = 40 * 1000;
                break;
            case "50 seconds":
                milli = 50 * 1000;
                break;
            case "1 minute":
                milli = 60 * 1000;
                break;
            case "2 minute":
                milli = 2 * 60 * 1000;
                break;
            case "3 minute":
                milli = 3 * 60 * 1000;
                break;
            case "4 minute":
                milli = 4 * 60 * 1000;
                break;
            case "5 minute":
                milli = 5 * 60 * 1000;
                break;
        }
        return milli;
    }
}
