package com.matan.themtpchaser;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class BackgroundMusicService extends Service {
    MediaPlayer mp;
    public BackgroundMusicService() {
        //בנאי ריק
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //הפעלת מוזיקת הרקע בלופ
        mp = MediaPlayer.create(this,R.raw.backgroundsoundservice);
        mp.setLooping(true);
        mp.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        //עצירת מוזיקת הרקע
        super.onDestroy();
        mp.stop();
    }
}