package com.chris_guzman.learn_to_use_websockets;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import okhttp3.WebSocket;

public class MainActivity extends AppCompatActivity implements ShowWebSocketMessage {
    private static final String TAG = "WS";
    private TextView echoTxt;
    private EditText editTxt;
    private Button sendBtn;
    private WebSocket ws;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        echoTxt = (TextView) findViewById(R.id.echo_text);
        editTxt = (EditText) findViewById(R.id.edit_text);
        sendBtn = (Button) findViewById(R.id.send_btn);

        WebSocketApplication application = (WebSocketApplication) getApplication();
        ws = application.getWebSocket();
        ws.send("this is a test");

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToTheVoid();
            }
        });
    }

    private void sendToTheVoid() {
        ws.send(editTxt.getText().toString());
        Log.d(TAG, "sendToTheVoid: " + editTxt.getText().toString());
        editTxt.setText(null);
    }

    @Override
    public void showMessage(String text) {
        Log.d(TAG, "showMessage: " + text);
        echoTxt.setText(text);
    }
}
