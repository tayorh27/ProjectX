package com.sss.projectx.Async;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;

/**
 * Created by Control & Inst. LAB on 23-Sep-16.
 */
public class BackgroundTask extends AsyncTask<Void, Void, Void> {
    Context context;

    public BackgroundTask(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, Uri.parse("file://" + "/storage/sdcard0/Music/Simi_FreshBazecom_-_Open_Close.mp3"));
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(100, 100);
        mediaPlayer.start();

        return null;
    }
}
