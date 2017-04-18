package com.chris_guzman.learn_to_use_websockets;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okio.ByteString;

/**
 * Created by chrisguzman on 4/4/17.
 */

public class OkHttpUtil extends OkHttpClient implements WebSocketMessageHandler {
    private static final String TAG = "WS";
    private static OkHttpUtil instance = null;
    private WebSocket ws;
    ShowInActivityHandler showInActivityHandler;
    private OkHttpClient client;

    public OkHttpUtil() {}

    public static OkHttpUtil getInstance() {
        if (instance == null) {
            instance = new OkHttpUtil();
        }
        return instance;
    }

    public void init() {
        client = new OkHttpClient.Builder().pingInterval(500, TimeUnit.MILLISECONDS).build();
        Request request = new Request.Builder().url("ws://echo.websocket.org").build();
        ws = client.newWebSocket(request, new EchoWebSocketListener(this));
    }

    public WebSocket getWs() {
        if (ws == null) {
            Log.d(TAG, "populateWs: is null");
            Request request = new Request.Builder().url("ws://echo.websocket.org").build();
            ws = client.newWebSocket(request, new EchoWebSocketListener(this));
        }
        Log.d(TAG, "populateWs: is populated");
        return ws;
    }

    public void setWs(WebSocket ws) {
        this.ws = ws;
    }

    @Override
    public void onMessageReceived(String text) {
        Log.d(TAG, "OkHttpUtil onMessageReceived: " + text);
        if (showInActivityHandler != null) {
            showInActivityHandler.showMessage(text);
        }
    }

    @Override
    public void onBytesReceived(ByteString bytes) {
        Log.d(TAG, "OkHttpUtil onBytesReceived: " + bytes.toString());
        if (showInActivityHandler != null){
            showInActivityHandler.showImage(bytes);
        }
    }


    public void setShowInActivityHandler(ShowInActivityHandler showInActivityHandler) {
        this.showInActivityHandler = showInActivityHandler;
    }
}
