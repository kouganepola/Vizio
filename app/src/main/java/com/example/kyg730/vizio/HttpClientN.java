package com.example.kyg730.vizio;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Koumudi on 01/05/2018.
 */

public class HttpClientN {
    private static final String BASE_URL = "http://a9cf2ba1.ngrok.io/";

    private static OkHttpClient client = new OkHttpClient();

    public static String doGetRequest(String url) throws IOException {
        Request request = new Request.Builder()
                .url(getAbsoluteUrl(url))
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String doGetRequest(String url, String[][] params) throws IOException {

        HttpUrl.Builder urlBuilder = HttpUrl.parse(getAbsoluteUrl(url)).newBuilder();
        for (String[] param:params) {
            urlBuilder.addQueryParameter(param[0], param[1]);

        }
        String newurl = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(newurl)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }




    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }


}
