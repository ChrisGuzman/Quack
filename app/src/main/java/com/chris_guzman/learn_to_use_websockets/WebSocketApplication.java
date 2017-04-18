package com.chris_guzman.learn_to_use_websockets;

import android.app.Application;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.ByteString;

/**
 * Created by chrisguzman on 4/4/17.
 */

public class WebSocketApplication extends Application {
    private static final String TAG = "WS";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: started");
        OkHttpUtil.getInstance().init();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.d(TAG, "onTrimMemory: ");
        OkHttpUtil.getInstance().getWs().close(1000, "App backgrounded");
        OkHttpUtil.getInstance().setWs(null);
    }
}
