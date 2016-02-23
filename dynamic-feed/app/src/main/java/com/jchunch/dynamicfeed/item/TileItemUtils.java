package com.jchunch.dynamicfeed.item;

import android.text.TextUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jchunch.dynamicfeed.item.large.LargeTileItem;
import com.jchunch.dynamicfeed.item.regular.RegularTileItem;
import com.jchunch.dynamicfeed.item.small.SmallTileItem;
import com.jchunch.dynamicfeed.log.LogUtil;
import com.jchunch.dynamicfeed.model.LargeTile;
import com.jchunch.dynamicfeed.model.RegularTile;
import com.jchunch.dynamicfeed.model.SmallTile;
import com.jchunch.dynamicfeed.network.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jchunch on 2/22/16.
 */
public class TileItemUtils {
    private static final String TAG = TileItemUtils.class.getName();

    private static final String KEY_VALUE_BODY = "body";
    private static final String KEY_VALUE_FEED = "feed";
    private static final String KEY_VALUE_HEADER = "header";
    private static final String KEY_VALUE_IMAGE_URL = "imageUrl";
    private static final String KEY_VALUE_TYPE = "type";

    private static final String TYPE_VALUE_LARGE = "large";
    private static final String TYPE_VALUE_REGULAR = "regular";
    private static final String TYPE_VALUE_SMALL = "small";

    public static LargeTileItem buildLargeTileItem(JsonObject jsonObject) {
        String jsonString = NetworkUtils.convertObjectToJsonString(jsonObject);
        LargeTile largeTile = NetworkUtils.fromJson(jsonString, LargeTile.class);
        return new LargeTileItem(largeTile);
    }

    public static RegularTileItem buildRegularTileItem(JsonObject jsonObject) {
        String jsonString = NetworkUtils.convertObjectToJsonString(jsonObject);
        RegularTile regularTile = NetworkUtils.fromJson(jsonString, RegularTile.class);
        return new RegularTileItem(regularTile);
    }

    public static SmallTileItem buildSmallTileItem(JsonObject jsonObject) {
        String jsonString = NetworkUtils.convertObjectToJsonString(jsonObject);
        SmallTile smallTile = NetworkUtils.fromJson(jsonString, SmallTile.class);
        return new SmallTileItem(smallTile);
    }

    public static List<TileItem> getTileItemsFromResponseBodyJson(String jsonString) {
        List<TileItem> tileItems = new ArrayList<TileItem>();

        if (!TextUtils.isEmpty(jsonString)) {
            JsonObject jsonObject = NetworkUtils.fromJson(jsonString, JsonObject.class);
            if (jsonObject != null) {
                JsonArray jsonArray = jsonObject.getAsJsonArray(KEY_VALUE_FEED);
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JsonElement jsonElement = jsonArray.get(i);
                        if (jsonElement != null) {

                            // Get tile and type
                            JsonObject tile = jsonElement.getAsJsonObject();
                            JsonElement type = tile.get(KEY_VALUE_TYPE);

                            // Build tile and add to list
                            switch (type != null ? type.getAsString() : "") {
                                case TYPE_VALUE_LARGE:
                                    LargeTileItem largeTileItem = buildLargeTileItem(tile);
                                    tileItems.add(largeTileItem);
                                    break;

                                case TYPE_VALUE_REGULAR:
                                    RegularTileItem regularTileItem = buildRegularTileItem(tile);
                                    tileItems.add(regularTileItem);
                                    break;

                                case TYPE_VALUE_SMALL:
                                    SmallTileItem smallTileItem = buildSmallTileItem(tile);
                                    tileItems.add(smallTileItem);
                                    break;

                                default:
                                    LogUtil.w(TAG, "getTileItemsFromResponseBodyJson: unknown tile type");
                                    break;
                            }
                        }
                    }
                }
            }
        }

        return tileItems;
    }
}
