package com.jchunch.dynamicfeed.network;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

import com.google.gson.JsonElement;
import com.jchunch.dynamicfeed.log.LogUtil;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by jchunch on 2/22/16.
 */
public class NetworkService extends IntentService {
    private static final String TAG = NetworkService.class.getName();
    private static final String KEY_ARG_ENDPOINT = "KEY_ARG_ENDPOINT";

    public static final String ACTION = "ACTION_NETWORK_SERVICE";
    public static final String KEY_ARG_RESPONSE_BODY_JSON = "KEY_ARG_RESPONSE_BODY_JSON";

    public static Intent newInstanceIntent(Context context, String endpoint) {
        LogUtil.d(TAG, "newInstanceIntent: " + endpoint);

        Intent intent = new Intent(context, NetworkService.class);

        // Add the endpoint
        intent.putExtra(KEY_ARG_ENDPOINT, endpoint);

        return intent;
    }

    public NetworkService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        LogUtil.d(TAG, "onHandleIntent");

        String responseBodyJson = null;

        // Get the endpoint
        String endpoint = intent.getStringExtra(KEY_ARG_ENDPOINT);
        if (!TextUtils.isEmpty(endpoint)) {

            try {

                // Request the dynamic feed
                Response<JsonElement> response = RestClient.get()
                        .getData(endpoint)
                        .execute();

                // Convert response into tile items
                responseBodyJson = NetworkUtils.getResponseBodyJson(response);

            } catch (IOException e) {
                LogUtil.w(TAG, e.getMessage());
            }
        }

        // Broadcast result
        Intent broadcastIntent = new Intent(ACTION);
        broadcastIntent.putExtra(KEY_ARG_RESPONSE_BODY_JSON, responseBodyJson);
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
    }
}
