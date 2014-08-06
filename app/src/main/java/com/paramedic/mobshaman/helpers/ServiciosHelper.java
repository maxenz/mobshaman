package com.paramedic.mobshaman.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paramedic.mobshaman.models.Servicio;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by maxo on 24/07/14.
 */
public final class ServiciosHelper {

    private ServiciosHelper() {

    }

    public static ArrayList<Servicio> getArrayListServicioFromJSONArray(JSONArray jServicios) throws JSONException {

        ArrayList<Servicio> vServicios = new ArrayList<Servicio>();

        for (int i = 0; i < jServicios.length(); i++) {
            JSONObject jServicio = jServicios.getJSONObject(i);
            vServicios.add(getServicioFromJSONObject(jServicio));
        }

        return vServicios;
    }

    public static Servicio getServicioFromJSONObject(JSONObject jServicio) {

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        return  gson.fromJson(String.valueOf(jServicio),Servicio.class);

    }
}

