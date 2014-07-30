package com.paramedic.mobshaman.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.paramedic.mobshaman.R;
import com.paramedic.mobshaman.activities.DetalleServicioActivity;
import com.paramedic.mobshaman.adapters.ServiciosAdapter;
import com.paramedic.mobshaman.handlers.HttpHandler;
import com.paramedic.mobshaman.helpers.ServiciosHelper;
import com.paramedic.mobshaman.models.Servicio;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by maxo on 22/07/14.
 */
public class ServiciosFragment extends ListFragment {

    ListView listView;
    private ProgressDialog pDialog;
    private ServiciosAdapter servAdapter;
    private String NRO_MOVIL, URL_REST_SERVICIOS;
    public int ID_SERVICIO_SELECCIONADO = 0;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);

        initializeComponents();

        getListadoServicios();

    }

    private void initializeComponents() {

        /** Redefino el estilo de la listview que usa el fragment **/
        listView = getListView();
        ColorDrawable divider = new ColorDrawable(this.getResources().getColor(R.color.verde_color));
        listView.setDivider(divider);
        listView.setDividerHeight(1);

        /** Muestro un dialogo spinner (no dejo que usuario use UI) **/
        pDialog = new ProgressDialog(getActivity());
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setCancelable(true);

        /** Obtengo datos de shared preferences de la configuración general **/
        SharedPreferences sp = getActivity().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        NRO_MOVIL = sp.getString("nroMovil","");
        URL_REST_SERVICIOS = sp.getString("urlREST","");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        /** Redefino este metodo para poder actualizar desde el fragment y no desde la activity **/

        boolean handled = false;

        int id = item.getItemId();
        switch (id) {
            case R.id.action_refresh:
                getListadoServicios();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return handled;
    }


    public void getListadoServicios() {

        /** Si no está configurado el móvil, no puedo obtener servicios.. **/

        if (NRO_MOVIL == "") {
            Toast.makeText(getActivity(),"La aplicación no fue configurada para su uso.",Toast.LENGTH_LONG).show();
            return;
        }

        pDialog.setMessage("Actualizando Servicios...");
        pDialog.show();

        /** Request asincrónica para traer todos los servicios de ese móvil **/
        new HttpHandler() {
            @Override
            public HttpUriRequest getHttpRequestMethod() {

                HttpGet hgServicios = new HttpGet(URL_REST_SERVICIOS + "?idMovil=" + NRO_MOVIL);

                hgServicios.setHeader("content-type", "application/json");

                return hgServicios;

            }
            @Override
            public void onResponse(String result) {

                /** Veo si pude obtener los servicios o si hubo un problema **/
                if (result == "") {

                    Toast.makeText(getActivity(),"Error en la red. Intente nuevamente",Toast.LENGTH_LONG).show();

                } else {

                    /** Seteo los datos en la lista **/
                    ArrayList<Servicio> servicios = new ServiciosHelper().stringToListServicios(result,0);

                    servAdapter = new ServiciosAdapter(getActivity(), servicios, ServiciosFragment.this);
                    setListAdapter(servAdapter);
                }

                pDialog.dismiss();

            }

        }.execute();

    }

    public void getDetalleServicio() {

        pDialog.setMessage("Cargando Servicio...");
        pDialog.show();

        /** Obtengo asincronicamente el detalle del servicio .. **/
        new HttpHandler() {
            @Override
            public HttpUriRequest getHttpRequestMethod() {

                String urlFinal = URL_REST_SERVICIOS + "/" + ID_SERVICIO_SELECCIONADO;

                HttpGet hgServicios = new HttpGet(urlFinal);

                hgServicios.setHeader("content-type", "application/json");

                return hgServicios;

            }
            @Override
            public void onResponse(String result) {

                /** Veo si llegan los datos o si hubo un problema **/
                if (result == "") {

                    Toast.makeText(getActivity(),"Error en la red. Intente nuevamente",Toast.LENGTH_LONG).show();

                } else {

                    try {

                        /** Obtengo el servicio y lo paso a la actividad de detalle **/
                        JSONObject jServ = new JSONObject(result);

                        Servicio servDetalle = new ServiciosHelper().jsonToServicio(jServ,1);

                        pDialog.dismiss();

                        Intent intent = new Intent(getActivity(), DetalleServicioActivity.class);
                        intent.putExtra("Servicio", servDetalle);
                        startActivity(intent);


                    } catch (JSONException e) {

                        e.printStackTrace();

                    }

                }

            }

        }.execute();

    }

}
