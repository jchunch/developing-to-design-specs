package com.jchunch.dynamicfeed.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;

import com.jchunch.dynamicfeed.log.LogUtil;

/**
 * Created by jchunch on 2/22/16.
 */
public class NetworkActivity extends AppCompatActivity {
    private static final String TAG = NetworkActivity.class.getName();

    private BroadcastReceiver mNetworkReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtil.d(TAG, "onReceive");

            String responseBodyJson = null;

            String action = intent.getAction();
            if (action != null && action.equals(NetworkService.ACTION)) {
                responseBodyJson = intent.getStringExtra(NetworkService.KEY_ARG_RESPONSE_BODY_JSON);
            }

            handleNetworkResponse(responseBodyJson);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(TAG, "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d(TAG, "onResume");

        // Register broadcast receiver
        IntentFilter intentFilter = new IntentFilter(NetworkService.ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mNetworkReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.d(TAG, "onPause");

        // Unregister broadcast receiver
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mNetworkReceiver);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtil.d(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d(TAG, "onDestroy");
    }

    protected void executeNetworkRequest(String endpoint) {
        Intent intent = NetworkService.newInstanceIntent(this, endpoint);
        startService(intent);
    }

    protected void handleNetworkResponse(String responseBodyObject) {
        // Override in sub-class
    }
}
