package com.paramedic.mobshaman.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.paramedic.mobshaman.R;
import com.paramedic.mobshaman.activities.ServiciosActivity;
import com.paramedic.mobshaman.domain.Configuration;
import org.json.JSONArray;
import org.json.JSONObject;

import com.onesignal.OneSignal;


/**
 * Created by Maximiliano Poggio on 28/07/2014.
 */
public class ConfigGeneralFragment extends Fragment {

    EditText etNroMovilRegistro, etUrlregistro, etNroLicencia;
    Button btnRegistrarMovil;
    CheckBox checkboxSolicitaReport, checkboxAttachImage;
    String  url, nroLicencia, nroMovil;
    Boolean solicitaNroReport, solicitaAttachImage;
    private Configuration configuration;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /** Inicializo los objetos de la UI para usarlos **/

        View myView = inflater.inflate(R.layout.fragment_config_general,container, false);

        etNroMovilRegistro = (EditText) myView.findViewById(R.id.et_movil_registro);
        etUrlregistro = (EditText) myView.findViewById(R.id.et_url_registro);
        btnRegistrarMovil = (Button) myView.findViewById(R.id.btn_registrar_movil);
        etNroLicencia = (EditText) myView.findViewById(R.id.et_registro_licencia);
        checkboxSolicitaReport = (CheckBox) myView.findViewById(R.id.checkbox_nro_report);
        checkboxAttachImage = (CheckBox) myView.findViewById(R.id.checkbox_attach_image_config);

        configuration = Configuration.getInstance(this.getActivity());

        setValues();

        /** Si hago click en registrar movil.. **/
        btnRegistrarMovil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                /** Obtengo los valores del nro de movil y de la URL REST,
                 * y verifico que ambos tengan datos
                 */
                nroMovil = etNroMovilRegistro.getText().toString();
                url = etUrlregistro.getText().toString();
                nroLicencia = etNroLicencia.getText().toString();
                solicitaNroReport = checkboxSolicitaReport.isChecked();
                solicitaAttachImage = checkboxAttachImage.isChecked();

                if (!IsConfigurationValid()) {

                    Toast.makeText(getActivity(),"Debe ingresar Nro de Movil, Licencia y URL (10 caracteres)",Toast.LENGTH_LONG).show();

                } else {

                    try {

                        /** Guardo datos de la configuracion en las shared preferences **/
                        SharedPreferences prefs = getActivity().getSharedPreferences("configuration", Context.MODE_PRIVATE);

                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("url", url);
                        editor.putString("mobile", nroMovil.toString().trim());
                        editor.putString("license", nroLicencia);
                        editor.putBoolean("requestReportNumber", solicitaNroReport);
                        editor.putBoolean("requestAttachImage", solicitaAttachImage);
                        editor.commit();

                        configuration.setUrl(url);
                        configuration.setMobile(nroMovil.toString().trim());
                        configuration.setLicense(nroLicencia);
                        configuration.setRequestReportNumber(solicitaNroReport);
                        configuration.setRequestAttachImage(solicitaAttachImage);

                        // --> Registro el equipo para las push notifications con nro movil y licencia

                        JSONObject tags = new JSONObject();
                        tags.put("license", nroLicencia);
                        tags.put("mobile", nroMovil);
                        OneSignal.sendTags(tags);

                        Toast.makeText(getActivity(),"El movil se configuró correctamente.",Toast.LENGTH_LONG).show();

                        /** Ejecuto la actividad principal ya que está configurado **/
                        startActivity(new Intent(getActivity(),ServiciosActivity.class));

                    } catch (Exception e) {
                        Toast.makeText(getActivity(),"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

        return myView;

    }

    private boolean IsConfigurationValid() {
        if (nroMovil.toString() == "") return false;
        if (nroLicencia == "") return false;
        if (url == "") return false;
        if (url.length() < 10) return false;
        return true;
    }

    private void setValues() {
        if (configuration != null) {
            etNroMovilRegistro.setText(configuration.getMobile());
            etNroLicencia.setText(configuration.getLicense());
            etUrlregistro.setText(configuration.getUrl());
            checkboxSolicitaReport.setChecked(configuration.isRequestReportNumber());
            checkboxAttachImage.setChecked(configuration.isRequestAttachImage());
        }
    }
}
