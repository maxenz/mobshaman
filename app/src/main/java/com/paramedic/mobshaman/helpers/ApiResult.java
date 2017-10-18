package com.paramedic.mobshaman.helpers;

/**
 * Created by Maxo on 17/10/2017.
 */

public class ApiResult {
    private int Code;
    private String Message;

    public ApiResult(int code, String message) {
        this.Code = code;
        this.Message = message;
    }

    public int getCode() {
        return this.Code;
    }

    public void setCode(int code) {
        this.Code = code;
    }

    public String getMessage() {
        return this.Message;
    }

    public void setMessage(String message) {
        this.Message = message;
    }
}
