package com.paramedic.mobshaman.application;

import android.app.Application;

import com.paramedic.mobshaman.activities.ServiciosActivity;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.PushService;

/**
 * Created by soporte on 21/07/2014.
 */
public class MobShamanApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this, "Yu5MsVhQi7ih2ltKlNrQcrpFfvRlexZnGiecJZHd", "oNEoeZZYe5JcfNwnSSBWGNc93uGxIkd5Kcl5gts4");


        
    }
}
