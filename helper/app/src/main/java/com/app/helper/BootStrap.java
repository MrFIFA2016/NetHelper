package com.app.helper;

import android.content.Context;
import android.util.Log;


import com.app.helper.executor.AbstractExecutor;
import com.app.helper.executor.FieldValSetter;
import com.app.helper.executor.FuncExecutor;
import com.app.helper.pojo.FieldSetMsg;
import com.app.helper.pojo.FuncExecMsg;
import com.app.helper.pojo.Msg;
import com.app.helper.pojo.NormalMsg;
import com.app.helper.pojo.WsMsg;
import com.app.helper.tools.ContextUtil;
import com.app.helper.tools.WsManager;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class BootStrap {

    public static String serverUri = "192.168.1.21:5678";

    public static WsManager wsManager;

    public static boolean inited = false;

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
            wsManager.addMessageListener(new WsManager.MessageListener() {
                @Override
                public void onMessage(WsMsg message) {
                    Msg msg = null;
                    AbstractExecutor executor = null;
                    if ("exec".equals(message.getType())) {
                        msg = new FuncExecMsg().resolveMsg(message);
                        executor = new FuncExecutor();
                    } else if ("setField".equals(message.getType())) {
                        msg = new FieldSetMsg().resolveMsg(message);
                        executor = new FieldValSetter();
                    } else {
                        msg = new NormalMsg().resolveMsg(message);
                        executor = new AbstractExecutor() {
                            @Override
                            public Object exec(Msg msg) throws Exception {
                                Log.v("BootStrap", msg.toString());
                                return null;
                            }

                            @Override
                            public Object getInstance(Object... args) {
                                return null;
                            }
                        };
                    }
                    try {
                        Object result = executor.exec(msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String error) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            info = e.getMessage();
        }
        inited = true;
        return info;
    }

    public static void close() {
        if (wsManager != null)
            wsManager.stopConnect();
    }

    public static void say() {
        ContextUtil.show();
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
