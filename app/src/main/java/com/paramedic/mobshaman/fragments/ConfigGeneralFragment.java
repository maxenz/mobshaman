package com.paramedic.mobshaman.fragments;

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
import com.parse.PushService;

import java.util.Set;

/**
 * Created by soporte on 28/07/2014.
 */
public class ConfigGeneralFragment extends Fragment {

    EditText etNroMovilRegistro;
    Button btnRegistrarMovil;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_config_general,container, false);

        etNroMovilRegistro = (EditText) myView.findViewById(R.id.et_movil_registro);

        btnRegistrarMovil = (Button) myView.findViewById(R.id.btn_registrar_movil);
        btnRegistrarMovil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    PushService.subscribe(getActivity(), etNroMovilRegistro.getText().toString(), ServiciosActivity.class);
                    Toast.makeText(getActivity(),"El movil se configuró correctamente.",Toast.LENGTH_LONG).show();
                    getActivity().finish();
                } catch (Exception e) {
                    Toast.makeText(getActivity(),"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

        return myView;

    }
}
