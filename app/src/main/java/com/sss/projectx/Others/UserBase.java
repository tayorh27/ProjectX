package com.sss.projectx.Others;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Control & Inst. LAB on 23-Sep-16.
 */
public class UserBase {

    Context context;
    SharedPreferences preferences;

    public UserBase(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences("myPreference", 0);
    }

    public void setActionSettings(Information information) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("sound_path", information.sound_path);
        editor.putString("email_from", information.email_from);
        editor.putString("email_to", information.email_to);
        editor.putString("email_subject", information.email_subject);
        editor.putString("email_message", information.email_message);
        editor.putString("sms_number", information.sms_number);
        editor.putString("sms_text", information.sms_text);
        editor.putString("call_number", information.call_number);
        editor.apply();
    }

    public Information getActionSettings() {
        String a = preferences.getString("sound_path", "");
        String z = preferences.getString("email_from", "");
        String b = preferences.getString("email_to", "");
        String c = preferences.getString("email_subject", "");
        String d = preferences.getString("email_message", "");
        String e = preferences.getString("sms_number", "");
        String f = preferences.getString("sms_text", "");
        String g = preferences.getString("call_number", "");
        Information information = new Information(a,z,b,c,d,e,f,g);
        return information;
    }

    public void setSensorsSettings(SensorsInfo si){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("light_intensity",si.light_intensity);
        editor.putInt("proximity_intensity",si.proximity_intensity);
        editor.apply();
    }

    public SensorsInfo getSensorsSettings(){
        int light_intensity = preferences.getInt("light_intensity",0);
        int proximity_intensity = preferences.getInt("proximity_intensity",0);
        SensorsInfo si = new SensorsInfo(light_intensity,proximity_intensity);
        return si;
    }

    public void setActivation(int code) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("activation", code);
        editor.apply();
    }

    public int getCode() {
        return preferences.getInt("activation", 0);
    }

    public void setActivated(boolean active) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("activated", active);
        editor.apply();
    }

    public boolean getActivated() {
        return preferences.getBoolean("activated", false);
    }

    public void setCounter(int code) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Counter", code);
        editor.apply();
    }

    public int getCounter() {
        return preferences.getInt("Counter", 0);
    }

    public void setLogged(boolean logged) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("logged", logged);
        editor.apply();
    }

    public boolean getLogged() {
        return preferences.getBoolean("logged", false);
    }

    public void clear() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}
