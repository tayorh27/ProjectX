package com.sss.projectx.Others;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.CountDownTimer;

/**
 * Created by Control & Inst. LAB on 23-Sep-16.
 */
public class DelayCountDownClass extends CountDownTimer {

    Context context;
    MediaPlayer mediaPlayer;

    public DelayCountDownClass(Context context, MediaPlayer mediaPlayer, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.context = context;
        this.mediaPlayer = mediaPlayer;
    }

    @Override
    public void onTick(long l) {

    }

    @Override
    public void onFinish() {
        mediaPlayer.release();
    }
}
