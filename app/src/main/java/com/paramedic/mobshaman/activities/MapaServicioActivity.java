package com.paramedic.mobshaman.activities;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.paramedic.mobshaman.R;
import com.paramedic.mobshaman.models.Servicio;

public class MapaServicioActivity extends ActionBarActivity
implements LocationListener{

    private GoogleMap mMap;
    private String provider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setContentView(R.layout.activity_mapa_servicio);

        Intent intent = this.getIntent();

        Servicio serv = (Servicio) intent.getSerializableExtra("Servicio");

        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa_ubicacion)).getMap();
        Double latitud = Double.parseDouble(serv.getLatitud());
        Double longitud = Double.parseDouble(serv.getLongitud());
        LatLng punto = new LatLng(latitud,longitud);
        mMap.addMarker(new MarkerOptions()
                .position(punto)
                .title(serv.getDomicilio()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(punto, 14.0f));

//        LocationManager mlocManager = (LocationManager)
//                getSystemService(Context.LOCATION_SERVICE);
//
//        boolean enabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//
//        if(!enabled) {
//            Intent intSettingsGPS = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//            startActivity(intSettingsGPS);
//
//        } else {
//
//            Criteria criteria = new Criteria();
//            provider = mlocManager.getBestProvider(criteria,false);
//            Location location = mlocManager.getLastKnownLocation(provider);
//
//            // Initialize the location fields
//            if (location != null) {
//                System.out.println("Provider " + provider + " has been selected.");
//                onLocationChanged(location);
//            } else {
//                Toast.makeText(getApplicationContext(), "problema", Toast.LENGTH_LONG).show();
//            }
//        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean handled = false;

        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return handled;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


        @Override
        public void onLocationChanged(Location location) {

            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa_ubicacion)).getMap();
            Double latitud = location.getLatitude();
            Double longitud = location.getLongitude();
            LatLng punto = new LatLng(latitud,longitud);
            mMap.addMarker(new MarkerOptions()
                    .position(punto));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(punto, 14.0f));

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
}
