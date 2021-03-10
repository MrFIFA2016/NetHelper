package com.app.helper.hook.hooker;

import android.util.Log;
import com.app.helper.hook.TestClass;
import com.swift.sandhook.SandHook;
import com.swift.sandhook.annotation.*;

import java.lang.reflect.Method;

@HookClass(TestClass.class)
public class CtrHook {

    @HookMethodBackup
    @SkipParamCheck
    static Method ctrbackup;

    @HookMethodBackup("add1")
    @SkipParamCheck
    static Method add1backup;

    @HookMethodBackup("add2")
    @SkipParamCheck
    static Method add2backup;

    @HookMethod
    public static void onCtr(@ThisObject TestClass thiz, int a) throws Throwable {
        Log.e("TestClassHook", "TestClass(int) been hooked");
        SandHook.callOriginByBackup(ctrbackup, thiz, a);
    }

    @HookMethod("add1")
    @HookMode(HookMode.INLINE)
    public static void onAdd1(TestClass thiz) throws Throwable {
        Log.e("TestClassHook", "add1 been hooked");
        try {
            SandHook.callOriginByBackup(add1backup, thiz);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @HookMethod("add2")
    public static void onAdd2(TestClass thiz) throws  Throwable {
        SandHook.callOriginByBackup(add2backup, thiz);
    }



}
