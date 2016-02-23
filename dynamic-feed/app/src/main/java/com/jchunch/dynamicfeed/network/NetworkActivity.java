package com.jchunch.dynamicfeed.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.jchunch.dynamicfeed.log.LogUtil;

/**
 * Created by jchunch on 2/22/16.
 */
public class NetworkActivity extends AppCompatActivity {
    private static final String TAG = NetworkActivity.class.getName();
    private static final String KEY_ARG_RESPONSE_BODY_JSON = "KEY_ARG_RESPONSE_BODY_JSON";

    protected String mResponseBodyJson;
    protected String mEndpoint;

    private BroadcastReceiver mNetworkReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtil.d(TAG, "onReceive");

            // Get response body object
            String action = intent.getAction();
            if (action != null && action.equals(NetworkService.ACTION)) {
                mResponseBodyJson = intent.getStringExtra(NetworkService.KEY_ARG_RESPONSE_BODY_JSON);
            }

            // Handle network response
            handleNetworkResponse();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(TAG, "onCreate");

        // Init endpoint
        initEndpoint();

        // Attempt to restore response body json
        if (savedInstanceState != null) {
            mResponseBodyJson = savedInstanceState.getString(KEY_ARG_RESPONSE_BODY_JSON);
        } else {
            mResponseBodyJson = null;
        }
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

        // Handle response body json if available, otherwise execute network request
        if (!TextUtils.isEmpty(mResponseBodyJson)) {
            handleNetworkResponse();
        } else {
            executeNetworkRequest();
        }
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

        // Save response body json
        outState.putString(KEY_ARG_RESPONSE_BODY_JSON, mResponseBodyJson);
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

    protected void executeNetworkRequest() {
        Intent intent = NetworkService.newInstanceIntent(this, mEndpoint);
        startService(intent);
    }

    protected void handleNetworkResponse() {
        // Override in sub-class
    }

    protected void initEndpoint() {
        // Override in sub-class
    }
}
