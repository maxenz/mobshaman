package com.paramedic.mobshaman.rest;

/**
 * Created by maxo on 05/08/14.
 */
import com.loopj.android.http.*;

public class ServiciosRestClient {

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(url, params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(url, params, responseHandler);
    }

}