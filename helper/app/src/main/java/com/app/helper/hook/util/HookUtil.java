package com.app.helper.hook.util;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.alibaba.fastjson.JSONObject;
import com.app.helper.BootStrap;
import com.app.helper.pojo.WsMsg;
import com.swift.sandhook.SandHook;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HookUtil {

    private static DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd:HH:mm:ss.SSS");

    private static Handler mHandler = new Handler(Looper.myLooper());

    private static Runnable runnable = new Runnable() {
        @Override
        public void run() {
            sendLogToServer("level", "tag", String.valueOf(System.currentTimeMillis()));
        }
    };

    public static Object getObject(long address) {
        return SandHook.getObject(address);
    }

    public static long getAddress(Object object) {
        return SandHook.getObjectAddress(object);
    }

    public synchronized static boolean sendLogToServer(String level, String tag, String msg) {
        if (!BootStrap.sendLog)
            return true;

        Date date = new Date();
        String id = dateFormat.format(date);
        WsMsg wsMsg = new WsMsg();
        wsMsg.setId(id);
        wsMsg.setType(WsMsg.MsgType.CLIENT_LOG.name());
        JSONObject content = new JSONObject();
        content.put(level + "/" + tag, msg);
        wsMsg.setMsg(content);
        return BootStrap.wsManager.sendMessage(wsMsg);
    }

    public static <T> boolean recordData(String tag, T data) {
        Date date = new Date();
        String id = dateFormat.format(date);
        WsMsg wsMsg = new WsMsg();
        wsMsg.setId(id);
        wsMsg.setType(WsMsg.MsgType.DATA_RECORD.name());
        JSONObject content = new JSONObject();
        content.put(tag, data);
        wsMsg.setMsg(content);
        return BootStrap.wsManager.sendMessage(wsMsg);
    }

    public static int logW(String tag, String w) {
        sendLogToServer("warn", tag, w);
        return 1;
    }

    public static int logI(String tag, String i) {
        sendLogToServer("info", tag, i);
        return 1;
    }

    public static int logV(String tag, String v) {
        sendLogToServer("verbose", tag, v);
        return 1;
    }
}
