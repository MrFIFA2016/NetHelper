package com.app.helper;

import android.content.Context;


import com.app.helper.tools.ContextUtil;
import com.app.helper.tools.WsManager;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class BootStrap {

    public static String serverUri = "192.168.1.21:5678";

    public static boolean inited = false;

    public static WsManager wsManager = null;

    public static String init() {
        String info = "OK!";
        try {
            initWebSocket();
        } catch (Exception e) {
            e.printStackTrace();
            info = e.getMessage();
        }
        inited = true;
        return info;
    }

    public static String load() {
        String info = "OK!";
        try {
            wsManager.startConnect();
        } catch (Exception e) {
            e.printStackTrace();
            info = e.getMessage();
        }
        inited = true;
        return info;
    }

    public static void say() {
        ContextUtil.show();
    }

    private static void initWebSocket() {
        Context context = ContextUtil.getContextReflect();
        wsManager = new WsManager.Builder(context).client(
                new OkHttpClient().newBuilder()
                        .pingInterval(15, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(true)
                        .build())
                .needReconnect(true)
                .wsUrl("ws://" + serverUri)
                .build();

    }

}
