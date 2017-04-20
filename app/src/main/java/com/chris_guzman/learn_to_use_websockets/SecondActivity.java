package com.chris_guzman.learn_to_use_websockets;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import okhttp3.WebSocket;
import okio.ByteString;

public class SecondActivity extends AppCompatActivity implements ShowInActivityHandler {
    private static final String TAG = "WS";
    private TextView chatTxt;
    private Button sendMsgBtn;
    private EditText msgEditTxt;
    private TextView sendTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        OkHttpUtil.getInstance().getWs();

        chatTxt = (TextView) findViewById(R.id.chat_txt);
        msgEditTxt = (EditText) findViewById(R.id.msg_edit_txt);
        sendTxt = (TextView) findViewById(R.id.send_txt);
        sendMsgBtn = (Button) findViewById(R.id.send_msg_btn);
        sendMsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpUtil.getInstance().getWs().send(msgEditTxt.getText().toString());
                sendTxt.setText(msgEditTxt.getText().toString());
                msgEditTxt.setText(null);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        OkHttpUtil.getInstance().setShowInActivityHandler(this);
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

    @Override
    public void showImage(ByteString bytes) {

    }
}
