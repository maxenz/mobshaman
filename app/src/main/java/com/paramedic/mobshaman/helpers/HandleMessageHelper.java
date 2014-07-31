package com.paramedic.mobshaman.helpers;

/**
 * Created by maxo on 31/07/14.
 */
public class HandleMessageHelper {

    public String getCodeResponse(String response) {

        return response.split("&&")[0];

    }

    public String getMessageResponse(String response) {

        return response.split("&&")[1];
    }


}
