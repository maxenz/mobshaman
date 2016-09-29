package com.paramedic.mobshaman.models;

/**
 * Created by maxo on 26/9/16.
 */
public class LoginResponse {

    private String AndroidUrl;
    private boolean Error;
    private String Serial;

    public String getAndroidUrl() {
        return AndroidUrl;
    }

    public void setAndroidUrl(String androidUrl) {
        AndroidUrl = androidUrl;
    }

    public boolean getError() {
        return Error;
    }

    public void setError(boolean error) {
        Error = error;
    }

    public String getSerial() {
        return Serial;
    }

    public void setSerial(String serial) {
        Serial = serial;
    }
}
