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
import com.parse.ParseInstallation;
import com.parse.PushService;
import org.json.JSONArray;


/**
 * Created by Maximiliano Poggio on 28/07/2014.
 */
public class ConfigGeneralFragment extends Fragment {

    EditText etNroMovilRegistro, etUrlregistro, etNroLicencia;
    Button btnRegistrarMovil;
    CheckBox checkboxSolicitaReport;
    String nroMovil, url, nroLicencia;
    Boolean solicitaNroReport;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /** Inicializo los objetos de la UI para usarlos **/

        View myView = inflater.inflate(R.layout.fragment_config_general,container, false);

        etNroMovilRegistro = (EditText) myView.findViewById(R.id.et_movil_registro);
        etUrlregistro = (EditText) myView.findViewById(R.id.et_url_registro);
        btnRegistrarMovil = (Button) myView.findViewById(R.id.btn_registrar_movil);
        etNroLicencia = (EditText) myView.findViewById(R.id.et_registro_licencia);
        checkboxSolicitaReport = (CheckBox) myView.findViewById(R.id.checkbox_nro_report);

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

                if (!IsConfigurationValid()) {

                    Toast.makeText(getActivity(),"Debe ingresar Nro de Movil, Licencia y URL",Toast.LENGTH_LONG).show();

                } else {

                    try {

                        /** Guardo datos de la configuracion en las shared preferences **/
                        SharedPreferences prefs = getActivity().getSharedPreferences("configuration", Context.MODE_PRIVATE);

                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("url", url);
                        editor.putString("mobile", nroMovil);
                        editor.putString("license", nroLicencia);
                        editor.putBoolean("requestReportNumber", solicitaNroReport);
                        editor.commit();

                        /** Obtengo el/los canal/es en los cuales está registrado el móvil/celular **/
                        JSONArray vInstalacion = ParseInstallation.getCurrentInstallation().getJSONArray("channels");

                        /** Borro todas las suscripciones que tenga, ya que un móvil solo puede estar
                            asociado a un canal **/
                        if (vInstalacion != null) {
                            for(int i = 0; i < vInstalacion.length(); i++) {
                                PushService.unsubscribe(getActivity(),vInstalacion.getString(i));
                            }
                        }

                        /** Me suscribo al canal registrado. Por ejemplo, el móvil 23 se registra
                         * en el canal m23 **/
                        PushService.subscribe(getActivity(), "m" + nroMovil, ServiciosActivity.class);

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
        if (nroMovil == "") return false;
        if (nroLicencia == "") return false;
        if (url == "") return false;
        return true;
    }
}
