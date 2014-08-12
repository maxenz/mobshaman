package com.paramedic.mobshaman.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.paramedic.mobshaman.adapters.HistoriaClinicaAdapter;
import com.paramedic.mobshaman.helpers.HistoriaClinicaHelper;
import com.paramedic.mobshaman.helpers.SharedPrefsHelper;
import com.paramedic.mobshaman.models.HistoriaClinica;
import com.paramedic.mobshaman.rest.ServiciosRestClient;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class HistoriaClinicaFragment extends ListFragment {

    private ProgressDialog pDialog;
    String URL_REST, NRO_MOVIL;
    HistoriaClinicaAdapter hcAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        NRO_MOVIL = new SharedPrefsHelper().getNroMovilFromSharedPrefs(this.getActivity());
        URL_REST = new SharedPrefsHelper().getURLFromSharedPrefs(this.getActivity());

        String URL_HC = URL_REST + "/api/historiaclinica/";
        RequestParams rp = new RequestParams();
        rp.add("id","918");

        pDialog = new ProgressDialog(getActivity());
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        getHistoriaClinica(URL_HC,"Cargando historia clínica...",rp);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void getHistoriaClinica(String URL, final String dialogMessage, RequestParams rp) {

        ServiciosRestClient.get(URL, rp, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                showLoadingMessage(dialogMessage);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString,
                                  Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                showToast("Error " + statusCode + ": " + throwable.getMessage());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                                  JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                showToast("Error " + statusCode + ": " + throwable.getMessage());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray historiaClinica) {

                if (historiaClinica.length() == 0) {
                    showToast("El paciente no posee historia clínica");
                    getActivity().finish();
                }

                try {
                    ArrayList<HistoriaClinica> vHC =
                            HistoriaClinicaHelper.getArrayListFromJSONArray(historiaClinica);
                    hcAdapter = new HistoriaClinicaAdapter(getActivity(), vHC, HistoriaClinicaFragment.this);

                    setListAdapter(hcAdapter);
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

    private void showLoadingMessage(String message) {
        pDialog.setMessage(message);
        pDialog.show();
    }

    private void showToast(String mensaje) {
        Toast.makeText(getActivity().getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
    }
}
