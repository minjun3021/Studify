package com.kmj.studify;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkHelper {
    final static String url = "http://13.125.252.104";
    final static int port = 3030;
    //http://13.125.252.104:3030/
    //http://13.125.252.104:3030/

    private static Retrofit retrofit;


    public static NetworkInterface getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(url + ":" + port)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(NetworkInterface.class);
    }
}
