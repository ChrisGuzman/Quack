package com.chris_guzman.learn_to_use_websockets;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class NateActivity extends AppCompatActivity {

    private String TAG = NateActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nate);

        WebSocketClient client = new WebSocketClient(URI.create("ws://echo.websocket.org")) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.d(TAG, "onOpen: " + serverHandshake.getHttpStatusMessage());
            }

            @Override
            public void onMessage(String s) {
                Log.d(TAG, "onMessage: " + s);
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.d(TAG, "onClose: " + s);
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, "onError: ", e);
            }
        };

        client.connect();

        client.send("Hello world!");
    }
}
