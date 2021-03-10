package com.app.helper;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.multidex.MultiDex;
import com.app.helper.hook.hooker.LogHooker;
import com.swift.sandhook.SandHook;
import com.swift.sandhook.SandHookConfig;
import com.swift.sandhook.wrapper.HookErrorException;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SandHookConfig.DEBUG = BuildConfig.DEBUG;

        if (Build.VERSION.SDK_INT == 29 && getPreviewSDKInt() > 0) {
            // Android R preview
            SandHookConfig.SDK_INT = 30;
        }

        SandHook.disableVMInline();
        SandHook.tryDisableProfile(getPackageName());
        SandHook.disableDex2oatInline(false);

        if (SandHookConfig.SDK_INT >= Build.VERSION_CODES.P) {
            SandHook.passApiCheck();
        }

        try {
            SandHook.addHookClass(LogHooker.class);
        } catch (HookErrorException e) {
            e.printStackTrace();
        }


        //启动service
        Intent service = new Intent(MainActivity.this, NetHelperService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            MainActivity.this.startForegroundService(service);
        } else {
            MainActivity.this.startService(service);
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
        //解决4.x运行崩溃的问题
        MultiDex.install(this);
    }
}