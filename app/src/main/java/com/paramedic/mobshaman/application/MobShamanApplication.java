package com.paramedic.mobshaman.application;

import android.app.Application;
import com.parse.Parse;

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
