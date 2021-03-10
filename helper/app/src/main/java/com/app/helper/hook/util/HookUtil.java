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

//    private static DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy:HH:mm:ss.SSS");

    private static Handler mHandler = new Handler(Looper.getMainLooper());

    private static Runnable runnable = new Runnable() {
        @Override
        public void run() {
            sendLogToServer("level", "tag", String.valueOf(System.currentTimeMillis()));
        }
    };

    public static Object getObject(long address) {
        return SandHook.getObject(address);
    }

    public static boolean sendLogToServer(String level, String tag, String msg) {
        if (!BootStrap.sendLog)
            return true;

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy:HH:mm:ss.SSS");

        Date date = new Date();
        String id = dateFormat.format(date);
        WsMsg wsMsg = new WsMsg();
        wsMsg.setId(id);
        wsMsg.setType(WsMsg.MsgType.CLIENT_LOG.name());
        JSONObject content = new JSONObject();
        content.put(level + "/" + tag, msg);
        wsMsg.setMsg(content);
        return BootStrap.wsManager.sendMessage(wsMsg);
//        System.out.printf(wsMsg.toString());
//        return true;
    }

    public static int logW(String tag, String w) {
         sendLogToServer("warn", tag, w);
       // mHandler.post(runnable);
        return Log.w(tag, w);
    }

    public static int logI(String tag, String i) {
        sendLogToServer("info", tag, i);
        //mHandler.post(runnable);
        return Log.i(tag, i);
    }
}
