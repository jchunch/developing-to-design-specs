package com.jchunch.dynamicfeed.network;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.jchunch.dynamicfeed.log.LogUtil;

import retrofit2.Response;

/**
 * Created by jchunch on 2/22/16.
 */
public class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getName();

    public static String convertObjectToJsonString(Object objectToConvert) {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .serializeNulls();

        return gsonBuilder.create().toJson(objectToConvert);
    }

    public static <T> T fromJson(String jsonString, Class<T> classType) {
        if (jsonString != null) {
            try {
                return new Gson().fromJson(jsonString, classType);
            } catch (JsonSyntaxException e) {
                LogUtil.w(TAG, "fromJson: " + e.getMessage());
            }
        }

        return null;
    }

    public static String getResponseBodyJson(Response<JsonElement> response) {
        String responseBodyJson = null;

        if (response != null) {
            JsonElement jsonElement = response.body();
            if (jsonElement != null) {
                responseBodyJson = convertObjectToJsonString(jsonElement);
            }
        }

        return responseBodyJson;
    }
}
