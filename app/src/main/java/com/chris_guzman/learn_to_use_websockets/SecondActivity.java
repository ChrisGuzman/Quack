package com.chris_guzman.learn_to_use_websockets;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import okhttp3.WebSocket;

public class SecondActivity extends AppCompatActivity implements ShowInActivityHandler {
    private static final String TAG = "WS";
    private WebSocket ws;
    private TextView chatTxt;
    private Button sendMsgBtn;
    private EditText msgEditTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        OkHttpUtil.getInstance().setShowInActivityHandler(this);
        ws = OkHttpUtil.getInstance().getWs();

        chatTxt = (TextView) findViewById(R.id.chat_txt);
        msgEditTxt = (EditText) findViewById(R.id.msg_edit_txt);
        sendMsgBtn = (Button) findViewById(R.id.send_msg_btn);
        sendMsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ws.send(msgEditTxt.getText().toString());
                msgEditTxt.setText(null);
            }
        });

        ws.send("Hey from Activity #2");
    }

    @Override
    public void showMessage(final String text) {
        Log.d(TAG, "SecondActivity onMessageReceived: " + text);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chatTxt.setText(chatTxt.getText().toString() + "\n" + text );
            }
        });
    }
}
