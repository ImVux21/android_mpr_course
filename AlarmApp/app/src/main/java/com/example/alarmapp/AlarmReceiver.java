package com.example.alarmapp;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.io.IOException;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "alarm_channel";
    private static final int NOTIFICATION_ID = 1;

    @SuppressLint({"UnsafeProtectedBroadcastReceiver", "ObsoleteSdkInt"})
    @Override
    public void onReceive(Context context, Intent intent) {

        // Start the AlarmService to play the alarm sound
        Intent startServiceIntent = new Intent(context, AlarmService.class);
        context.startService(startServiceIntent);


        // Create a notification channel for Android Oreo and higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Alarm Channel", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        // Create a notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_access_alarm_24)
                .setContentTitle("Alarm")
                .setContentText("Time's up!")
                .setOngoing(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // Set the "Turn Off" button click listener
        Intent turnOffIntent = new Intent(context, TurnOffReceiver.class);
        PendingIntent turnOffPendingIntent = PendingIntent.getBroadcast(context, 0, turnOffIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.addAction(R.drawable.baseline_access_alarm_24, "Turn Off", turnOffPendingIntent);

        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

}
