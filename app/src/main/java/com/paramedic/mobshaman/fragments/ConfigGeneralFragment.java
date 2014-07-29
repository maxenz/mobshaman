package com.paramedic.mobshaman.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.paramedic.mobshaman.R;
import com.paramedic.mobshaman.activities.ServiciosActivity;
import com.parse.ParseInstallation;
import com.parse.PushService;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by soporte on 28/07/2014.
 */
public class ConfigGeneralFragment extends Fragment {

    EditText etNroMovilRegistro, etUrlregistro;
    Button btnRegistrarMovil;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_config_general,container, false);

        etNroMovilRegistro = (EditText) myView.findViewById(R.id.et_movil_registro);
        etUrlregistro = (EditText) myView.findViewById(R.id.et_url_registro);
        btnRegistrarMovil = (Button) myView.findViewById(R.id.btn_registrar_movil);

        btnRegistrarMovil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String nroMov = etNroMovilRegistro.getText().toString();
                String urlREST = etUrlregistro.getText().toString();

                if (nroMov.equals("") || urlREST.equals("")) {

                    Toast.makeText(getActivity(),"Debe ingresar nro de movil y url",Toast.LENGTH_LONG).show();

                } else {

                    try {

                        SharedPreferences prefs = getActivity().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("urlREST", urlREST);
                        editor.putString("nroMovil", nroMov);
                        editor.commit();

                        JSONArray vInstalacion = ParseInstallation.getCurrentInstallation().getJSONArray("channels");

                        if (vInstalacion != null) {
                            for(int i = 0; i < vInstalacion.length(); i++) {
                                PushService.unsubscribe(getActivity(),vInstalacion.getString(i));
                            }
                        }
                        PushService.subscribe(getActivity(),
                                "m"+etNroMovilRegistro.getText().toString(), ServiciosActivity.class);
                        Toast.makeText(getActivity(),"El movil se configuró correctamente.",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getActivity(),ServiciosActivity.class));
                    } catch (Exception e) {
                        Toast.makeText(getActivity(),"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

        return myView;

    }
}
