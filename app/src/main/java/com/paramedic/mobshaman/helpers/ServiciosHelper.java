package com.paramedic.mobshaman.helpers;

import com.paramedic.mobshaman.models.Servicio;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by maxo on 24/07/14.
 */
public class ServiciosHelper {

    public Servicio jsonToServicio(JSONObject obj) {

        Servicio serv = null;
        try {
            serv = new Servicio(obj.getInt("IdServicio"), obj.getString("Cliente"),
                    obj.getInt("Grado"), obj.getString("NroServicio"),
                    obj.getString("Domicilio"), obj.getString("Sexo"),
                    obj.getString("Edad"), obj.getString("Horario"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return serv;
    }

    public ArrayList<Servicio> stringToListServicios(String result) {
        JSONArray respJSON;
        ArrayList<Servicio> servicios = new ArrayList<Servicio>();
        try {

            respJSON = new JSONArray(result);

            for (int i = 0; i < respJSON.length(); i++) {
                JSONObject obj = respJSON.getJSONObject(i);
                Servicio serv = jsonToServicio(obj);
                servicios.add(serv);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return servicios;

    }
}

