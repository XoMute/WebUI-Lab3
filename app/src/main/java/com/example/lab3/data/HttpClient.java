package com.example.lab3.data;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

public class HttpClient {
    private static final OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    public static String host = "192.168.1.103";

    public static OkHttpClient getClient() {
        return client;
    }
}
