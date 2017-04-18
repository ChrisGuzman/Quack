package com.chris_guzman.learn_to_use_websockets;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pusher.client.Pusher;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionStateChange;

import java.util.ArrayList;
import java.util.List;

public class PusherActivity extends AppCompatActivity {

    private static final String TAG = PusherActivity.class.getSimpleName();
    private RecyclerView rv;
    private LinearLayoutManager layoutManager;
    private PusherAdapter rvAdapter;
    private List<String> dataset = new ArrayList<>();
    private Pusher pusher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pusher);

        rv = (RecyclerView) findViewById(R.id.pusher_rv);
        rv.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        rvAdapter = new PusherAdapter(dataset);
        rv.setAdapter(rvAdapter);

        pusher = new Pusher("50ed18dd967b455393ed");

        Channel channel = pusher.subscribe("pics");
        channel.bind("new-listing", new SubscriptionEventListener() {
            @Override
            public void onEvent(String s, String s1, String s2) {
                final String combinedString = s + " " + s1 + " " + s2;
                Log.d(TAG, combinedString);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dataset.add(0, combinedString);
                        rvAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

        pusher.connect(new ConnectionEventListener() {
            @Override
            public void onConnectionStateChange(ConnectionStateChange change) {
                Log.d(TAG, "onConnectionStateChange: State changed to " + change.getCurrentState() +
                        " from " + change.getPreviousState());
            }

            @Override
            public void onError(String message, String code, Exception e) {
                Log.d(TAG, "onError: There was a problem connecting!");
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        pusher.disconnect();
    }

    public class PusherAdapter extends RecyclerView.Adapter<ViewHolder> {
        private List<String> dataset;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.pubnub_tv, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.textView.setText(dataset.get(position));
        }

        @Override
        public int getItemCount() {
            return dataset.size();
        }

        public PusherAdapter(List<String> dataset) {
            this.dataset = dataset;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(TextView itemView) {
            super(itemView);
            textView = itemView;
        }
    }
}
