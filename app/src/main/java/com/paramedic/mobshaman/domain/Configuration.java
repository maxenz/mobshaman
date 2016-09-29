package com.paramedic.mobshaman.domain;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by maxo on 17/05/15.
 */
public class Configuration {

    //region Properties

    private static Configuration instance;
    private static String url;
    private static String mobile;
    private static String license;
    private static boolean requestReportNumber;
    private static String gestionUrl;

    //endregion

    //region Constructors

    private Configuration(Context context) {

        SharedPreferences sp = context.getSharedPreferences("configuration", Context.MODE_PRIVATE);
        this.url = sp.getString("url","");
        this.mobile = sp.getString("mobile","");
        this.license = sp.getString("license","");
        this.requestReportNumber = sp.getBoolean("requestReportNumber",false);
        this.gestionUrl = "http://paramedicapps.com.ar:57771/";

    }

    //endregion

    //region Getters and Setters

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public boolean isRequestReportNumber() {
        return requestReportNumber;
    }

    public void setRequestReportNumber(boolean requestReportNumber) {
        this.requestReportNumber = requestReportNumber;
    }

    public String getGestionUrl() { return gestionUrl;}

    //endregion

    //region Public Methods

    public static Configuration getInstance(Context context) {

        if (instance == null) {
            instance = new Configuration(context);
        }

        return instance;
    }

    public static boolean IsValid() {

        if (mobile == "") return false;
        if (license == "") return false;
        if (url == "") return false;

        return true;

    }

    //endregion

}
