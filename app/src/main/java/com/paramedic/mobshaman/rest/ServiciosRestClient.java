package com.paramedic.mobshaman.rest;

/**
 * Created by maxo on 05/08/14.
 * Esta clase la uso para llamar asincronicamente a los servicios rest, dependiendo si hago
 * un get o un post. Uso la libreria loopj para facilitar el manejo.
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