package com.sss.projectx.Async;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.sss.projectx.Others.CountDownClass;

import java.util.ArrayList;

/**
 * Created by Control & Inst. LAB on 23-Sep-16.
 */
public class BackgroundTask extends AsyncTask<Void,Void,Void>{

    ArrayList<String> do_what;
    Context context;
    long future;
    CountDownClass countDownClass;

    public BackgroundTask(Context context, ArrayList<String> do_what, long future){
        this.context = context;
        this.do_what = do_what;
        this.future = future;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                return false;
            }
        });

        return null;
    }
}
