package com.sss.projectx.Actions;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

/**
 * Created by Control & Inst. LAB on 23-Sep-16.
 */
public class startSound {
    Context context;

    public startSound(Context context, String path) {
        this.context = context;
        try {
            MediaPlayer mediaPlayer = MediaPlayer.create(context, Uri.parse("file://" + path));
            mediaPlayer.start();
            // mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            // mediaPlayer.setDataSource(context, Uri.parse("file://" + path));
            // mediaPlayer.prepare();
            // mediaPlayer.start();
        } catch (Exception e) {
            Log.e("Error frm sound", e.toString() + "|" + path);
        }
    }
}
