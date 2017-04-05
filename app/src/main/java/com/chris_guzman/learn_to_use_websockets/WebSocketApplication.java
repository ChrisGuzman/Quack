package com.chris_guzman.learn_to_use_websockets;

import android.app.Application;
import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by chrisguzman on 4/4/17.
 */

public class WebSocketApplication extends Application {
    private static final String TAG = "WS";
    private WebSocket ws;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: started");
        startWebSocket();
    }

    private void startWebSocket() {
        Request request = new Request.Builder().url("ws://echo.websocket.org").build();
        WebSocketListener listener = new EchoWebSocketListener();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
        ws = client.newWebSocket(request, listener);
        Log.d(TAG, "startWebSocket: ");
        ws.send("hello!");
//        client.dispatcher().executorService().shutdown();
    }

    public WebSocket getWebSocket() {
        return ws;
    }
}
