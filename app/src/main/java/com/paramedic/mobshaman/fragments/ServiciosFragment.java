package com.paramedic.mobshaman.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;
import com.paramedic.mobshaman.R;
import com.paramedic.mobshaman.adapters.ServiciosAdapter;
import com.paramedic.mobshaman.models.Servicio;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by maxo on 22/07/14.
 */
public class ServiciosFragment extends ListFragment {

    ListView listView;
    private ProgressDialog pDialog;
    private AsyncRefreshServices refreshServices;
    private ArrayList<Servicio> servicios;
    private ServiciosAdapter servAdapter;
    public final static String URL_REST_SERVICIOS = "http://paramedicapps.com.ar:58887/api/servicios";

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        // --> Uso comportamiento de la action bar desde el fragment
        setHasOptionsMenu(true);

        servicios = new ArrayList<Servicio>();

        // --> Redefino el estilo de la listview que usa el fragment
        listView = getListView();
        ColorDrawable divider = new ColorDrawable(this.getResources().getColor(R.color.verde_color));
        listView.setDivider(divider);
        listView.setDividerHeight(1);

        // --> Busco servicios en el servicio REST
        callAsyncServices();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // --> Redefino este metodo para poder actualizar desde el fragment y no desde la activity

        boolean handled = false;

        int id = item.getItemId();
        switch (id) {
            case R.id.action_refresh:
                callAsyncServices();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return handled;
    }

    private void callAsyncServices() {

        // --> Creo un nuevo progressDialog para mostrar que estoy actualizando
        pDialog = new ProgressDialog(getActivity());
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setMessage("Actualizando Servicios...");
        pDialog.setCancelable(true);
        // --> Llamo a la tarea asincronica que busca servicios desde el servicio REST
        refreshServices = new AsyncRefreshServices();
        refreshServices.execute();

    }

    private class AsyncRefreshServices extends AsyncTask<Void, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

            return getServicios();

        }

        private boolean getServicios(){

            HttpClient httpClient = new DefaultHttpClient();

            HttpGet del =
                    new HttpGet(URL_REST_SERVICIOS);

            del.setHeader("content-type", "application/json");

            try
            {
                HttpResponse resp = httpClient.execute(del);

                String respStr = EntityUtils.toString(resp.getEntity());

                JSONArray respJSON = new JSONArray(respStr);

                servicios.clear();

                for(int i=0; i<respJSON.length(); i++)
                {
                    JSONObject obj = respJSON.getJSONObject(i);
                    Servicio serv = new Servicio(obj.getInt("IdServicio"),obj.getString("Cliente"),
                            obj.getInt("Grado"), obj.getString("NroServicio"),
                            obj.getString("Domicilio"),obj.getString("Sexo"),
                            obj.getString("Edad"),obj.getString("Horario"));
                    servicios.add(serv);

                }

                return true;

            }
            catch(Exception ex)
            {
                Log.e("ServicioRest", "Error!", ex);
                return false;
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int progreso = values[0].intValue();

            pDialog.setProgress(progreso);
        }

        @Override
        protected void onPreExecute() {

            pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    AsyncRefreshServices.this.cancel(true);
                }
            });

            pDialog.show();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            pDialog.dismiss();
            if(result)
            {
                servAdapter = new ServiciosAdapter(getActivity(), servicios);
                setListAdapter(servAdapter);
               // Toast.makeText(getActivity().getApplicationContext(), clientes[0], Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity().getApplicationContext(),
                        "Hubo un problema. Vuelva a intentarlo", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(getActivity().getApplicationContext(), "Tarea cancelada!", Toast.LENGTH_SHORT).show();
        }
    }
}
