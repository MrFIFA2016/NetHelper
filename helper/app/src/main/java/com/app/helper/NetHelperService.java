package com.app.helper;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetHelperService extends Service {
    public NetHelperService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.v("NetHelperService", "NetHelperService onBind");
        return null;
    }

    @Override
    public void onCreate() {
        Log.v("NetHelperService", "NetHelperService onCreate");
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.v("NetHelperService", "NetHelperService onStart");
        super.onStart(intent, startId);
    }

    public static Notification notification;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("NetHelperService", "NetHelperService onStartCommand");

        BootStrap.init();
        BootStrap.load();

        String CHANNEL_ONE_ID = "com.app.helper";
        String CHANNEL_ONE_NAME = "Channel One";

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //Notification.Builder builder = new Notification.Builder(this);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        NotificationChannel notificationChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(CHANNEL_ONE_ID,
                    CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            manager.createNotificationChannel(notificationChannel);
            builder.setChannelId(CHANNEL_ONE_ID);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        notification = builder.setTicker("Nature")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("HelperAPP")
                .setContentText("连接到ws://" + BootStrap.serverUri)
                .setContentIntent(pendingIntent)
                .setVisibility(1)
                .build();
        notification.flags |= Notification.FLAG_NO_CLEAR;
        startForeground(1, notification);


        return super.onStartCommand(intent, flags, startId);
    }
}