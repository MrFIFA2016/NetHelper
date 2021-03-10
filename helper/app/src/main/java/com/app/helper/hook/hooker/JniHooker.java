package com.app.helper.hook.hooker;

import android.os.Build;
import android.util.Log;
import com.app.helper.hook.TestClass;
import com.swift.sandhook.SandHook;
import com.swift.sandhook.annotation.*;

import java.lang.reflect.Method;

@HookClass(TestClass.class)
public class JniHooker {

    @HookMethodBackup("jni_test")
    @SkipParamCheck
    static Method backup;

    @HookMethod("jni_test")
    public static void onJni(@ThisObject TestClass thiz) throws Throwable {
        Log.e("JniHooker", "hooked success ");
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            SandHook.callOriginByBackup(backup, thiz);
        }
    }

}
