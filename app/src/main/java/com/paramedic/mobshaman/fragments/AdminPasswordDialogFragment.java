package com.paramedic.mobshaman.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.paramedic.mobshaman.R;
import com.paramedic.mobshaman.activities.ConfigGeneralActivity;
import com.paramedic.mobshaman.application.MobShamanApplication;
import com.paramedic.mobshaman.domain.Configuration;
import com.paramedic.mobshaman.helpers.Utils;
import com.paramedic.mobshaman.managers.SessionManager;
import com.paramedic.mobshaman.models.LoginResponse;
import com.paramedic.mobshaman.rest.ApiClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by maxo on 29/07/14.
 */

public class AdminPasswordDialogFragment extends DialogFragment {

    EditText ipt_user, ipt_password;
    ProgressDialog progressDialog;
    SessionManager session;
    Configuration configuration;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        configuration = Configuration.getInstance(this.getActivity());
        /** Muestro un dialogo spinner (no dejo que usuario use UI) **/
        progressDialog = new ProgressDialog(this.getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        session = new SessionManager(getActivity().getApplicationContext());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        /** Obtengo variables para usar de la UI **/

        View myView = inflater.inflate(R.layout.dialog_admin_password,null);

        ipt_user = (EditText) myView.findViewById(R.id.username);
        ipt_password = (EditText) myView.findViewById(R.id.password);
        ipt_password.setTypeface(Typeface.DEFAULT );

        /** Armo el dialog para ingresar usuario / password **/
        builder.setView(myView)
                // Add action buttons
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        /** Si ingreso los datos correctos del administrador, puedo configurar
                         * la aplicación
                         */

                        String user_ingresado = ipt_user.getText().toString();
                        String pass_ingresado = ipt_password.getText().toString();

                        loginRequest(user_ingresado, pass_ingresado);

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AdminPasswordDialogFragment.this.getDialog().cancel();
                    }
                });

        builder.setInverseBackgroundForced(true);
        return builder.create();
    }

    private void loginRequest(String username, String password) {

        try {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            ApiClient sac = new ApiClient(getActivity(), configuration.getGestionUrl());
            LoginResponse lr = sac.getLoginCall(username, password).execute().body();

            if (lr.getError()) {
                Utils.showToast(getActivity(), "Error! Los datos son incorrectos.");
            } else {
                startActivity(new Intent(getActivity(), ConfigGeneralActivity.class));
            }

        } catch (IOException e) {

            Utils.showToast(getActivity(), "Error:" + e.getMessage());

        }

    }
}