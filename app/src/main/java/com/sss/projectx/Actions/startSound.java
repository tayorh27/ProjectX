package com.sss.projectx.Actions;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Control & Inst. LAB on 23-Sep-16.
 */
public class startSound {
    Context context;

    public startSound(Context context, String path) {
        this.context = context;
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(context, Uri.parse("file://" + path));
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            Log.e("Error frm sound", e.toString()+"|"+path);
        }
    }
}
