package com.paramedic.mobshaman.fragments;

import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.paramedic.mobshaman.R;
import com.paramedic.mobshaman.activities.ServiciosActivity;
import com.paramedic.mobshaman.adapters.ServiciosAdapter;
import com.paramedic.mobshaman.models.Servicio;

import java.util.ArrayList;

/**
 * Created by maxo on 22/07/14.
 */
public class ServiciosFragment extends ListFragment {

    String[] mServicios;
    int[] mImagenes = {R.drawable.ic_action_search,R.drawable.ic_action_drive_file,R.drawable.ic_launcher};
    ListView listView;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView = getListView();

        ColorDrawable divider = new ColorDrawable(this.getResources().getColor(R.color.verde_color));
        listView.setDivider(divider);
        listView.setDividerHeight(1);

        ServiciosAdapter servAdapter = new ServiciosAdapter(this.getActivity(), generateData());
        setListAdapter(servAdapter);

    }


    private ArrayList<Servicio> generateData() {
        ArrayList<Servicio> lstServ = new ArrayList<Servicio>();
        lstServ.add(new Servicio("OSDE","ROJO","K4L","Av. Alvarez Thomas 1544","M","11","15:01"));
        lstServ.add(new Servicio("OSDE","ROJO","K4L","Av. Alvarez Thomas 1544","M","11","15:01"));
        lstServ.add(new Servicio("OSDE","ROJO","K4L","Av. Alvarez Thomas 1544","M","11","15:01"));
        lstServ.add(new Servicio("OSDE","ROJO","K4L","Av. Alvarez Thomas 1544","M","11","15:01"));
        lstServ.add(new Servicio("OSDE","ROJO","K4L","Av. Alvarez Thomas 1544","M","11","15:01"));
        lstServ.add(new Servicio("OSDE","ROJO","K4L","Av. Alvarez Thomas 1544","M","11","15:01"));
        lstServ.add(new Servicio("OSDE","ROJO","K4L","Av. Alvarez Thomas 1544","M","11","15:01"));
        lstServ.add(new Servicio("OSDE","ROJO","K4L","Av. Alvarez Thomas 1544","M","11","15:01"));
        lstServ.add(new Servicio("OSDE","ROJO","K4L","Av. Alvarez Thomas 1544","M","11","15:01"));
        lstServ.add(new Servicio("OSDE","ROJO","K4L","Av. Alvarez Thomas 1544","M","11","15:01"));

        return lstServ;
    }




}
