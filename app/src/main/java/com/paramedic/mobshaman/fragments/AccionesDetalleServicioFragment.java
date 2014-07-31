package com.paramedic.mobshaman.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.paramedic.mobshaman.R;
import com.paramedic.mobshaman.adapters.ServiciosAdapter;
import com.paramedic.mobshaman.handlers.HttpHandler;
import com.paramedic.mobshaman.helpers.DialogHelper;
import com.paramedic.mobshaman.helpers.ServiciosHelper;
import com.paramedic.mobshaman.interfaces.AlertListener;
import com.paramedic.mobshaman.models.Servicio;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import java.util.ArrayList;


/**
 * Created by soporte on 23/07/2014.
 */
public class AccionesDetalleServicioFragment extends Fragment {

    ProgressDialog pDialog;
    Servicio serv;
    Button btnSalidaServicio;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        MenuItem itemMapa = menu.findItem(R.id.action_ubicacion_servicio);
        itemMapa.setVisible(false);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_acciones_detalle_servicio,container,false);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        Intent intent = this.getActivity().getIntent();

        serv = (Servicio) intent.getSerializableExtra("Servicio");

        btnSalidaServicio = (Button) myView.findViewById(R.id.btn_salida_servicio);

        setButtonSalidaListener();

        return myView;
    }

    private void setButtonSalidaListener() {
        btnSalidaServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogHelper dg = new DialogHelper();
                dg.getConfirmDialog(getActivity(),"Alerta", "¿Desea darle salida al movil?", "Si", "No", false,
                        new AlertListener() {

                            @Override
                            public void PositiveMethod(final DialogInterface dialog, final int id) {
                                Toast.makeText(getActivity().getApplicationContext(),serv.getPaciente(),Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void NegativeMethod(DialogInterface dialog, int id) {
                                Toast.makeText(getActivity().getApplicationContext(),"No se le ha dado salida al movil",Toast.LENGTH_LONG).show();
                            }
                        }
                );
            }
        });
    }

//    private void doAsyncTask(String pDialogMessage) {
//
//        pDialog.setMessage(pDialogMessage);
//        pDialog.show();
//
//        new HttpHandler() {
//            @Override
//            public HttpUriRequest getHttpRequestMethod() {
//
//                HttpGet hgServicios = new HttpGet(URL_REST_SERVICIOS + "?idMovil=" + NRO_MOVIL);
//
//                hgServicios.setHeader("content-type", "application/json");
//
//                return hgServicios;
//
//            }
//            @Override
//            public void onResponse(String result) {
//
//                /** Veo si pude obtener los servicios o si hubo un problema **/
//                if (result == "") {
//
//                    Toast.makeText(getActivity(),"Error en la red. Intente nuevamente",Toast.LENGTH_LONG).show();
//
//                } else {
//
//                    /** Seteo los datos en la lista **/
//                    ArrayList<Servicio> servicios = new ServiciosHelper().stringToListServicios(result,0);
//
//                    servAdapter = new ServiciosAdapter(getActivity(), servicios, ServiciosFragment.this);
//                    setListAdapter(servAdapter);
//                }
//
//                pDialog.dismiss();
//
//            }
//
//        }.execute();
//
//
//    }


}
