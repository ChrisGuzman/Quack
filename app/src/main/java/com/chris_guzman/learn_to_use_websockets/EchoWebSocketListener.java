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
    WebSocketMessageHandler webSocketMessageHandler;

    public EchoWebSocketListener(WebSocketMessageHandler webSocketMessageHandler) {
        this.webSocketMessageHandler = webSocketMessageHandler;
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        Log.d(TAG, "onOpen: ");
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        Log.d(TAG, "onMessage: " + text);
        webSocketMessageHandler.onMessageReceived(text);
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        Log.d(TAG, "onMessage: " + bytes.toString());
        webSocketMessageHandler.onBytesReceived(bytes);
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        Log.d(TAG, "onClosing: ");
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        Log.d(TAG, "onClosed: ");
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        Log.e(TAG, "onFailure: " + response.toString(), t);
    }
}
