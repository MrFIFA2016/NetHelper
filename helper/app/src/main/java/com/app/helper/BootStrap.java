package com.app.helper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.app.helper.hook.hooker.LogHooker;
import com.app.helper.pojo.WsMsg;
import com.app.helper.tools.ContextUtil;
import com.app.helper.tools.MsgHandler;
import com.app.helper.tools.WsManager;

import java.util.concurrent.TimeUnit;

import com.swift.sandhook.SandHook;
import com.swift.sandhook.wrapper.HookErrorException;

import okhttp3.OkHttpClient;

public class BootStrap {

    //------------------------配置区---------------------------


    //public static String serverUri = "192.168.1.21:5678";
    public static String serverUri = "10.10.133.79:5678";

    public static boolean sendLog = true;


    //--------------------------------------------------------

    public static WsManager wsManager;

    public static String init() {
        String info = "OK!";
        try {
            initWebSocket();
            Log.i("BootStrap.initWebSocket", "ws init success ! ");
            initSandHook();
            Log.i("BootStrap.initSandHook", "sandhook init success ! ");
        } catch (Exception e) {
            e.printStackTrace();
            info = e.getMessage();
        }
        return info;
    }

    public static String load() {
        String info = "OK!";
        try {
            wsManager.startConnect();
            wsManager.addMessageListener(new WsManager.MessageListener() {
                @Override
                public void onMessage(WsMsg message) {//收到服务器消息处理
                    //long address = SandHook.getObjectAddress(message);
                    MsgHandler.handleMsg(message, content -> {
                        Toast.makeText(ContextUtil.getContextReflect(), content, Toast.LENGTH_LONG).show();
                    });
//                    Object object = SandHook.getObject(address);
//                    Log.i("type", object.toString());
                }

                @Override
                public void onError(String error) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            info = e.getMessage();
        }
        return info;
    }

    public static void close() {
        if (wsManager != null)
            wsManager.stopConnect();
    }

    private static void initSandHook() {
//        try {
//            SandHook.addHookClass(LogHooker.class);
//        } catch (HookErrorException e) {
//            e.printStackTrace();
//        }
    }

    private static void initWebSocket() {
        Context context = ContextUtil.getContextReflect();
        wsManager = new WsManager.Builder(context).client(
                new OkHttpClient().newBuilder()
                        .pingInterval(10, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(true)
                        .build())
                .needReconnect(true)
                .wsUrl("ws://" + serverUri)
                .build();
    }

}
