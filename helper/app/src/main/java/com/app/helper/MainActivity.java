package com.app.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.multidex.MultiDex;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //启动service
        Intent service = new Intent(MainActivity.this, NetHelperService.class);
        MainActivity.this.startService(service);
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