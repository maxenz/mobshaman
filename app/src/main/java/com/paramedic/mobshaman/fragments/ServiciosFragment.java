package com.paramedic.mobshaman.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
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
    public final static String URL_REST_SERVICIOS = "http://paramedicapps.com.ar:58887/api/servicios?idMovil=23";
    public final static String URL_REST_SERVICIO_DETALLE = "http://paramedicapps.com.ar:58887/api/servicios";
    public int ID_SERVICIO_SELECCIONADO = 0;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);

        initializeComponents();

        getListadoServicios();

    }

    private void initializeComponents() {

        // --> Redefino el estilo de la listview que usa el fragment
        listView = getListView();
        ColorDrawable divider = new ColorDrawable(this.getResources().getColor(R.color.verde_color));
        listView.setDivider(divider);
        listView.setDividerHeight(1);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setCancelable(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // --> Redefino este metodo para poder actualizar desde el fragment y no desde la activity

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

        pDialog.setMessage("Actualizando Servicios...");
        pDialog.show();

        new HttpHandler() {
            @Override
            public HttpUriRequest getHttpRequestMethod() {

                HttpGet hgServicios = new HttpGet(URL_REST_SERVICIOS);

                hgServicios.setHeader("content-type", "application/json");

                return hgServicios;

            }
            @Override
            public void onResponse(String result) {

                if (result == "") {

                    Toast.makeText(getActivity(),"Error en la red. Intente nuevamente",Toast.LENGTH_LONG).show();

                } else {

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

        new HttpHandler() {
            @Override
            public HttpUriRequest getHttpRequestMethod() {

                String urlFinal = URL_REST_SERVICIO_DETALLE + "/" + ID_SERVICIO_SELECCIONADO;

                HttpGet hgServicios = new HttpGet(urlFinal);

                hgServicios.setHeader("content-type", "application/json");

                return hgServicios;

            }
            @Override
            public void onResponse(String result) {

                if (result == "") {

                    Toast.makeText(getActivity(),"Error en la red. Intente nuevamente",Toast.LENGTH_LONG).show();

                } else {

                    try {

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
