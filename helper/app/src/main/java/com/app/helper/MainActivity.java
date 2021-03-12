package com.app.helper;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.multidex.MultiDex;
import com.app.helper.hook.TestClass;
import com.app.helper.hook.hooker.ConstructionHooker;
import com.app.helper.hook.hooker.LogHooker;
import com.swift.sandhook.SandHook;
import com.swift.sandhook.SandHookConfig;
import com.swift.sandhook.wrapper.HookErrorException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class MainActivity extends Activity {

    public static int getPreviewSDKInt() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                return Build.VERSION.PREVIEW_SDK_INT;
            } catch (Throwable e) {
                // ignore
            }
        }
        return 0;
    }
static TestClass testClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BootStrap.init();
        BootStrap.load();


        SandHookConfig.DEBUG = BuildConfig.DEBUG;

        if (Build.VERSION.SDK_INT == 29 && getPreviewSDKInt() > 0) {
            // Android R preview
            SandHookConfig.SDK_INT = 30;
        }

        SandHook.disableVMInline();
        SandHook.tryDisableProfile(getPackageName());
        SandHook.disableDex2oatInline(false);

        if (SandHookConfig.SDK_INT >= Build.VERSION_CODES.P) {
            SandHook.passApiCheck();//允许反射调用hidden Api
        }

        try {
            SandHook.addHookClass(LogHooker.class, ConstructionHooker.class);
        } catch (HookErrorException e) {
            e.printStackTrace();
        }

        testClass = new TestClass(0);

        testClass.add2();

        //启动service
        Intent service = new Intent(MainActivity.this, NetHelperService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            MainActivity.this.startForegroundService(service);
        } else {
            MainActivity.this.startService(service);
        }

//        try {
//            test();
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
    }

    private void test() throws Throwable {
        Class<?> hclass = Class.forName("android.util.Log");
        Method[] declaredFields = hclass.getDeclaredMethods();
        for (Method declaredField : declaredFields) {
            Log.i("Service", "declareMethod: " + declaredField.toGenericString());
        }
    }

    public void showInfo(String content) {
        Toast.makeText(this, content, Toast.LENGTH_LONG).show();
    }

    //8.0以上手机需要添加此代码才能正常运行
    public void onResume() {
        super.onResume();
        finish();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //Reflection.unseal(base);
        //解决4.x运行崩溃的问题
        MultiDex.install(this);
    }
}