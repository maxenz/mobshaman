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
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.paramedic.mobshaman.R;
import com.paramedic.mobshaman.activities.FinalServicioActivity;
import com.paramedic.mobshaman.activities.ServiciosActivity;
import com.paramedic.mobshaman.helpers.DialogHelper;
import com.paramedic.mobshaman.helpers.SharedPrefsHelper;
import com.paramedic.mobshaman.interfaces.AlertListener;
import com.paramedic.mobshaman.models.AccionesRestModel;
import com.paramedic.mobshaman.models.Servicio;
import com.paramedic.mobshaman.rest.ServiciosRestClient;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by soporte on 23/07/2014.
 */
public class AccionesDetalleServicioFragment extends Fragment {

    ProgressDialog pDialog;
    Servicio serv;
    Button btnLlegadaServicio, btnSalidaServicio, btnFinalServicio;
    String URL_REST, NRO_MOVIL;
    int FLAG_FINALIZAR_SERVICIO = 0;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
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

        URL_REST = new SharedPrefsHelper().getURLFromSharedPrefs(this.getActivity());
        NRO_MOVIL = new SharedPrefsHelper().getNroMovilFromSharedPrefs(this.getActivity());

        getServicioFromIntent();

        initializeUI(myView);

        initializeActionButtons();

        return myView;
    }

    private void getServicioFromIntent() {
        Intent intent = this.getActivity().getIntent();
        serv = (Servicio) intent.getSerializableExtra("Servicio");
    }

    private void initializeActionButtons() {

        RequestParams reqParams = new RequestParams();
        reqParams.put("movil",NRO_MOVIL);
        reqParams.put("viajeID", serv.getIdServicio());

        AccionesRestModel finalServ = new AccionesRestModel("Final del servicio",
                "¿Seguro que desea finalizar el servicio?","Final de servicio cancelado",
                URL_REST + "/acciones/setFinal", "Finalizando servicio...");

        AccionesRestModel salidaServ = new AccionesRestModel("Salida del servicio",
                "¿Seguro que desea dar salida al servicio?","Salida de servicio cancelada",
                URL_REST + "/acciones/setSalidaMovil", "Dando salida al servicio...");

        AccionesRestModel llegadaServ = new AccionesRestModel("Llegada del servicio",
                "¿Seguro que desea dar llegada al servicio?","Llegada de servicio cancelada",
                URL_REST + "/acciones/setLlegadaMovil", "Dando llegada al servicio...");

        //doActionServicio(finalServ, btnFinalServicio, reqParams);
        doActionServicio(salidaServ,btnSalidaServicio,reqParams);
        doActionServicio(llegadaServ,btnLlegadaServicio,reqParams);

        btnFinalServicio.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), FinalServicioActivity.class);
                i.putExtra("nroServicio", serv.getNroServicio());
                startActivityForResult(i, 1);
            }
        });

    }

    private void initializeUI(View myView) {

        btnLlegadaServicio = (Button) myView.findViewById(R.id.btn_llegada_servicio);
        btnSalidaServicio = (Button) myView.findViewById(R.id.btn_salida_servicio);
        btnFinalServicio = (Button) myView.findViewById(R.id.btn_final_servicio);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

    }

    private void showToast(String mensaje) {
        Toast.makeText(getActivity().getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
    }

    private void showLoadingMessage(String message) {
        pDialog.setMessage(message);
        pDialog.show();
    }

    private void doActionServicio(final AccionesRestModel accServ, Button btn,
                                  final RequestParams rp) {
        setActionListener(btn,accServ.getTituloAlertDialog(),
                accServ.getMensajeAlertDialog(),
                new AlertListener() {
                    @Override
                    public void PositiveMethod(final DialogInterface dialog, final int id) {
                        try {
                            doAsyncTaskPostServicio(accServ.getUrl(), accServ.getLoadingMessage(),
                                    rp);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void NegativeMethod(DialogInterface dialog, int id) {
                        showToast(accServ.getMensajeCancelAlertDialog());
                    }
                }
        );
    }

    private void doAsyncTaskPostServicio(String url, final String dialogMessage,
                                         RequestParams rp) throws JSONException{

            ServiciosRestClient.post(url,rp, new JsonHttpResponseHandler() {

                @Override
                public void onStart() {
                    showLoadingMessage(dialogMessage);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                    pDialog.dismiss();
                    showToast("Error " + statusCode + ": " + throwable.getMessage());
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    pDialog.dismiss();
                    showToast("Error " + statusCode + ": " + throwable.getMessage());
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        showToast(response.getString("Message"));
                        if (FLAG_FINALIZAR_SERVICIO == 1) {
                            startActivity(new Intent(getActivity(), ServiciosActivity.class));
                        }
                    } catch (JSONException e) {
                        showToast(e.getMessage());
                    }
                    pDialog.dismiss();

                }

            });

    }

    private void setActionListener(Button btnAction, final String title,
                                   final String messageDialog, final AlertListener alertListener) {

        btnAction.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DialogHelper dg = new DialogHelper();
                dg.getConfirmDialog(getActivity(),title,messageDialog,"Si","No",false, alertListener);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == getActivity().RESULT_OK){
                RequestParams rp = new RequestParams();
                rp.add("movil",NRO_MOVIL);
                rp.add("viajeID", String.valueOf(serv.getIdServicio()));
                rp.add("motivoID", String.valueOf(data.getIntExtra("idMotivo", 0)));
                rp.add("diagnosticoID", String.valueOf(data.getIntExtra("idDiagnostico", 0)));
                rp.add("observaciones", data.getStringExtra("observaciones"));
                FLAG_FINALIZAR_SERVICIO = 1;
                try {
                    doAsyncTaskPostServicio(URL_REST + "/acciones/setFinalServicio","Finalizando servicio...",rp);
                } catch (JSONException e) {
                    showToast(e.getMessage());
                }
            }
            if (resultCode == getActivity().RESULT_CANCELED) {
                showToast("No se finalizó el servicio");
            }
        }
    }

}
