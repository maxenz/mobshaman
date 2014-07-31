package com.paramedic.mobshaman.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.paramedic.mobshaman.R;
import com.paramedic.mobshaman.handlers.HttpHandler;
import com.paramedic.mobshaman.helpers.DialogHelper;
import com.paramedic.mobshaman.helpers.HandleMessageHelper;
import com.paramedic.mobshaman.helpers.SharedPrefsHelper;
import com.paramedic.mobshaman.interfaces.AlertListener;
import com.paramedic.mobshaman.models.Servicio;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by soporte on 23/07/2014.
 */
public class AccionesDetalleServicioFragment extends Fragment {

    ProgressDialog pDialog;
    HandleMessageHelper msgHelper;
    Servicio serv;
    Button btnLlegadaServicio;

    String URL_REST, NRO_MOVIL;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);

        URL_REST = new SharedPrefsHelper().getURLFromSharedPrefs(this.getActivity());
        NRO_MOVIL = new SharedPrefsHelper().getNroMovilFromSharedPrefs(this.getActivity());
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

        msgHelper = new HandleMessageHelper();

        Intent intent = this.getActivity().getIntent();

        serv = (Servicio) intent.getSerializableExtra("Servicio");

        btnLlegadaServicio = (Button) myView.findViewById(R.id.btn_llegada_servicio);

        setButtonLlegadaListener();

        return myView;
    }

    /**
     * Empieza llegada de movil
     */

    private void setButtonLlegadaListener() {
        btnLlegadaServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogHelper dg = new DialogHelper();
                dg.getConfirmDialog(getActivity(),"Alerta", "¿Desea darle llegada al movil?", "Si", "No", false,
                        new AlertListener() {

                            @Override
                            public void PositiveMethod(final DialogInterface dialog, final int id) {
                                doAsyncTaskLlegada();
                            }

                            @Override
                            public void NegativeMethod(DialogInterface dialog, int id) {
                                Toast.makeText(getActivity().getApplicationContext(),"Llegada cancelada.",Toast.LENGTH_LONG).show();
                            }
                        }
                );
            }
        });
    }

    private void doAsyncTaskLlegada() {

        pDialog.setMessage("Dando llegada al móvil...");
        pDialog.show();

        new HttpHandler() {
            @Override
            public HttpUriRequest getHttpRequestMethod() {

                String viajeID = "" + serv.getId();

                HttpPost hpSalida = new HttpPost(URL_REST + "/Acciones/setLlegadaMovil");
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("movil", NRO_MOVIL));
                nameValuePairs.add(new BasicNameValuePair("viajeID", viajeID));
                try {
                    hpSalida.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                return hpSalida;

            }
            @Override
            public void onResponse(String result) {

                /** Veo si pude obtener los servicios o si hubo un problema **/
                if (result == "") {

                    Toast.makeText(getActivity(),"Error en la red. Intente nuevamente.",Toast.LENGTH_LONG).show();

                } else if (msgHelper.getCodeResponse(result) == "0") {

                    Toast.makeText(getActivity(),"La llegada se registró correctamente",Toast.LENGTH_LONG).show();
                } else {

                    Toast.makeText(getActivity(),"Error: " + msgHelper.getMessageResponse(result),Toast.LENGTH_LONG).show();
                }

                pDialog.dismiss();
            }

        }.execute();
    }

    /**
     * Termina llegada de movil
     */


}
