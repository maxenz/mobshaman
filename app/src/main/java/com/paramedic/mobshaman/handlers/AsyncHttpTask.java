package com.paramedic.mobshaman.handlers;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by maxo on 24/07/14.
 */
public class AsyncHttpTask extends AsyncTask<String, Void, String> {

    private HttpHandler httpHandler;
    public AsyncHttpTask(HttpHandler httpHandler){
        this.httpHandler = httpHandler;
    }

    @Override
    protected String doInBackground(String... arg0) {

        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make the http request
            HttpResponse httpResponse = httpclient.execute(httpHandler.getHttpRequestMethod());

            result = EntityUtils.toString(httpResponse.getEntity());


        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }
    @Override
    protected void onPostExecute(String result) {
        httpHandler.onResponse(result);
    }

}
