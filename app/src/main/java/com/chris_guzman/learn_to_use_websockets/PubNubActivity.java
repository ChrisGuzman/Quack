package com.chris_guzman.learn_to_use_websockets;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PubNubActivity extends AppCompatActivity {

    private RecyclerView rv;
    private LinearLayoutManager layoutManager;
    private PubNubAdapter rvAdapter;
    private List<String> dataset = new ArrayList<>();
    private PubNub pubNub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub_nub);

        
        rv = (RecyclerView) findViewById(R.id.pubnub_rv);
        rv.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        rvAdapter = new PubNubAdapter(dataset);
        rv.setAdapter(rvAdapter);



        PNConfiguration pnConfiguration = new PNConfiguration();
        pnConfiguration.setSubscribeKey("sub-c-b0d14910-0601-11e4-b703-02ee2ddab7fe");
        pubNub = new PubNub(pnConfiguration);

        pubNub.addListener(new SubscribeCallback() {
            @Override
            public void status(PubNub pubnub, PNStatus status) {

            }

            @Override
            public void message(PubNub pubnub, PNMessageResult message) {
                if (message.getChannel() != null && message.getChannel().equals("pubnub-wikipedia")) {
                    dataset.add(0, message.toString());
                    Log.d(PubNubActivity.class.getSimpleName(), message.toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            rvAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }

            @Override
            public void presence(PubNub pubnub, PNPresenceEventResult presence) {

            }
        });

        pubNub.subscribe().channels(Arrays.asList("pubnub-wikipedia")).execute();


    }

    @Override
    protected void onStop() {
        super.onStop();
        pubNub.disconnect();
    }

    public class PubNubAdapter extends RecyclerView.Adapter<ViewHolder> {
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

        public PubNubAdapter(List<String> dataset) {
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
