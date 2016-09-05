package com.paramedic.mobshaman.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.paramedic.mobshaman.R;
import com.paramedic.mobshaman.activities.DetalleServicioActivity;
import com.paramedic.mobshaman.activities.ServiciosActivity;
import com.paramedic.mobshaman.adapters.ServiciosAdapter;
import com.paramedic.mobshaman.domain.Configuration;
import com.paramedic.mobshaman.helpers.ServiciosHelper;
import com.paramedic.mobshaman.helpers.Utils;
import com.paramedic.mobshaman.models.Servicio;
import com.paramedic.mobshaman.rest.ServiciosRestClient;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.util.ArrayList;

/**
 * Created by maxo on 22/07/14.
 */
public class ServiciosFragment extends ListFragment {

    ListView listView;
    private ProgressDialog pDialog;
    private ServiciosAdapter servAdapter;
    public String NRO_MOVIL, URL_REST, URL_REST_SERVICIOS;
    public int ID_SERVICIO_SELECCIONADO = 0;
    public Configuration configuration;
    private static final String URL_SERVICE_PARAMETERS = "/api/services?idMovil=";
    private LocationManager locManager;
    private Location actualLocation;
    private LocationListener locListener;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        locManager =
                (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

        //Obtenemos la última posición conocida
        Location loc =
                locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        actualLocation = loc;


        //Nos registramos para recibir actualizaciones de la posición
        locListener = new LocationListener() {
            public void onLocationChanged(Location loc) {
                actualLocation = null;
                if (loc != null) {
                    actualLocation = loc;
                    Utils.showToast(getActivity(), "lat: " + Double.toString(actualLocation.getLatitude()) + " long:" + Double.toString(actualLocation.getLongitude()));
                }

            }
            public void onProviderDisabled(String provider){
                Log.i("Mapa: ", "El gps fue deshabilitado");
            }
            public void onProviderEnabled(String provider){
                Log.i("Mapa: ", "El gps fue habilitado");
            }
            public void onStatusChanged(String provider, int status, Bundle extras){
            }
        };

        locManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 3000, 1, locListener);


        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);

        initializeComponents();

        try {
            getServicios(URL_REST_SERVICIOS ,"Actualizando Servicios...",null);
        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), "Error en la conexión con el servidor",
                    Toast.LENGTH_LONG).show();
        }

    }

    private void initializeComponents() {

        configuration = Configuration.getInstance(this.getActivity());

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
        URL_REST_SERVICIOS = configuration.getUrl() + URL_SERVICE_PARAMETERS
                + configuration.getMobile() + "&licencia=" + configuration.getLicense();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        /** Redefino este metodo para poder actualizar desde el fragment y no desde la activity **/

        boolean handled = false;

        int id = item.getItemId();
        switch (id) {
            case R.id.action_refresh:
                getServicios(URL_REST_SERVICIOS ,"Actualizando Servicios...",null);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return handled;
    }

    private void showLoadingMessage(String message) {
        pDialog.setMessage(message);
        pDialog.show();
    }

    private void showToast(String mensaje) {
        Toast.makeText(getActivity().getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
    }

    public void getServicios(String URL, final String dialogMessage, RequestParams rp) {

        if (!configuration.IsValid()) {
            Toast.makeText(getActivity(),"La aplicación no fue configurada para su uso.",
                    Toast.LENGTH_LONG).show();
            return;
        }

        ServiciosRestClient.get(URL, rp, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                showLoadingMessage(dialogMessage);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString,
                                  Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                showToast("Error en la red. Intente nuevamente ");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                                  JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                showToast("Error en la red. Intente nuevamente ");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray servicios) {

                try {
                    ArrayList<Servicio> listaServicios =
                            ServiciosHelper.getArrayListServicioFromJSONArray(servicios);
                    if (actualLocation != null) {
                        Utils.showToast(getActivity(),"lat: " + Double.toString(actualLocation.getLatitude()) + " long:" + Double.toString(actualLocation.getLongitude()));
                    } else {
                        Utils.showToast(getActivity(),"nulo");
                    }

//                    if (actualLocation != null) {
//                        for (Servicio servicio : listaServicios) {
//                            Location serviceLoc = new Location("service");
//                            serviceLoc.setLatitude(Double.parseDouble(servicio.getLatitud()));
//                            serviceLoc.setLongitude(Double.parseDouble(servicio.getLongitud()));
//
//                            servicio.setDistanceToIncident(actualLocation.distanceTo(serviceLoc)/1000);
//                        }
//                    }

                    servAdapter = new ServiciosAdapter(getActivity(), listaServicios, ServiciosFragment.this);
                    setListAdapter(servAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFinish() {
                pDialog.dismiss();
            }
        });
    }

    public void getDetalleServicio(String URL, final String dialogMessage, RequestParams rp) {

        ServiciosRestClient.get(URL, rp, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                showLoadingMessage(dialogMessage);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString,
                                  Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                showToast("Error en la red. Intente nuevamente ");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                                  JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                showToast("Error en la red. Intente nuevamente ");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject servicio) {

                Intent intent = new Intent(getActivity(), DetalleServicioActivity.class);
                intent.putExtra("Servicio", ServiciosHelper.getServicioFromJSONObject(servicio));
                startActivity(intent);

            }

            @Override
            public void onFinish() {
                pDialog.dismiss();
            }
        });
    }

}
