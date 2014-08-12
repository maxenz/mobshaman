package com.paramedic.mobshaman.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paramedic.mobshaman.models.HistoriaClinica;
import com.paramedic.mobshaman.models.Servicio;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by soporte on 11/08/2014.
 */
public class HistoriaClinicaHelper {

    private HistoriaClinicaHelper() {

    }

    public static ArrayList<HistoriaClinica> getArrayListFromJSONArray(JSONArray jArray) throws JSONException {

        ArrayList<HistoriaClinica> vServicios = new ArrayList<HistoriaClinica>();

        for (int i = 0; i < jArray.length(); i++) {
            JSONObject jObj = jArray.getJSONObject(i);
            vServicios.add(getFromJSONObject(jObj));
        }

        return vServicios;
    }

    public static HistoriaClinica getFromJSONObject(JSONObject jArray) {

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        return  gson.fromJson(String.valueOf(jArray),HistoriaClinica.class);

    }
}
