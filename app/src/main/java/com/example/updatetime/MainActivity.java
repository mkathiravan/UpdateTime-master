package com.example.updatetime;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ExampleService.ExampleServiceUIListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private UpdateTimeAdapter mAdapter;
    ArrayList<String> updateTimeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        updateTimeList = new ArrayList<>();

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new UpdateTimeAdapter(MainActivity.this, updateTimeList);
        mRecyclerView.setAdapter(mAdapter);

        startExampleService();
        ExampleService.getInstance(this);


        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }


    private void startExampleService() {

        Intent serviceIntent = new Intent(this, ExampleService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent);
        } else {
            startService(serviceIntent);
        }
    }

    @Override
    public void updateListener(String data) {
        updateTimeList.add(data);
        if (mAdapter != null)
        Log.d(TAG,"UPDATETEAMSIE "+updateTimeList.size());
         mAdapter = new UpdateTimeAdapter(MainActivity.this, updateTimeList);
         mRecyclerView.setAdapter(mAdapter);
         mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();

        }

        @Override
    protected void onStop() {
        super.onStop();

    }



}
