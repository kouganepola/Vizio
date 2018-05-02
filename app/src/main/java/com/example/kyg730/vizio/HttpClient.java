package com.example.kyg730.vizio;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.HttpEntity;

/**
 * Created by Koumudi on 18/04/2018.
 */

public class HttpClient {
    private static final String BASE_URL = "http://5a2739ae.ngrok.io/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, TextHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), responseHandler);

    }

    public static void get(String url, RequestParams params, TextHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);

    }

    public static void post(String url, RequestParams params, TextHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);

    }

    public static void postJson(Context context, String url, HttpEntity entity, TextHttpResponseHandler responseHandler) {
        client.post(context,getAbsoluteUrl(url),entity,"application/json",responseHandler);
    }


    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

}
