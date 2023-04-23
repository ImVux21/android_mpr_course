package com.example.alarmapp;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TurnOffReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Stop the AlarmService
        Intent stopServiceIntent = new Intent(context, AlarmService.class);
        context.stopService(stopServiceIntent);

        // Dismiss the notification
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(1);
    }
}
