package com.paramedic.mobshaman.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.paramedic.mobshaman.R;


/**
 * Created by soporte on 23/07/2014.
 */
public class AccionesDetalleServicioFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_acciones_detalle_servicio,container,false);

        displayValues(myView);

        return myView;
    }

    private void displayValues(View myView) {

        TextView txtIdServicio = (TextView) myView.findViewById(R.id.idServicio2);

        txtIdServicio.setText("OLA K ASE 2");

    }
}
