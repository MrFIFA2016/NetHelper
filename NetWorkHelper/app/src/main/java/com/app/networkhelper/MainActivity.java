package com.app.networkhelper;

import android.os.Bundle;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.networkhelper.to.WsMsg;
import com.app.networkhelper.to.WsReq;
import com.app.networkhelper.tools.WsManager;
import com.app.networkhelper.tools.WsMsgReader;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONObject;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BootStrap.init();
        BootStrap.load();

        BootStrap.wsManager.addMessageListener(new WsManager.MessageListener() {
            @Override
            public void onMessage(WsMsg message) {
                showInfo(message.toString());
            }

            @Override
            public void onError(String error) {
                showInfo(error);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "开启WS", Toast.LENGTH_SHORT).show();
                try {
                    try {
                        JSONObject json = new JSONObject();
                        json.put("请求时间", new Date().toLocaleString());
                        WsMsg req = new WsMsg();
                        req.setSender(0);
                        req.setMsg(json.toString());
                        BootStrap.wsManager.sendMessage(req);
                    } catch (Exception e) {
                    }
                } catch (Exception e) {
                }
            }
        });
        Button sendBtn = findViewById(R.id.sendHttp);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Looper.prepare();
                            OkHttpClient client = new OkHttpClient.Builder().build();
                            Request.Builder rb = new Request.Builder();
                            rb.url("http://10.10.133.132:8000");
                            rb.get();
                            Call call = client.newCall(rb.build());
                            Response response = call.execute();
                            String string = response.body().string();
                            showToast(Toast.makeText(MainActivity.this, string, Toast.LENGTH_LONG));
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            Looper.loop();
                        }
                    }
                }).start();
            }
        });
    }

    public void showInfo(String info) {
        TextView tv =findViewById(R.id.info_view);
        tv.setText(info);
    }

    public static void showToast(Toast toast) {
        toast.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}