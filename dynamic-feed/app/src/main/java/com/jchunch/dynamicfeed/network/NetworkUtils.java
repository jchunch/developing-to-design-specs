package com.jchunch.dynamicfeed.network;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jchunch.dynamicfeed.BuildConfig;
import com.jchunch.dynamicfeed.item.TileItem;
import com.jchunch.dynamicfeed.item.large.LargeTileItem;
import com.jchunch.dynamicfeed.item.regular.RegularTileItem;
import com.jchunch.dynamicfeed.item.small.SmallTileItem;
import com.jchunch.dynamicfeed.model.LargeTile;
import com.jchunch.dynamicfeed.model.RegularTile;
import com.jchunch.dynamicfeed.model.SmallTile;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * Created by jchunch on 2/22/16.
 */
public class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getName();

    private static final String KEY_VALUE_BODY = "body";
    private static final String KEY_VALUE_FEED = "feed";
    private static final String KEY_VALUE_HEADER = "header";
    private static final String KEY_VALUE_IMAGE_URL = "imageUrl";
    private static final String KEY_VALUE_TYPE = "type";
    private static final String TYPE_VALUE_LARGE = "large";
    private static final String TYPE_VALUE_REGULAR = "regular";
    private static final String TYPE_VALUE_SMALL = "small";

    public static LargeTileItem buildLargeTileItem(JsonObject jsonObject) {

        String header = "";
        String body = "";
        String imageUrl = "";

        if (jsonObject != null) {
            header = jsonObject.get(KEY_VALUE_HEADER).getAsString();
            body = jsonObject.get(KEY_VALUE_BODY).getAsString();
            imageUrl = jsonObject.get(KEY_VALUE_IMAGE_URL).getAsString();
        }

        LargeTile largeTile = new LargeTile(header, body, imageUrl);

        return new LargeTileItem(largeTile);
    }

    public static RegularTileItem buildRegularTileItem(JsonObject jsonObject) {

        String header = "";
        String body = "";
        String imageUrl = "";

        if (jsonObject != null) {
            header = jsonObject.get(KEY_VALUE_HEADER).getAsString();
            body = jsonObject.get(KEY_VALUE_BODY).getAsString();
            imageUrl = jsonObject.get(KEY_VALUE_IMAGE_URL).getAsString();
        }

        RegularTile regularTile = new RegularTile(header, body, imageUrl);

        return new RegularTileItem(regularTile);
    }

    public static SmallTileItem buildSmallTileItem(JsonObject jsonObject) {

        String header = "";
        String body = "";
        String imageUrl = "";

        if (jsonObject != null) {
            header = jsonObject.get(KEY_VALUE_HEADER).getAsString();
            body = jsonObject.get(KEY_VALUE_BODY).getAsString();
            imageUrl = jsonObject.get(KEY_VALUE_IMAGE_URL).getAsString();
        }

        SmallTile smallTile = new SmallTile(header, body, imageUrl);

        return new SmallTileItem(smallTile);
    }

    public static List<TileItem> getTileItemsFromResponse(Response<JsonElement> response) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "getTileItemsFromResponse");
        }

        List<TileItem> tileItems = new ArrayList<TileItem>();

        // TODO: Clean this up
        if (response != null) {
            JsonElement responseBody = response.body();
            if (responseBody != null) {
                JsonObject jsonObject = responseBody.getAsJsonObject();
                if (jsonObject != null) {
                    JsonArray jsonArray = jsonObject.getAsJsonArray(KEY_VALUE_FEED);
                    if (jsonArray != null) {
                        for (int i = 0; i < jsonArray.size(); i++) {
                            JsonElement jsonElement = jsonArray.get(i);
                            if (jsonElement != null) {
                                JsonObject obj = jsonElement.getAsJsonObject();
                                String type = obj.get(KEY_VALUE_TYPE).getAsString();
                                switch (type) {
                                    case TYPE_VALUE_LARGE:
                                        LargeTileItem largeTileItem = buildLargeTileItem(obj);
                                        tileItems.add(largeTileItem);
                                        break;

                                    case TYPE_VALUE_REGULAR:
                                        RegularTileItem regularTileItem = buildRegularTileItem(obj);
                                        tileItems.add(regularTileItem);
                                        break;

                                    case TYPE_VALUE_SMALL:
                                        SmallTileItem smallTileItem = buildSmallTileItem(obj);
                                        tileItems.add(smallTileItem);
                                        break;

                                    default:
                                        if (BuildConfig.DEBUG) {
                                            Log.w(TAG, "getTileItemsFromResponse: unknown type");
                                        }
                                        break;
                                }
                            }
                        }
                    }
                }
            }
        }

        return tileItems;
    }
}
