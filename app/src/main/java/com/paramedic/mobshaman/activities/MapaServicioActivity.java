package com.paramedic.mobshaman.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.paramedic.mobshaman.R;
import com.paramedic.mobshaman.models.Servicio;

public class MapaServicioActivity extends ActionBarActivity {

    private GoogleMap mMap;



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
}
