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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import com.paramedic.mobshaman.R;
import com.paramedic.mobshaman.activities.DetalleServicioActivity;
import com.paramedic.mobshaman.adapters.ServiciosAdapter;
import com.paramedic.mobshaman.domain.Configuration;
import com.paramedic.mobshaman.managers.SessionManager;
import com.paramedic.mobshaman.models.LoginResponse;
import com.paramedic.mobshaman.models.Servicio;
import com.paramedic.mobshaman.rest.ApiClient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by maxo on 22/07/14.
 */
public class ServiciosFragment extends ListFragment implements AdapterView.OnItemSelectedListener{

    ListView listView;
    private ProgressDialog pDialog;
    private ServiciosAdapter servAdapter;
    public String NRO_MOVIL, URL_REST, URL_REST_SERVICIOS;
    public int ID_SERVICIO_SELECCIONADO = 0;
    public Configuration configuration;
    private LocationManager locManager;
    private Location actualLocation;
    private LocationListener locListener;
    private ArrayList<Servicio> services;
    private Spinner spinnerOrderBy;
    SessionManager session;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        session = new SessionManager(getActivity().getApplicationContext());

        locManager =
                (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

        //Obtenemos la última posición conocida
        Location loc =
                locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        actualLocation = loc;


        //Nos registramos para recibir actualizaciones de la posición
        locListener = new LocationListener() {
            public void onLocationChanged(Location loc) {
                if (loc != null && services != null) {
                    actualLocation = loc;
                    updateServices();
                }

            }
            public void onProviderDisabled(String provider){
            }
            public void onProviderEnabled(String provider){
            }
            public void onStatusChanged(String provider, int status, Bundle extras){
            }
        };

        locManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 10000, 100, locListener);


        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);

        initializeComponents();

        if (session.isLoggedIn()) {
            try {
                getServicios();
            } catch (Exception e) {
                Toast.makeText(getActivity().getApplicationContext(), "Error en la conexión con el servidor",
                        Toast.LENGTH_LONG).show();
            }
        } else {
            session.checkLogin();
        }

    }

    @Override
    public void onPause(){
        locManager.removeUpdates(locListener);
        super.onPause();
    }

    @Override
    public void onResume() {
        locManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 10000, 100, locListener);
        super.onResume();
    }

    private void updateServices() {

        if (actualLocation != null) {
            for (Servicio servicio : services) {
                Location serviceLoc = new Location("service");
                serviceLoc.setLatitude(Double.parseDouble(servicio.getLatitud()));
                serviceLoc.setLongitude(Double.parseDouble(servicio.getLongitud()));
                servicio.setDistanceToIncident(actualLocation.distanceTo(serviceLoc)/1000);
            }
        }

        orderBy(spinnerOrderBy.getSelectedItemId());

        servAdapter = new ServiciosAdapter(getActivity(), services, ServiciosFragment.this);
        setListAdapter(servAdapter);
        validateSerial();
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

        // --> Seteo spinner para ordenar servicios
        // Spinner element
        spinnerOrderBy = (Spinner) getActivity().findViewById(R.id.spinner_orderby_services);

        // Spinner click listener
        spinnerOrderBy.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Cronológicamente");
        categories.add("Distancia al servicio");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerOrderBy.setAdapter(dataAdapter);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        orderBy(id);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        /** Redefino este metodo para poder actualizar desde el fragment y no desde la activity **/

        boolean handled = false;

        int id = item.getItemId();
        switch (id) {
            case R.id.action_refresh:
                getServicios();
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

    public void orderBy(long orderType) {
        try {
            if (services != null) {
                if (orderType == 0) {

                    // --> Cronologico
                    Collections.sort(services, new Comparator<Servicio>() {
                        public int compare(Servicio s1, Servicio s2) {
                            if (s1.getIncidentDateTime().equals(s2.getIncidentDateTime())) {
                                return 0;
                            }
                            return s1.getIncidentDateTime().before(s2.getIncidentDateTime())  ? -1 : 1;
                        }
                    });

                } else {
                    if (locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        if (actualLocation != null) {
                            // --> Distancia al incidente
                            Collections.sort(services, new Comparator<Servicio>() {
                                public int compare(Servicio s1, Servicio s2) {
                                    if (s1.getDistanceToIncident() == s2.getDistanceToIncident()) {
                                        return 0;
                                    }
                                    return s1.getDistanceToIncident() > s2.getDistanceToIncident() ? 1 : -1;
                                }
                            });
                        } else {
                            showToast("No se puede obtener su ubicación");
                        }

                    } else {
                        showToast("Para ordenar por distancia, debe tener el gps habilitado.");
                    }

                }

                // --> Por currentViaje
                Collections.sort(services, new Comparator<Servicio>() {
                    public int compare(Servicio s1, Servicio s2) {
                        if (s1.getCurrentViaje() == s2.getCurrentViaje()) {
                            return 0;
                        }
                        return s1.getCurrentViaje() > s2.getCurrentViaje()  ? -1 : 1;
                    }
                });

                servAdapter = new ServiciosAdapter(getActivity(), services, ServiciosFragment.this);
                setListAdapter(servAdapter);
            }
        } catch(Exception ex) {
            showToast("Error: no se pudieron ordenar los incidentes.");
        }

    }

    private void getServicios() {

        String dialogMessage = "Cargando incidentes...";
        if (!configuration.IsValid()) {
            Toast.makeText(getActivity(),"La aplicación no fue configurada para su uso.",
                    Toast.LENGTH_LONG).show();
            return;
        }

        showLoadingMessage(dialogMessage);

        ApiClient sac = new ApiClient(getActivity(), configuration.getUrl());

        sac.getServicesCall().enqueue(new Callback<ArrayList<Servicio>>() {
            @Override
            public void onResponse(Call<ArrayList<Servicio>> call, Response<ArrayList<Servicio>> response) {
                int code = response.code();
                if (code == 200) {
                    services = response.body();
                    updateServices();
                } else {
                    Toast.makeText(getActivity(), "Error: " + String.valueOf(code), Toast.LENGTH_LONG).show();
                }
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<Servicio>> call, Throwable t) {
                pDialog.dismiss();

            }

        });


    }

    public void getDetalleServicio(int serviceId) {

        String dialogMessage = "Cargando incidente...";

        showLoadingMessage(dialogMessage);

        ApiClient sac = new ApiClient(getActivity(), configuration.getUrl());

        sac.getServiceCall(String.valueOf(serviceId)).enqueue(new Callback<Servicio>() {
            @Override
            public void onResponse(Call<Servicio> call, Response<Servicio> response) {
                int code = response.code();
                if (code == 200) {
                    Servicio service = response.body();
                    Intent intent = new Intent(getActivity(), DetalleServicioActivity.class);
                    intent.putExtra("Servicio", service);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "Error: " + String.valueOf(code), Toast.LENGTH_LONG).show();
                }
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Servicio> call, Throwable t) {
                pDialog.dismiss();

            }

            ;
        });
    }

    public void validateSerial() {

        ApiClient sac = new ApiClient(getActivity(), configuration.getGestionUrl());

        String user = session.getUserDetails().get("user");
        String password = session.getUserDetails().get("password");

        sac.getLoginCall(user, password).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                int code = response.code();
                if (code == 200) {
                    LoginResponse lr = response.body();
                    if (lr.getError()) {
                        session.logoutUser();
                    }
                } else {
                    Toast.makeText(getActivity(), "Error: " + String.valueOf(code), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Error: " + String.valueOf(t.getMessage()), Toast.LENGTH_LONG).show();
            }

        });

    }

}
