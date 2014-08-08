package com.paramedic.mobshaman.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.paramedic.mobshaman.R;
import com.paramedic.mobshaman.helpers.SharedPrefsHelper;
import com.paramedic.mobshaman.rest.ServiciosRestClient;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class ActualizarInformacionActivity extends ActionBarActivity {

    ProgressDialog pDialog;
    Button btnActualizarMotivos, btnActualizarDiagnosticos;
    String URL_REST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_actualizar_informacion);

        pDialog = new ProgressDialog(this);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        URL_REST = new SharedPrefsHelper().getURLFromSharedPrefs(this);
        btnActualizarDiagnosticos = (Button) findViewById(R.id.btn_actualizar_diagnosticos);
        btnActualizarMotivos = (Button) findViewById(R.id.btn_actualizar_motivos_no_realizacion);

        String URL_DIAGNOSTICOS = URL_REST + "/api/diagnosticos";
        String URL_MOTIVOS = URL_REST + "/api/motivos";

        setButtonsForDownloadInformation(btnActualizarDiagnosticos,
                "Cargando Diagnósticos...",URL_DIAGNOSTICOS,"diagnosticos",
                "Los diagnósticos han sido actualizados");

        setButtonsForDownloadInformation(btnActualizarMotivos,
                "Cargando Motivos...",URL_MOTIVOS,"motivos",
                "Los motivos han sido actualizados");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setButtonsForDownloadInformation(Button btn, final String dialogMessage,
                                                  final String URL, final String fileName,
                                                  final String okMessage) {

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                try {
                    doAsyncTaskGetInformation(URL,dialogMessage,null,fileName,okMessage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void doAsyncTaskGetInformation(String url, final String dialogMessage,
                                           RequestParams rp, final String fileName,
                                           final String okMessage)
            throws JSONException {

        ServiciosRestClient.get(url, rp, new JsonHttpResponseHandler() {

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
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                ArrayList<String> lineas = new ArrayList<String>();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject obj = (JSONObject) response.get(i);
                        String linea = obj.getInt("ID") + "&" + obj.getString("Descripcion");
                        lineas.add(linea);
                    }

                    updateFile(fileName,lineas);
                    showToast(okMessage);

                } catch (JSONException e) {
                    showToast(e.getMessage());
                }
                pDialog.dismiss();

            }
        });
    }

    private void showToast(String mensaje) {
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
    }

    private void showLoadingMessage(String message) {
        pDialog.setMessage(message);
        pDialog.show();
    }

    private void updateFile(String fileName,ArrayList<String> inputs) {

        FileOutputStream outputStream;

        try {
            String separator = System.getProperty("line.separator");
            outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(outputStream);

            for (String input : inputs) {
                osw.append(input);
                osw.append(separator);
            }

            osw.flush();
            osw.close();
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
