package com.paramedic.mobshaman.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paramedic.mobshaman.R;

import org.w3c.dom.Text;

/**
 * Created by soporte on 23/07/2014.
 */
public class DetalleServicioFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_detalle_servicio,container,false);

        displayValues(myView);

        return myView;
    }

    private void displayValues(View myView) {

        TextView txtIdServicio = (TextView) myView.findViewById(R.id.idServicio);

        //txtIdServicio.setText("OLA K ASE");

    }
}
