package com.chris_guzman.learn_to_use_websockets;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import okhttp3.WebSocket;

public class MainActivity extends AppCompatActivity implements ShowInActivityHandler {
    private static final String TAG = "WS";
    private TextView echoTxt;
    private EditText editTxt;
    private Button sendBtn;
    private WebSocket ws;
    private Button nextActBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OkHttpUtil.getInstance().setShowInActivityHandler(this);
        ws = OkHttpUtil.getInstance().getWs();

        echoTxt = (TextView) findViewById(R.id.echo_text);
        editTxt = (EditText) findViewById(R.id.edit_text);
        sendBtn = (Button) findViewById(R.id.send_btn);
        nextActBtn = (Button) findViewById(R.id.next_act);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToTheVoid();
            }
        });

        nextActBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), SecondActivity.class));
            }
        });
    }

    private void sendToTheVoid() {
        ws.send(editTxt.getText().toString());
        Log.d(TAG, "sendToTheVoid: " + editTxt.getText().toString());
        editTxt.setText(null);
    }

    @Override
    public void showMessage(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                echoTxt.setText(echoTxt.getText().toString() + "\n" + text);
            }
        });
        Log.d(TAG, "MainActivity onMessageReceived: " + text);
    }
}
