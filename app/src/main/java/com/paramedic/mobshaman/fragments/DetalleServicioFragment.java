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

    TextView tvLocalidad, tvDomicilio, tvEntreCalle1, tvEntreCalle2,
    tvReferencias, tvSintomas, tvGrado, tvAviso, tvPaciente, tvSexo,
    tvEdad, tvEntidad, tvNroAfiliado, tvNroInterno, tvCoPago, tvObservaciones,
    tvNroServicio;

    Servicio serv;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        /** Redefino este metodo para poder actualizar desde el fragment y no desde la activity **/

        boolean handled = false;

        int id = item.getItemId();
        switch (id) {
            case R.id.action_ubicacion_servicio:
                /** Voy a la actividad donde me muestra la ubicación del servicio **/
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

        /** Obtengo via intent los datos del servicio y los muestro en la UI **/

        Intent intent = this.getActivity().getIntent();

        serv = (Servicio) intent.getSerializableExtra("Servicio");

        tvLocalidad = (TextView) myView.findViewById(R.id.txtLocalidadServicio);
        tvDomicilio = (TextView) myView.findViewById(R.id.txtDomicilioServicio);
        tvEntreCalle1 = (TextView) myView.findViewById(R.id.txtEntreCalle1Servicio);
        tvEntreCalle2 = (TextView) myView.findViewById(R.id.txtEntreCalle2Servicio);
        tvReferencias = (TextView) myView.findViewById(R.id.txtReferenciasServicio);
        tvSintomas = (TextView) myView.findViewById(R.id.txtSintomasServicio);
        tvGrado = (TextView) myView.findViewById(R.id.txtGradoServicio);
        tvAviso = (TextView) myView.findViewById(R.id.txtAvisoServicio);
        tvPaciente = (TextView) myView.findViewById(R.id.txtPacienteServicio);
        tvSexo = (TextView) myView.findViewById(R.id.txtSexoServicio);
        tvEdad = (TextView) myView.findViewById(R.id.txtEdadServicio);
        tvEntidad = (TextView) myView.findViewById(R.id.txtEntidadServicio);
        tvNroAfiliado = (TextView) myView.findViewById(R.id.txtNroAfiliadoServicio);
        tvNroInterno = (TextView) myView.findViewById(R.id.txtNroInternoServicio);
        tvCoPago = (TextView) myView.findViewById(R.id.txtCoPagoServicio);
        tvObservaciones = (TextView) myView.findViewById(R.id.txtObservacionesServicio);
        tvNroServicio = (TextView) myView.findViewById(R.id.txt_header_detalle_servicio);

        tvLocalidad.setText(serv.getLocalidad());
        tvDomicilio.setText(serv.getDomicilio());
        tvEntreCalle1.setText(serv.getEntreCalle1());
        tvEntreCalle2.setText(serv.getEntreCalle2());
        tvReferencias.setText(serv.getReferencia());
        tvSintomas.setText(serv.getSintomas());
        tvGrado.setText(serv.getGrado());
        tvGrado.setTextColor(getResources().getColor(serv.getGradoColor()));
        tvAviso.setText(serv.getAviso());
        tvPaciente.setText(serv.getPaciente());
        tvSexo.setText(serv.getSexo());
        tvEdad.setText(serv.getEdad());
        tvEntidad.setText(serv.getCliente());
        tvNroAfiliado.setText(serv.getNroAfiliado());
        //tvNroInterno.setText(serv.getNro);
        tvCoPago.setText(serv.getCoPago().toString());
        tvObservaciones.setText(serv.getObservaciones());

        tvNroServicio.setText("Datos del Servicio " + serv.getNroServicio());

    }
}
