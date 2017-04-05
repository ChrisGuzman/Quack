package com.chris_guzman.learn_to_use_websockets;

import android.util.Log;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * Created by chrisguzman on 4/4/17.
 */

public class EchoWebSocketListener extends WebSocketListener {
    private static final String TAG = "WS";
    ShowWebSocketMessage showWebSocketMessage;

    @Override
    public void onOpen(WebSocket webSocket, Response response) {

    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        Log.d(TAG, "onMessage: " + text);
        showWebSocketMessage.showMessage(text);
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
    }
}
