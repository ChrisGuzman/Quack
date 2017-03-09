package com.chris_guzman.learn_to_use_websockets;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.suitebuilder.TestMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class MainActivity extends AppCompatActivity {
    private OkHttpClient client;
    private TextView echoTxt;
    private EditText editTxt;
    private Button sendBtn;
    private WebSocket ws;

    private final class EchoWebSocketListener extends WebSocketListener {
        private static final int NORMAL_CLOSURE_STATUS = 1000;

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            updateEchoTxt(text);
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

    private void updateEchoTxt(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                echoTxt.setText( echoTxt.getText().toString() + "\n" + text);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        echoTxt = (TextView) findViewById(R.id.echo_text);
        editTxt = (EditText) findViewById(R.id.edit_text);
        sendBtn = (Button) findViewById(R.id.send_btn);
        client = new OkHttpClient();

        startWebSocket();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToTheVoid();
            }
        });
    }

    private void sendToTheVoid() {
        ws.send(editTxt.getText().toString());
        editTxt.setText(null);
    }

    private void startWebSocket() {
        Request request = new Request.Builder().url("ws://echo.websocket.org").build();
        WebSocketListener listener = new EchoWebSocketListener();
        ws = client.newWebSocket(request, listener);
//        client.dispatcher().executorService().shutdown();
    }
}
