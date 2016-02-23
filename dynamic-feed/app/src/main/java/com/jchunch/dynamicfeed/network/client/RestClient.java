package com.jchunch.dynamicfeed.network.client;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by jchunch on 2/22/16.
 */
public class RestClient {
    private final static String BASE_URL = "http://private-94acb-dynamicfeed.apiary-mock.com";

    private static Api REST_CLIENT;

    static {
        setupRestClient();
    }

    private RestClient() {

    }

    public static Api get() {
        return REST_CLIENT;
    }

    private static void setupRestClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        REST_CLIENT = retrofit.create(Api.class);
    }
}
