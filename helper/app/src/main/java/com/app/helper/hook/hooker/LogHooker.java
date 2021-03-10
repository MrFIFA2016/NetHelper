package com.app.helper.hook.hooker;

import android.util.Log;

import com.app.helper.hook.util.HookUtil;
import com.swift.sandhook.SandHook;
import com.swift.sandhook.annotation.*;

import java.lang.reflect.Method;

@HookClass(Log.class)
public class LogHooker {

    @HookMethodBackup("e")
    @SkipParamCheck
    static Method backupW;

    @HookMethodBackup("i")
    @SkipParamCheck
    static Method backupI;

    @HookMethodBackup("v")
    @SkipParamCheck
    static Method backupV;

//    @HookMethodBackup("println_native")
//    @SkipParamCheck
//    static Method backupPrintln;

    @HookMethod("e")
    public static int onW(String tag, @Param("java.lang.String") Object msg) throws Throwable {
        HookUtil.logW(tag, String.valueOf(msg));
        return (int) SandHook.callOriginByBackup(backupW, null, tag, msg);
    }

    @HookMethod("i")
    public static int onI(String tag, @Param("java.lang.String") Object msg) throws Throwable {
        HookUtil.logI("LogHooker", String.valueOf(msg));
        return (int) SandHook.callOriginByBackup(backupI, null, tag, msg);
    }

    @HookMethod("v")
    public static int onV(String tag, @Param("java.lang.String") Object msg) throws Throwable {
        HookUtil.logV("LogHooker", String.valueOf(msg));
        return (int) SandHook.callOriginByBackup(backupV, null, tag, msg);
    }

//    @HookMethod("println_native")
//    public static int onPrintln(int p1, int p2, String p3, String p4) throws Throwable {
//        HookUtil.logV("println", String.valueOf(p4));
//        return (int) SandHook.callOriginByBackup(backupPrintln, null, p1, p2, p3, p4);
//    }

}
