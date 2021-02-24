package com.app.networkhelper;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.app.networkhelper.tools.JWebSocketClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class MainActivity extends AppCompatActivity {
    JWebSocketClient client = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "开启WS", Toast.LENGTH_SHORT).show();

                try {

                    URI uri = URI.create("ws://10.10.133.132:19011/ws?token=123456");
                    client = new JWebSocketClient(uri) {
                        @Override
                        public void onOpen(ServerHandshake handshakedata) {
                            Toast.makeText(MainActivity.this, "WS onOpen", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onMessage(String message) {
                            //message就是接收到的消息
                            //Log.e("JWebSClientService", message);
                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                        }
                    };
                    try {
                        client.connect();
                        Toast.makeText(MainActivity.this, "已连接", Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    if (client != null && client.isOpen()) {
                        client.send("你好");
                    }
                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
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