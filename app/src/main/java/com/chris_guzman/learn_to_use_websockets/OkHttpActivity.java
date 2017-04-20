package com.chris_guzman.learn_to_use_websockets;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

import okhttp3.WebSocket;
import okio.ByteString;

public class OkHttpActivity extends AppCompatActivity implements ShowInActivityHandler {
    private static final String TAG = "WS";
    private TextView echoTxt;
    private EditText editTxt;
    private Button sendBtn;
    private Button nextActBtn;
    private ImageView iv;
    private Button sendImageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);

        OkHttpUtil.getInstance().setShowInActivityHandler(this);

        echoTxt = (TextView) findViewById(R.id.echo_text);
        editTxt = (EditText) findViewById(R.id.edit_text);
        iv = (ImageView) findViewById(R.id.iv);

        sendBtn = (Button) findViewById(R.id.send_btn);
        nextActBtn = (Button) findViewById(R.id.next_act);
        sendImageBtn = (Button) findViewById(R.id.send_img);


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

        sendImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable dr = getDrawable(R.mipmap.ic_quack);
                if (dr != null) {
                    Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] bitmapdata = stream.toByteArray();
                    OkHttpUtil.getInstance().getWs().send(ByteString.of(bitmapdata));
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        OkHttpUtil.getInstance().setShowInActivityHandler(this);
    }

    private void sendToTheVoid() {
        OkHttpUtil.getInstance().getWs().send(editTxt.getText().toString());
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
        Log.d(TAG, "OkHttpActivity onMessageReceived: " + text);
    }

    @Override
    public void showImage(ByteString bytes) {
        String strBase64 = bytes.base64();
        byte[] decodedString = Base64.decode(strBase64, Base64.DEFAULT);
        final Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                iv.setImageBitmap(decodedByte);
            }
        });
    }
}
