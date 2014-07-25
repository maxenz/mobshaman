package com.paramedic.mobshaman.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.paramedic.mobshaman.R;
import com.paramedic.mobshaman.models.Servicio;

/**
 * Created by soporte on 25/07/2014.
 */
public class GeoServicioFragment extends Fragment {

    private GoogleMap mMap;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Intent intent = this.getActivity().getIntent();

        Servicio serv = (Servicio) intent.getSerializableExtra("Servicio");

        mMap = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        Double latitud = Double.parseDouble(serv.getLatitud());
        Double longitud = Double.parseDouble(serv.getLongitud());
        LatLng punto = new LatLng(latitud,longitud);
        mMap.addMarker(new MarkerOptions()
                .position(punto)
                .title("Hello world"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(punto, 12.0f));


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_geo_servicio,container,false);



        return myView;
    }

    @Override
    public void onDestroyView() {

        Fragment f = (Fragment) getFragmentManager().findFragmentById(R.id.map);
        if (f != null) {
            getFragmentManager().beginTransaction().remove(f).commit();
        }

        super.onDestroyView();
    }
}
