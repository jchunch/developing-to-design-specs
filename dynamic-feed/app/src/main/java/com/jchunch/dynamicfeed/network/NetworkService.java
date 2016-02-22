package com.jchunch.dynamicfeed.network;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.gson.JsonElement;
import com.jchunch.dynamicfeed.BuildConfig;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by jchunch on 2/22/16.
 */
public class NetworkService extends IntentService {
    private static final String TAG = NetworkService.class.getName();

    public NetworkService() {
        super(TAG);
    }

    // TODO: Pass in endpoint

    @Override
    protected void onHandleIntent(Intent intent) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onHandleIntent");
        }

        try {
            Response<JsonElement> response = null;
            response = RestClient.get()
                    .getData(Endpoints.ENDPOINT_VALUE_DYNAMIC_FEED)
                    .execute();

            if (response != null) {
                NetworkUtils.getTileItemsFromResponse(response);
            }
        } catch (IOException e) {
            if (BuildConfig.DEBUG) {
                Log.w(TAG, "onHandleIntent: " + e.getMessage());
            }
        }
    }
}
