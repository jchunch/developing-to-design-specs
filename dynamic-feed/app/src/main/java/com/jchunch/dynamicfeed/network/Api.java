package com.jchunch.dynamicfeed.network;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by jchunch on 2/22/16.
 */
public interface Api {

        @GET("{endpoint}")
        Call<JsonElement> getData(@Path("endpoint") String endpoint);
}
