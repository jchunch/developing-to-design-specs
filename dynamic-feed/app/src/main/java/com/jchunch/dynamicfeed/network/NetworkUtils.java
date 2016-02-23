package com.jchunch.dynamicfeed.network;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import retrofit2.Response;

/**
 * Created by jchunch on 2/22/16.
 */
public class NetworkUtils {

    public static String convertObjectToJsonString(Object objectToConvert) {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .serializeNulls();

        return gsonBuilder.create().toJson(objectToConvert);
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
