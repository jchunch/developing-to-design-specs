package com.jchunch.dynamicfeed.network;

import android.app.IntentService;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.gson.JsonElement;
import com.jchunch.dynamicfeed.BuildConfig;
import com.jchunch.dynamicfeed.item.TileItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * Created by jchunch on 2/22/16.
 */
public class FeedNetworkService extends IntentService {
    private static final String TAG = FeedNetworkService.class.getName();

    public static final String ACTION = "com.jchunch.dynamicfeed.action.NETWORK_SERVICE_UPDATE";

    public static final String KEY_ARG_SUCCESS = "KEY_ARG_SUCCESS";
    public static final String KEY_ARG_TILE_ITEMS = "KEY_ARG_TILE_ITEMS";

    public FeedNetworkService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onHandleIntent");
        }

        try {

            // Request the dynamic feed
            Response<JsonElement> response = RestClient.get()
                    .getData(Endpoints.ENDPOINT_VALUE_DYNAMIC_FEED)
                    .execute();

            // Convert response into tile items
            List<TileItem> tileItems = NetworkUtils.getTileItemsFromResponse(response);

            // Broadcast success
            Intent broadcastIntent = new Intent(ACTION);
            broadcastIntent.putExtra(KEY_ARG_SUCCESS, true);
            broadcastIntent.putParcelableArrayListExtra(
                    KEY_ARG_TILE_ITEMS, (ArrayList<? extends Parcelable>) tileItems);

            LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);

        } catch (IOException e) {
            if (BuildConfig.DEBUG) {
                Log.w(TAG, "onHandleIntent: " + e.getMessage());
            }

            // Broadcast error
            Intent broadcastIntent = new Intent(ACTION);
            broadcastIntent.putExtra(KEY_ARG_SUCCESS, false);

            LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
        }
    }
}
