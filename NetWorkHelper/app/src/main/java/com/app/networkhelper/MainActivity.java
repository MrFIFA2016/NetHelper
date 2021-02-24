package com.app.networkhelper;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.app.networkhelper.tools.JWebSocketClient;
import com.app.networkhelper.tools.WsManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;

public class MainActivity extends AppCompatActivity {
//    final WebSocketClient client = new WebSocketClient(URI.create("ws://192.168.1.21:5678")) {
//        @Override
//        public void onOpen(ServerHandshake handshakedata) {
//            Toast.makeText(MainActivity.this, "WS onOpen", Toast.LENGTH_LONG).show();
//
//        }
//
//        @Override
//        public void onMessage(String message) {
//            //message就是接收到的消息
//            //Log.e("JWebSClientService", message);
//            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
//        }
//
//        @Override
//        public void onClose(int code, String reason, boolean remote) {
//            //Toast.makeText(MainActivity.this, reason, Toast.LENGTH_LONG).show();
//        }
//
//        @Override
//        public void onError(Exception ex) {
//            //Toast.makeText(MainActivity.this, ex.getLocalizedMessage(), Toast.LENGTH_LONG).show();
//            ex.printStackTrace();
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final WsManager wsManager = new WsManager.Builder(getBaseContext()).client(
                new OkHttpClient().newBuilder()
                        .pingInterval(15, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(true)
                        .build())
                .needReconnect(true)
                .wsUrl("ws://192.168.1.21:5678")
                .build();
        wsManager.startConnect();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "开启WS", Toast.LENGTH_SHORT).show();
                try {
                    try {
                        wsManager.sendMessage(new Date().toString());
//                        client.reconnect();
//                        client.sendPing();
                        //Toast.makeText(MainActivity.this, "已连接", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        //Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
//                    if (client != null && client.isOpen()) {
//                        client.send("你好");
//                    }
                } catch (Exception e) {
                    //Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
                            rb.url("http://192.168.1.21:8000");
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