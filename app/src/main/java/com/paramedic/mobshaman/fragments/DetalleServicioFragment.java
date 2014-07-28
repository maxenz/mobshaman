package com.paramedic.mobshaman.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paramedic.mobshaman.R;
import com.paramedic.mobshaman.activities.DetalleServicioActivity;
import com.paramedic.mobshaman.activities.MapaServicioActivity;
import com.paramedic.mobshaman.models.Servicio;

import org.w3c.dom.Text;

/**
 * Created by soporte on 23/07/2014.
 */
public class DetalleServicioFragment extends Fragment {

    TextView tvCliente,tvGrado, tvDomicilio, tvSexo, tvEdad, tvNroServicio;
    Servicio serv;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // --> Redefino este metodo para poder actualizar desde el fragment y no desde la activity

        boolean handled = false;

        int id = item.getItemId();
        switch (id) {
            case R.id.action_ubicacion_servicio:
                Intent intent = new Intent(getActivity(), MapaServicioActivity.class);
                intent.putExtra("Servicio", serv);
                startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return handled;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_detalle_servicio,container,false);

        displayValues(myView);

        return myView;
    }

    private void displayValues(View myView) {

        Intent intent = this.getActivity().getIntent();

        serv = (Servicio) intent.getSerializableExtra("Servicio");

        tvCliente = (TextView) myView.findViewById(R.id.txtClienteServicio);
        tvGrado = (TextView) myView.findViewById(R.id.txtGradoServicio);
        tvDomicilio = (TextView) myView.findViewById(R.id.txtDomicilioServicio);
        tvSexo = (TextView) myView.findViewById(R.id.txtSexoServicio);
        tvEdad = (TextView) myView.findViewById(R.id.txtEdadServicio);
        tvNroServicio = (TextView) myView.findViewById(R.id.nroServicio);

        tvCliente.setText(serv.getCliente());
        tvGrado.setTextColor(getResources().getColor(serv.getGradoColor()));
        tvGrado.setText(serv.getGrado());
        tvDomicilio.setText(serv.getDomicilio());
        tvSexo.setText(serv.getSexo());
        tvEdad.setText(serv.getEdad());

        tvNroServicio.setText("Datos del Servicio " + serv.getNroIncidente());

    }
}
