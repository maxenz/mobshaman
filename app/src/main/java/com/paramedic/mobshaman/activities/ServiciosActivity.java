package com.paramedic.mobshaman.activities;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.paramedic.mobshaman.R;
import com.paramedic.mobshaman.fragments.AdminPasswordDialogFragment;
import com.paramedic.mobshaman.helpers.Utils;


public class ServiciosActivity extends ActionBarActivity {

    LocationManager locManager;
    LocationListener locListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        //showDistance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);

    }


//    private void showDistance()
//    {
//        //Obtenemos una referencia al LocationManager
//        locManager =
//                (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//
//        //Obtenemos la última posición conocida
//        Location loc =
//                locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//
//        if (loc != null) {
//            Utils.showToast(this, "Latitud: " + Double.toString(loc.getLatitude()));
//        }

//        //Nos registramos para recibir actualizaciones de la posición
//        locListener = new LocationListener() {
//            public void onLocationChanged(Location loc) {
//                showData(loc);
//            }
//            public void onProviderDisabled(String provider){
//                Log.i("Mapa: ", "El gps fue deshabilitado");
//            }
//            public void onProviderEnabled(String provider){
//                Log.i("Mapa: ", "El gps fue habilitado");
//            }
//            public void onStatusChanged(String provider, int status, Bundle extras){
//            }
//        };

//        locManager.requestLocationUpdates(
//                LocationManager.GPS_PROVIDER, 30000, 100, locListener);
    //}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.servicios, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        boolean handled = true;
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                AdminPasswordDialogFragment admFrg = new AdminPasswordDialogFragment();
                admFrg.show(this.getSupportFragmentManager(),"ConfiguracionGeneral");
                break;
            case R.id.action_refresh:
                /** Paso false para poder redefinirlo desde el fragment **/
                handled = false;
                break;
            case R.id.action_actualizar_parametros:
                startActivity(new Intent(this,ActualizarInformacionActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }

        return handled;

    }


}
