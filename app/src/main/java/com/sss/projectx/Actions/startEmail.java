package com.sss.projectx.Actions;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.sss.projectx.Others.Mail;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;

/**
 * Created by Control & Inst. LAB on 23-Sep-16.
 */
public class startEmail {

    Context context;
    String email_from,email_to,email_subject,email_body;

    public startEmail(Context context, String email_from, String email_to, String email_subject, String email_body) {
        this.context = context;
        this.email_from = email_from;
        this.email_to = email_to;
        this.email_subject = email_subject;
        this.email_body = email_body;
    }

    public void sendMessage() {
        String[] recipients = { email_to };
        String user = "tagtagdeco@gmail.com";
        String pass = "gisanrin123";
        SendEmailAsyncTask email = new SendEmailAsyncTask();
        email.m = new Mail(user, pass);
        email.m.set_from(email_from);
        email.m.setBody(email_body);
        email.m.set_to(recipients);
        email.m.set_subject(email_subject);
        email.execute();
    }

    class SendEmailAsyncTask extends AsyncTask<Void, Void, Boolean> {
        Mail m;

        public SendEmailAsyncTask() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                if (m.send()) {
                    displayMessage("Email sent.");
                } else {
                    displayMessage("Email failed to send.");
                }

                return true;
            } catch (AuthenticationFailedException e) {
                Log.e(SendEmailAsyncTask.class.getName(), "Bad account details");
                e.printStackTrace();
                displayMessage("Authentication failed.");
                return false;
            } catch (MessagingException e) {
                Log.e(SendEmailAsyncTask.class.getName(), "Email failed");
                e.printStackTrace();
                displayMessage("Email failed to send.");
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                displayMessage("Unexpected error occured.");
                return false;
            }
        }
    }

    public void displayMessage(String message) {
        //Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
