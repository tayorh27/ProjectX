package com.sss.projectx.Others;

/**
 * Created by Control & Inst. LAB on 23-Sep-16.
 */
public class Information {

    public String sound_path,email_from, email_to, email_subject, email_message, sms_number, sms_text, call_number;

    public Information(String sound_path,String email_from, String email_to, String email_subject, String email_message, String sms_number, String sms_text, String call_number) {

        this.sound_path = sound_path;
        this.email_from = email_from;
        this.email_to = email_to;
        this.email_subject = email_subject;
        this.email_message = email_message;
        this.sms_number = sms_number;
        this.sms_text = sms_text;
        this.call_number = call_number;

    }
}
