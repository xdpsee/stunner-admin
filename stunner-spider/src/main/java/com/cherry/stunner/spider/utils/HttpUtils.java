package com.cherry.stunner.spider.utils;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.springframework.util.StringUtils;

import java.io.IOException;

public class HttpUtils {

    private final OkHttpClient client = new OkHttpClient();

    public String get(String url) throws IOException {

        okhttp3.Request request = new okhttp3.Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String result = response.body().string();
            if (StringUtils.isEmpty(result)) {
                System.out.println(result);
            }

            return result;
        }

        throw new IOException("failure");
    }

}
