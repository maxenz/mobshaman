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

    public Servicio jsonToServicio(JSONObject obj, int tipConstructor) {

        Servicio serv = null;
        try {
            if (tipConstructor == 0) {
                serv = new Servicio(obj.getInt("IdServicio"), obj.getString("Cliente"),
                        obj.getString("Grado"), obj.getString("NroServicio"),
                        obj.getString("Domicilio"), obj.getString("Sexo"),
                        obj.getString("Edad"), obj.getString("Horario"));
            } else {
                serv = new Servicio(obj.getInt("IdServicio"), obj.getString("Cliente"),
                        obj.getString("Grado"), obj.getString("NroServicio"),
                        obj.getString("Domicilio"), obj.getString("Sexo"),
                        obj.getString("Edad"),obj.getString("FecIncidente"),
                        obj.getString("NroAfiliado"),obj.getString("Aviso"),
                        obj.getString("Localidad"), obj.getString("Partido"),
                        obj.getString("EntreCalle1"),obj.getString("EntreCalle2"),
                        obj.getString("Referencia"), obj.getString("Latitud"),
                        obj.getString("Longitud"),obj.getString("Sintomas"),
                        obj.getString("Paciente"),obj.getString("PlanId"),
                        obj.getDouble("CoPago"), obj.getString("Observaciones"));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return serv;
    }

    public ArrayList<Servicio> stringToListServicios(String result, int tipConstructor) {
        JSONArray respJSON;
        ArrayList<Servicio> servicios = new ArrayList<Servicio>();
        try {

            respJSON = new JSONArray(result);

            for (int i = 0; i < respJSON.length(); i++) {
                JSONObject obj = respJSON.getJSONObject(i);
                Servicio serv = jsonToServicio(obj,tipConstructor);
                servicios.add(serv);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return servicios;

    }
}

