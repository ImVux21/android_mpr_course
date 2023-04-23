package com.example.alarmapp;

import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;

import androidx.annotation.Nullable;

import java.io.IOException;

public class AlarmService extends Service {
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        // Create and configure the MediaPlayer instance
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(this, Settings.System.DEFAULT_ALARM_ALERT_URI);
            mediaPlayer.setLooping(true);
            mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build());
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Start playing the alarm sound
        mediaPlayer.start();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        // Stop and release the MediaPlayer instance
        mediaPlayer.stop();
        mediaPlayer.release();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
