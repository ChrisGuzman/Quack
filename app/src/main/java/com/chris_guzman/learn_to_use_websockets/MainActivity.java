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
    private Button button;
    private TextView textView;
    private OkHttpClient client;
    private TextView echoTxt;
    private EditText editTxt;
    private Button sendBtn;
    private WebSocket ws;

    private final class EchoWebSocketListener extends WebSocketListener {
        private static final int NORMAL_CLOSURE_STATUS = 1000;

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
//            webSocket.send("Hello?");
//            webSocket.send("Is anyone there?");
//            //When would I send bytes?
//            webSocket.send(ByteString.decodeHex("deadbeef"));
//            //Do I need to close the websocket?
//            /*
//            1000 = normal closure
//            1001 = endpoint is going away, server is closing or client is navigating away
//            1002 = something was closed due to error
//            1003 = endpoint recieved data type it can't accept
//             */
//            webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye!");
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
//            output("Receiving String: " + text);
            updateEchoTxt(text);
        }


        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            /*
            It looks like I can use a magic number https://en.wikipedia.org/wiki/Magic_number_%28programming%29
            to know what kind of file the image is.
            */
            output("Receiving Bytes: " + bytes.hex());
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            output("Closing: " + code + " / " + reason);
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            output("Closed for business! " + code + " / " + reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            output("Error: " + t.getMessage());
        }
    }

    private void updateEchoTxt(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String prevText = echoTxt.getText().toString();
                echoTxt.setText( prevText + "\n" + text);
            }
        });
    }

    private void output(final String txt) {
        //what's a better way to do this than to runOnUIThread?
        /*
        the WebSocket/OkHttp is on a background thread
        this is just taking the output and putting it on the main thread to do UI stuff
         */
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(textView.getText().toString() + "\n\n" + txt);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.websocket_text);
        button = (Button) findViewById(R.id.start);
        echoTxt = (TextView) findViewById(R.id.echo_text);
        editTxt = (EditText) findViewById(R.id.edit_text);
        sendBtn = (Button) findViewById(R.id.send_btn);
        client = new OkHttpClient();

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startWebSocket();
//            }
//        });
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
        //Why isn't this used? What can I do with it?
        ws = client.newWebSocket(request, listener);

        // Trigger shutdown of the dispatcher's executor so this process can exit cleanly.
        //what if I don't shut down correctly?
        client.dispatcher().executorService().shutdown();
    }
}
