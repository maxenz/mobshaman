package com.paramedic.mobshaman.rest;

/**
 * Created by maxo on 05/08/14.
 * Esta clase la uso para llamar asincronicamente a los servicios rest, dependiendo si hago
 * un get o un post. Uso la libreria loopj para facilitar el manejo.
 */
import android.content.Context;

import com.loopj.android.http.*;

import org.apache.http.entity.StringEntity;

public class ServiciosRestClient {

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(url, params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(url, params, responseHandler);
    }

    public static void post(Context context, String url, StringEntity entity, String contentType, AsyncHttpResponseHandler responseHandler) {
        client.post(context, url, entity, contentType, responseHandler);
    }

}