package com.paramedic.mobshaman.application;

import android.app.Application;
import com.onesignal.OneSignal;

/**
 * Created by soporte on 21/07/2014.
 */
public class MobShamanApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.startInit(this).init();
    }
}
