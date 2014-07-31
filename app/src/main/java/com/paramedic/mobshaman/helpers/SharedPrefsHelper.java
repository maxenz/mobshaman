package com.paramedic.mobshaman.helpers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by maxo on 31/07/14.
 */
public class SharedPrefsHelper {

    public String getURLFromSharedPrefs(Context ctx) {

        SharedPreferences sp = ctx.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

        return sp.getString("urlREST","");

    }

    public String getNroMovilFromSharedPrefs(Context ctx) {
        SharedPreferences sp = ctx.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

        return sp.getString("nroMovil","");
    }

}
