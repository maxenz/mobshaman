package com.paramedic.mobshaman.application;

import android.app.Application;
import android.content.Context;

import com.onesignal.OneSignal;

/**
 * Created by soporte on 21/07/2014.
 */
public class MobShamanApplication extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.startInit(this).init();
        MobShamanApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MobShamanApplication.context;
    }
}
