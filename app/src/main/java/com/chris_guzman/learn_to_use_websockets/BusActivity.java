package com.chris_guzman.learn_to_use_websockets;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import okio.ByteString;

public class BusActivity extends AppCompatActivity implements ShowInActivityHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);

        OkHttpUtil.getInstance().setShowInActivityHandler(this);

        Button sendHello = (Button) findViewById(R.id.bus_send);
        sendHello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpUtil.getInstance().getWs().send("Hello world");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        OkHttpUtil.getInstance().setShowInActivityHandler(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void showMessage(String text) {
        EventBus.getDefault().post(new MessageEvent(text));
    }

    @Override
    public void showImage(ByteString bytes) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        Toast.makeText(this, event.text, Toast.LENGTH_LONG).show();
    };


    private class MessageEvent {
        public String text;

        public MessageEvent(String text) {
            this.text = text;
        }
    }
}
