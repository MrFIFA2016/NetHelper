package com.app.helper.hook.hooker;

import android.app.Activity;
import android.util.Log;
import com.app.helper.hook.TestClass;
import com.swift.sandhook.SandHook;
import com.swift.sandhook.annotation.*;

import java.lang.reflect.Method;

@HookClass(TestClass.class)
public class NewAnnotationApiHooker {

    @HookMethodBackup("testNewHookApi")
    @SkipParamCheck
    static Method backup;

    @HookMethod("testNewHookApi")
    public static void onTestNewHookApi(@ThisObject TestClass thiz, @Param("com.swift.sandhook.MainActivity") Activity activity, int a) throws Throwable {
        Log.e("TestClassHook", "testNewHookApi been hooked");
        SandHook.callOriginByBackup(backup, thiz, activity, a);
    }

    @HookMethodBackup("testNewHookApi")
    public static void onTestNewHookApiBackup(@ThisObject TestClass thiz, @Param("com.swift.sandhook.MainActivity") Activity activity, int a) {
        onTestNewHookApiBackup(thiz, activity, a);
    }

}
