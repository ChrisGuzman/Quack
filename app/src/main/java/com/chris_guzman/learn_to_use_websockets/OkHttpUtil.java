package com.chris_guzman.learn_to_use_websockets;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

/**
 * Created by chrisguzman on 4/4/17.
 */

public class OkHttpUtil extends OkHttpClient implements WebSocketMessageHandler {
    private static final String TAG = "WS";
    private static OkHttpUtil instance = null;
    private WebSocket ws;
    ShowInActivityHandler showInActivityHandler;

    public OkHttpUtil() {
    }

    public static OkHttpUtil getInstance() {
        if (instance == null) {
            instance = new OkHttpUtil();
        }
        return instance;
    }

    public void init() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("ws://echo.websocket.org").build();
        ws = client.newWebSocket(request, new EchoWebSocketListener(this));
    }

    public WebSocket getWs() {
        return ws;
    }

    @Override
    public void onMessageReceived(String text) {
        Log.d(TAG, "OkHttpUtil onMessageReceived: " + text);
        if (showInActivityHandler != null) {
            showInActivityHandler.showMessage(text);
        }
    }

    public void setShowInActivityHandler(ShowInActivityHandler showInActivityHandler) {
        this.showInActivityHandler = showInActivityHandler;
    }
}
