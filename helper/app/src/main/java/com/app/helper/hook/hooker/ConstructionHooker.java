package com.app.helper.hook.hooker;

import android.util.Log;
import com.app.helper.hook.TestClass;
import com.app.helper.hook.util.HookUtil;
import com.swift.sandhook.SandHook;
import com.swift.sandhook.annotation.*;

import java.lang.reflect.Method;

@HookClass(TestClass.class)
public class ConstructionHooker {

    /**
     * 构造方法Hook
     */
    @HookMethodBackup
    @SkipParamCheck
    static Method constructionMethodBackup;
//    /**
//     * 静态方法Hook
//     */
//    @HookMethodBackup("staticMethodHook")
//    @MethodParams({int.class, int.class})
//    static Method staticMethodBackup;
//
//
//    @HookMethodBackup("errorMethod")
//    @SkipParamCheck
//    static Method errorMethodBackup;

    @HookMethod
    public static void onConstructionHook(@ThisObject TestClass thiz, int a) throws Throwable {
        SandHook.callOriginByBackup(constructionMethodBackup, thiz, a);
        System.out.printf("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX" + thiz.toString());
        HookUtil.recordData(thiz.toString(), HookUtil.getAddress(thiz));
    }


//    @HookMethod("staticMethodHook")
//    @MethodParams({int.class, int.class})
//    public static int staticMethodHooked(int a, int b) {
//        try {
//            return (int) staticMethodBackup.invoke(null, a, b) + 1;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }
//
//
//    @HookMethod("errorMethod")
//    @HookMode(HookMode.INLINE)
//    public static void errorMethodHook(TestClass thiz) throws Throwable {
//        try {
//            SandHook.callOriginByBackup(errorMethodBackup, thiz);
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e("TestClassHook", "error:" + e.getMessage());
//        }
//    }

}
