package com.app.helper.hook.hooker;

import android.util.Log;
import com.app.helper.hook.util.HookUtil;
import com.swift.sandhook.SandHook;
import com.swift.sandhook.annotation.*;

import java.lang.reflect.Method;

@HookClass(Log.class)
public class LogHooker {

    @HookMethodBackup("w")
    @SkipParamCheck
    static Method backupW;

//    @HookMethodBackup("i")
//    @SkipParamCheck
//    static Method backupI;

    @HookMethod("w")
    public static int onW(String tag, @Param("java.lang.String") Object msg) throws Throwable {
        HookUtil.logW(tag, msg.toString());
        return (int) SandHook.callOriginByBackup(backupW, null, tag, msg);
    }

//    @HookMethod("i")
//    public static int onI(String tag, @Param("java.lang.String") Object msg) throws Throwable {
//        HookUtil.logI("LogHooker", msg.toString());
//        return (int) SandHook.callOriginByBackup(backupI, null, tag, msg);
//    }

}
