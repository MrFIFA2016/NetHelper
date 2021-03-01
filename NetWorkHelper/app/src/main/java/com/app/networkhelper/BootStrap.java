package com.app.networkhelper;
import android.content.Context;
import com.app.networkhelper.tools.ContextUtil;
import com.app.networkhelper.tools.WsManager;
import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

public class BootStrap {

    public static String serverUri = "10.10.133.132:5678";

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
