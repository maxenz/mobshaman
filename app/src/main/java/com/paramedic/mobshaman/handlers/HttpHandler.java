package com.paramedic.mobshaman.handlers;

import org.apache.http.client.methods.HttpUriRequest;

/**
 * Created by maxo on 24/07/14.
 */
public abstract class HttpHandler {

    public abstract HttpUriRequest getHttpRequestMethod();

    public abstract void onResponse(String result);

    public void execute(){
        new AsyncHttpTask(this).execute();
    }
}
