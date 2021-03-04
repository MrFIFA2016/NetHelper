package com.app.helper;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

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

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("NetHelperService", "NetHelperService onStartCommand");
//        ExecutorService threadPool = Executors.newCachedThreadPool();
//        threadPool.execute(new Runnable() {
//            @Override
//            public void run() {
//                int time = 0;
//                while (true) {
//                    Log.d("NetHelperService", "HeartBeat " + time);
////                    try {
////                        Thread.sleep(10000);
////                    } catch (InterruptedException e) {
////                        e.printStackTrace();
////                    }
//                    time++;
//                }
//            }
//        });
        BootStrap.init();
        BootStrap.load();

        return super.onStartCommand(intent, flags, startId);
    }
}