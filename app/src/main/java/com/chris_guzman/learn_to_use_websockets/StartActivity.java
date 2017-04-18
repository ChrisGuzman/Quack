package com.chris_guzman.learn_to_use_websockets;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button okhttp = (Button) findViewById(R.id.okhttp);
        Button pubnub = (Button) findViewById(R.id.pubnub);
        Button pusher = (Button) findViewById(R.id.pusher);
        Button koush = (Button) findViewById(R.id.koush);
        Button nate = (Button) findViewById(R.id.nate);

        okhttp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, OkHttpActivity.class));
            }
        });

        pubnub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, PubNubActivity.class));
            }
        });

        pusher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, PusherActivity.class));
            }
        });

        koush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, KoushActivity.class));
            }
        });

        nate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, NateActivity.class));
            }
        });


    }
}
