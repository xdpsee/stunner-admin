package com.cherry.stunner.spider.utils;

import okhttp3.OkHttpClient;
import okhttp3.Response;

import java.io.IOException;

public class HttpUtils {

    public static final String MORE_ALBUMS_REQUEST_FORMAT = "http://www.17786.com/Process/handler.ashx?action=tagslist&keyword=%s&page=%d&rnd=%f";

    private final OkHttpClient client = new OkHttpClient();

    public String get(String url) throws IOException {

        okhttp3.Request request = new okhttp3.Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        }

        throw new IOException("failure");
    }

}
